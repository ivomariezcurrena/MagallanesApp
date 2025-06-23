const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');
const { formatearFecha, buscarDivision, crearDivisionPayload } = require('../support/helpers');

// Estado compartido entre pasos
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
    currentCargo.fechaInicio = formatearFecha(desde);
    currentCargo.fechaFin = formatearFecha(hasta);
});

Given('que si el tipo es {string}, opcionalmente se asigna a la división {string} {string} {string}', function (tipoOriginal, anio, numero, turno) {
    const hayDatosDivision = anio && numero && turno;

    if (!hayDatosDivision) {
        currentCargo.division = null;
        return;
    }

    if (currentCargo.tipoDesignacion === 'ESPACIO_CURRICULAR') {
        currentCargo.division = buscarDivision(anio, numero, turno);
    } else {
        currentCargo.division = crearDivisionPayload(anio, numero, turno);
    }
});

When('se presiona el botón de guardar', function () {
    try {
        const res = request('POST', 'http://backend:8080/cargos', { json: currentCargo });
        apiResponse = JSON.parse(res.getBody('utf8'));
    } catch (error) {
        apiResponse = {
            status: error.statusCode || 500,
            data: error.message
        };
    }
});

Then('se espera el siguiente {int} con la {string}', function (expectedStatus, expectedMsg) {
    assert.strictEqual(apiResponse.status, expectedStatus, `Esperado ${expectedStatus} pero se recibió ${apiResponse.status}`);
    assert.strictEqual(apiResponse.data, expectedMsg, `Esperado mensaje "${expectedMsg}" pero se recibió "${apiResponse.data}"`);
});
