const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

// Variables compartidas
let persona = {};
let designacion = {};
let apiResponse = {};

Given('la persona con {int} {string} y {string}', function (dni, nombre, apellido) {
    persona = {
        dni,
        nombre,
        apellido
    };
});

Given('que se asigna al cargo  con tipo de designación {string} y {string}', function (tipo, nombreDesignacion) {
    try {
        const tipoParam = encodeURIComponent(tipo);
        const nombreParam = encodeURIComponent(nombreDesignacion);
        const url = `http://backend:8080/cargos/buscar-por-nombre-y-tipo?nombre=${nombreParam}&tipo=${tipoParam}`;
        const res = request('GET', url);

        if (res.statusCode === 200) {
            const envelope = JSON.parse(res.getBody('utf8'));
            const cargo = envelope.data || envelope;
            designacion.cargo = {
                id: cargo.id,
                nombre: cargo.nombre,
                cargaHoraria: cargo.cargaHoraria,
                fechaInicio: cargo.fechaInicio,
                fechaInicio: cargo.fechaInicio,
                tipoDesignacion: cargo.tipoDesignacion,
                division: cargo.division,
                horarios: cargo.horarios
            };
            designacion.tipo = tipo; // ✅ ESTA LÍNEA ES CLAVE
        } else {
            throw new Error("Cargo no encontrado");
        }
    } catch (error) {
        throw new Error(`Error al buscar el cargo: ${error.message}`);
    }
});


Given('que si el tipo es {string}, opcionalmente se asigna a la división {string} {string} {string}',
    function (tipoOriginal, anio, numero, turno) {

        if (designacion.tipo === 'ESPACIO_CURRICULAR' && anio && numero && turno) {
            try {
                const turnoParam = encodeURIComponent(turno);
                const url = `http://backend:8080/divisiones/buscar-por-anio-numero-turno`
                    + `?anio=${anio}&numero=${numero}&turno=${turnoParam}`;
                const res = request('GET', url);
                if (res.statusCode === 200) {
                    const envelope = JSON.parse(res.getBody('utf8'));
                    const division = envelope.data || envelope;
                    designacion.cargo.division = { id: division.id };
                } else {
                    throw new Error("Division no encontrada");
                }
            } catch {
                throw new Error("Error al asignar una Division no encontrada");
            }
        } else {
            designacion.division = null;
        }
    });

Given('se designa por el período {string} {string}', function (desde, hasta) {
    designacion.fechaDesde = desde + "T00:00:00";
    designacion.fechaHasta = hasta ? hasta + "T00:00:00" : null;
});

When('se presiona el botón guardar', function () {

    const body = {
        persona,
        // aDesignacion.cargo  
        cargo: designacion.cargo,
        // aDesignacion.fechaInicio
        fechaInicio: designacion.fechaDesde,
        // aDesignacion.fechaFin
        fechaFin: designacion.fechaHasta,
        // si tenés situacionRevista en tu clase Designacion y querés setearla:
        situacionRevista: designacion.situacionRevista || 'Designación automática de test'
    };
    console.log('DEBUG body designación:', JSON.stringify(body, null, 2));
    try {
        const res = request('POST', 'http://backend:8080/designaciones', {
            json: body
        });
        apiResponse = JSON.parse(res.getBody('utf8'));
        apiResponse.status = res.statusCode;
    } catch (error) {
        apiResponse = {
            status: error.statusCode || 500,
            data: error.message
        };
        console.error('Error en la solicitud:', error);  // Esto te ayudará a ver si la solicitud falló
    }
});

Then('se espera el siguiente {int} y {string}', function (statusEsperado, mensajeEsperado) {
    console.log('Respuesta cruda del backend:', apiResponse);

    assert.strictEqual(apiResponse.status, statusEsperado,
        `Esperado ${statusEsperado} pero se recibió ${apiResponse.status}`);
    assert.strictEqual(apiResponse.data, mensajeEsperado,
        `Esperado mensaje "${mensajeEsperado}" pero se recibió "${apiResponse.data}"`);
});
