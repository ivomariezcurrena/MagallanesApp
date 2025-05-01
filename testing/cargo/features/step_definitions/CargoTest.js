const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

// Variables compartidas
let currentCargo = {};
let apiResponse = {};

Given('el cargo institucional cuyo {string} que da título al mismo', function (nombre) {
    currentCargo.nombre = nombre;
});

Given('que es del tipo de designación {string}', function (tipo) {
    currentCargo.tipoDesignacion = tipo;
});

Given('que tiene una carga horaria de {int} horas, con vigencia desde {string} hasta {string}', function (carga, desde, hasta) {
    currentCargo.cargaHoraria = carga;
    currentCargo.fechaInicio = desde + "T00:00:00";
    currentCargo.fechaFin = hasta ? hasta + "T00:00:00" : null;
});

Given(
    'que si el tipo es {string}, opcionalmente se asigna a la división {string} {string} {string}',
    function (tipoOriginal, anio, numero, turno) {
        // Si no vienen todos los datos, no asignamos nada
        if (!anio || !numero || !turno) {
            currentCargo.division = null;
            return;
        }

        // ESPACIO_CURRICULAR: intento buscarla; si no existe la dejo en null
        if (currentCargo.tipoDesignacion === 'ESPACIO_CURRICULAR') {
            try {
                const turnoParam = encodeURIComponent(turno);
                const url = `http://backend:8080/divisiones/buscar-por-anio-numero-turno`
                    + `?anio=${anio}&numero=${numero}&turno=${turnoParam}`;
                const res = request('GET', url);
                if (res.statusCode === 200) {
                    const envelope = JSON.parse(res.getBody('utf8'));
                    const division = envelope.data || envelope;
                    currentCargo.division = { id: division.id };
                } else {
                    currentCargo.division = null;
                }
            } catch {
                currentCargo.division = null;
            }
            return;
        }

        // CUALQUIER OTRO TIPO (CARGO): **si el feature pasó datos de división, los
        // envío tal cual** para que el backend valide este error
        currentCargo.division = {
            anio: parseInt(anio),
            numDivision: parseInt(numero),
            orientacion: null,
            turno: turno
        };
    }
);

When('se presiona el botón de guardar', function () {
    try {
        const res = request('POST', 'http://backend:8080/cargos', {
            json: currentCargo
        });
        apiResponse = JSON.parse(res.getBody('utf8'));
    } catch (error) {
        // Si el backend lanza error, capturamos el status y el mensaje
        apiResponse = {
            status: error.statusCode || 500,
            data: error.message
        };
    }
});

Then('se espera el siguiente {int} con la {string}', function (expectedStatus, expectedMsg) {
    assert.strictEqual(apiResponse.status, expectedStatus,
        `Esperado ${expectedStatus} pero se recibió ${apiResponse.status}`);
    assert.strictEqual(apiResponse.data, expectedMsg,
        `Esperado mensaje "${expectedMsg}" pero se recibió "${apiResponse.data}"`);
});
