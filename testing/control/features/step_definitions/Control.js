const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');
const { formatearFecha, hacerGET, construirURLBusquedaCargo,construirURLBusquedaDivision } = require('../support/helpers');
// Estado compartido
let persona = {};
let designacion = {};
let apiResponse = {};

Given('la persona con {int} {string} y {string}', function (dni, nombre, apellido) {
    persona = { dni, nombre, apellido };
});

Given('que se asigna al cargo  con tipo de designación {string} y {string}', function (tipo, nombreDesignacion) {
    const url = construirURLBusquedaCargo(nombreDesignacion, tipo);
    const cargo = hacerGET(url);
    designacion.cargo = {
        id: cargo.id,
        nombre: cargo.nombre,
        tipoDesignacion: cargo.tipoDesignacion,
        division: cargo.division
    };
});

Given('que si el tipo es {string}, opcionalmente se asigna a la división {string} {string} {string}', function (tipoOriginal, anio, numero, turno) {
    if (designacion.cargo.tipoDesignacion === tipoOriginal) {
        const url = construirURLBusquedaDivision(anio, numero, turno);
        const division = hacerGET(url);

        designacion.cargo.division = {
            id: division.id,
            anio: division.anio,
            numDivision: division.numDivision,
            turno: division.turno
        };
    }
});

Given('se designa por el período {string} {string}', function (desde, hasta) {
    designacion.fechaDesde = formatearFecha(desde);
    designacion.fechaHasta = formatearFecha(hasta);
});

When('se presiona el botón guardar', function () {
    const payload = {
        persona,
        cargo: designacion.cargo,
        fechaInicio: designacion.fechaDesde,
        fechaFin: designacion.fechaHasta,
        situacionRevista: 'Designación automática de test'
    };

    try {
        const res = request('POST', 'http://backend:8080/designaciones', { json: payload });
        apiResponse = JSON.parse(res.getBody('utf8'));
    } catch (error) {
        apiResponse = {
            status: error.statusCode || 500,
            message: error.message || "Error desconocido"
        };
    }
});

Then('se espera el siguiente {int} y {string}', function (statusEsperado, mensajeEsperado) {
    assert.strictEqual(apiResponse.status, statusEsperado, `Esperado ${statusEsperado} pero se recibió ${apiResponse.status}`);
    assert.strictEqual(apiResponse.message, mensajeEsperado, `Esperado mensaje "${mensajeEsperado}" pero se recibió "${apiResponse.message}"`);
});
