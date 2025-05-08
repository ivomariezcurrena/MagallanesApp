const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

// Variables compartidas
let currentPersona = {};
let apiResponse = {};

// Paso: Dada la persona con <nombre> <apellido> <DNI> <CUIL> <sexo> <título> <domicilio> <teléfono>
Given(
    'la persona con {string} {string} {int} {string} {string} {string} {string} {string}',
    function (nombre, apellido, dni, cuil, sexo, titulo, domicilio, telefono) {
        currentPersona = {
            dni,
            nombre,
            apellido,
            cuil,
            sexo,
            titulo: titulo || null,
            domicilio,
            telefono
        };
    }
);

// Paso: Cuando se presiona el botón de guardar
When('se presiona el botón de guardar', function () {
    try {
        const res = request('POST', 'http://backend:8080/personas', {
            json: currentPersona
        });
        apiResponse = JSON.parse(res.getBody('utf8'));
        apiResponse.status = res.statusCode; // Añadido para validación posterior
    } catch (error) {
        apiResponse = {
            status: error.statusCode || 500,
            data: error.message
        };
    }
});

// Paso: Entonces se espera el siguiente <status> con la <respuesta>
Then('se espera el siguiente {int} con la {string}', function (expectedStatus, expectedResponse) {
    assert.strictEqual(apiResponse.status, expectedStatus,
        `Esperado status ${expectedStatus}, recibido ${apiResponse.status}`);

    assert.strictEqual(apiResponse.data, expectedResponse,
        `Esperado mensaje "${expectedResponse}", recibido "${apiResponse.data}"`);
});
