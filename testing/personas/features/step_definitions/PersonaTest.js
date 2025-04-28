const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

// Variables compartidas
let currentPersona = {};
let apiResponse = {};

// Paso: Dada la persona con <nombre> <apellido> <DNI> <CUIL> <sexo> <título> <domicilio> <teléfono>
Given(
    'la persona con {word} {word} {int} {word} {word} {string} {string} {string}',
    function (nombre, apellido, dni, cuil, sexo, titulo, domicilio, telefono) {
        // Corregido: los parámetros ahora coinciden con su uso
        currentPersona = {
            dni: dni,
            nombre: nombre,
            apellido: apellido,
            cuil: cuil,
            sexo: sexo,
            titulo: titulo === '' ? null : titulo,
            domicilio: domicilio,
            telefono: telefono
        };

        console.log('Persona creada:', currentPersona);
    }
);

// Paso: Cuando se presiona el botón de guardar
When('se presiona el botón de guardar', function () {
    try {
        console.log('Enviando petición al backend...');
        // En Docker, el nombre del contenedor es "backend" según el docker-compose.yml
        const res = request('POST', 'http://backend:8080/personas', {
            json: currentPersona
        });
        apiResponse = JSON.parse(res.getBody('utf8'));
        console.log('Respuesta recibida:', apiResponse);
    } catch (error) {
        console.error('Error al hacer la solicitud:', error.message);
        throw error;
    }
});

// Paso: Entonces se espera el siguiente <status> con la <respuesta>
Then('se espera el siguiente {int} con la {string}', function (expectedStatus, expectedResponse) {
    console.log(`Verificando respuesta: status=${apiResponse.status}, data="${apiResponse.data}"`);

    assert.equal(apiResponse.status, parseInt(expectedStatus),
        `El código de estado esperado era ${expectedStatus}, pero se recibió ${apiResponse.status}`);

    assert.equal(apiResponse.data, expectedResponse,
        `La respuesta esperada era "${expectedResponse}", pero se recibió "${apiResponse.data}"`);
});