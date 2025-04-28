const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

// Variables compartidas
let currentDivision = {};
let apiResponse = {};

// Paso: Dada la división con <año> <número de división> <orientación> <turno>
Given(
    'la división con {int} {int} {string} {string}',
    function (anio, numDivision, orientacion, turno) {
        currentDivision = {
            anio: anio,
            numDivision: numDivision,
            orientacion: orientacion === '' ? null : orientacion,
            turno: turno
        };

        console.log('División creada:', currentDivision);
    }
);

// Paso: Cuando se presiona el botón de guardar la división
When('se presiona el botón de guardar la división', function () {
    try {
        console.log('Enviando petición al backend...');
        // En Docker, asumimos que el contenedor del backend se llama "backend"
        const res = request('POST', 'http://backend:8080/divisiones', {
            json: currentDivision
        });
        apiResponse = JSON.parse(res.getBody('utf8'));
        console.log('Respuesta recibida:', apiResponse);
    } catch (error) {
        console.error('Error al hacer la solicitud:', error.message);
        throw error;
    }
});

// Paso: Entonces se espera el siguiente <status> con la <respuesta> de división
Then('se espera el siguiente {int} con la {string}', function (expectedStatus, expectedResponse) {
    console.log(`Verificando respuesta: status=${apiResponse.status}, data="${apiResponse.data}"`);

    assert.equal(apiResponse.status, parseInt(expectedStatus),
        `El código de estado esperado era ${expectedStatus}, pero se recibió ${apiResponse.status}`);

    assert.equal(apiResponse.data, expectedResponse,
        `La respuesta esperada era "${expectedResponse}", pero se recibió "${apiResponse.data}"`);
});
