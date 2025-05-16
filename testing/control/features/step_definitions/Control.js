const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

let persona = {};
let designacion = {};
let apiResponse = {};

Given('la persona con {int} {string} y {string}', function (dni, nombre, apellido) {
    persona = { dni, nombre, apellido };
});

Given('que se asigna al cargo  con tipo de designación {string} y {string}', function (tipo, nombreDesignacion) {
    const url = `http://backend:8080/cargos/buscar-por-nombre-y-tipo?nombre=${encodeURIComponent(nombreDesignacion)}&tipo=${encodeURIComponent(tipo)}`;
    const res = request('GET', url);

    if (res.statusCode !== 200) throw new Error("Cargo no encontrado");

    const cargo = JSON.parse(res.getBody('utf8')).data;
    designacion.cargo = {
        id: cargo.id,
        nombre: cargo.nombre,
        tipoDesignacion: cargo.tipoDesignacion,
        division: cargo.division
    };
});

Given('que si el tipo es {string}, opcionalmente se asigna a la división {string} {string} {string}',
    function (tipoOriginal, anio, numero, turno) {
        if (designacion.cargo.tipoDesignacion === tipoOriginal) {
            const url = `http://backend:8080/divisiones/buscar-por-anio-numero-turno?anio=${encodeURIComponent(anio)}&numero=${encodeURIComponent(numero)}&turno=${encodeURIComponent(turno)}`;
            const res = request('GET', url);
            if (res.statusCode !== 200) throw new Error("División no encontrada");

            const division = JSON.parse(res.getBody('utf8')).data;
            designacion.cargo.division = {
                id: division.id,
                anio: division.anio,
                numDivision: division.numDivision,
                turno: division.turno
            };
        }
    });

Given('se designa por el período {string} {string}', function (desde, hasta) {
    designacion.fechaDesde = `${desde}T00:00:00`;
    designacion.fechaHasta = hasta ? `${hasta}T00:00:00` : null;
});

When('se presiona el botón guardar', function () {
    const body = {
        persona,
        cargo: designacion.cargo,
        fechaInicio: designacion.fechaDesde,
        fechaFin: designacion.fechaHasta,
        situacionRevista: 'Designación automática de test'
    };

    try {
        const res = request('POST', 'http://backend:8080/designaciones', { json: body });
        apiResponse = JSON.parse(res.getBody('utf8'));


    } catch (error) {
        apiResponse = {
            status: error.statusCode || 500,
            data: error.message || "Error desconocido"
        };
    }
});

Then('se espera el siguiente {int} y {string}', function (statusEsperado, mensajeEsperado) {
    assert.strictEqual(apiResponse.status, statusEsperado,
        `Esperado ${statusEsperado} pero se recibió ${apiResponse.status}`);
    assert.strictEqual(apiResponse.data, mensajeEsperado,
        `Esperado mensaje "${mensajeEsperado}" pero se recibió "${apiResponse.data}"`);
});