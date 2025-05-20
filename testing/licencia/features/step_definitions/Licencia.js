const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

let licenciaRequest = {};
let licenciaResponse = {};
let apiResponse = {};

Given('el docente con DNI {int}, nombre {string} y apellido {string}', function (dni, nombre, apellido) {
  licenciaRequest.dni = dni;
  licenciaRequest.nombre = nombre;
  licenciaRequest.apellido = apellido;
});

When('solicita una licencia artículo {string} con descripción {string} para el período {string} {string}', function (articulo, descripcion, desde, hasta) {
  licenciaRequest.articulo = articulo;
  licenciaRequest.descripcion = descripcion;
  licenciaRequest.fechaDesde = `${desde}T00:00:00`;
  licenciaRequest.fechaHasta = `${hasta}T00:00:00`;

  // Buscar el artículo de licencia antes de solicitar la licencia
  try {
    const resArticulo = request('GET', `http://backend:8080/articulolicencias/buscar/${encodeURIComponent(articulo)}`);
    const articuloResponse = JSON.parse(resArticulo.getBody('utf8'));
    if (!articuloResponse.data || articuloResponse.data.length === 0) {
      licenciaResponse = {
        status: 404,
        mensaje: `No se encontró el artículo con el valor: ${articulo}`
      };
      return;
    }
    const articuloId = articuloResponse.data[0].id; // Tomar el primero de la lista
    const body = {
      persona: { dni: licenciaRequest.dni },
      articulo: { id: articuloId },
      pedidoDesde: licenciaRequest.fechaDesde,
      pedidoHasta: licenciaRequest.fechaHasta,
      certificadoMedico: licenciaRequest.certificadoMedico ||false,
    };

    const res = request('POST', 'http://backend:8080/licencias', { json: body });
    const parsed = JSON.parse(res.getBody('utf8'));
    apiResponse = { ...parsed, status: parsed.status }; // Usar el status del body
  } catch (error) {
    apiResponse = {
        status: error.statusCode || 500,
        data: error.message
    };
  }
});

Then('debería obtener la siguiente resultado de {int} y {string}', function (statusEsperado, mensajeEsperado) {
  assert.strictEqual(
    apiResponse.status,
    statusEsperado,
    `Esperado status ${statusEsperado} pero se recibió ${apiResponse.status}`
  );

  assert.strictEqual(
    apiResponse.data,
    mensajeEsperado,
    `Esperado mensaje "${mensajeEsperado}" pero se recibió "${apiResponse.data}"`
  );
});
