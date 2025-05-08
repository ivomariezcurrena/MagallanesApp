const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

// Variables compartidas
let currentDivision = {};
let apiResponse = {};

// Paso: Dada la división con <año> <número de división> <orientación> <turno>
Given('la división con {int} {int} {string} {string}', function (anio, numDivision, orientacion, turno) {
    currentDivision = {
        anio,
        numDivision,
        orientacion: orientacion || null,
        turno
    };
});

// Paso: Cuando se presiona el botón de guardar la división
When('se presiona el botón de guardar la división', function () {
    try {
        const res = request('POST', 'http://backend:8080/divisiones', {
            json: currentDivision
        });
        apiResponse = JSON.parse(res.getBody('utf8'));
        apiResponse.status = res.statusCode;
    } catch (error) {
        apiResponse = {
            status: error.statusCode || 500,
            data: error.message
        };
    }
});

// Paso: Entonces se espera el siguiente <status> con la <respuesta> de división
Then('se espera el siguiente {int} con la {string}', function (expectedStatus, expectedResponse) {
    assert.strictEqual(apiResponse.status, expectedStatus,
        `Esperado status ${expectedStatus}, recibido ${apiResponse.status}`);

    assert.strictEqual(apiResponse.data, expectedResponse,
        `Esperado mensaje "${expectedResponse}", recibido "${apiResponse.data}"`);
});
