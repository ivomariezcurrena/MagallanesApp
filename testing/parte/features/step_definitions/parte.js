const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

/**
 * Transforma una licencia del backend al formato esperado por el feature.
 */
function mapLicenciaToDocente(licencia) {
  return {
    DNI: licencia.persona.dni,
    Nombre: licencia.persona.nombre,
    Apellido: licencia.persona.apellido,
    Artículo: licencia.articulo.articulo,
    Descripción: licencia.articulo.descripcion,
    Desde: licencia.pedidoDesde.substring(0, 10),
    Hasta: licencia.pedidoHasta.substring(0, 10)
  };
}

Given('la existencia de las siguientes licencias', function (dataTable) {
  this.licencias = dataTable.hashes();
});

Given('que se otorgan las siguientes nuevas licencias', function (dataTable) {
  dataTable.hashes().forEach(licencia => {
    // Buscar el id del artículo por nombre/código
    const resArticulo = request(
      'GET',
      `http://backend:8080/articulolicencias/buscar/${encodeURIComponent(licencia.Artículo)}`
    );
    const articuloResponse = JSON.parse(resArticulo.getBody('utf8'));
    if (!articuloResponse.data || articuloResponse.data.length === 0) {
      throw new Error(`No se encontró el artículo con el valor: ${licencia.Artículo}`);
    }
    const articuloId = articuloResponse.data[0].id;

    // Construir el body para el POST
    const body = {
      persona: { dni: licencia.DNI },
      articulo: { id: articuloId },
      pedidoDesde: `${licencia.Desde}T00:00:00`,
      pedidoHasta: `${licencia.Hasta}T00:00:00`,
      descripcion: licencia.Descripción,
      certificadoMedico: true
    };

    // Hacer el POST
    request('POST', 'http://backend:8080/licencias', { json: body });
  });
});

When('se solicita el parte diario para la fecha {string}', function (fecha) {
  this.fechaParteDiario = fecha;
  const res = request(
    'GET',
    `http://backend:8080/licencias/vigentes-en-fecha?fecha=${encodeURIComponent(fecha)}T00:00:00`
  );
  this.parteDiarioResponse = JSON.parse(res.getBody('utf8'));
});

Then('el sistema responde', function (docString) {
  const { data } = this.parteDiarioResponse;
  const docentes = (data || []).map(mapLicenciaToDocente);

  const parteFormateado = {
    ParteDiario: {
      Fecha: this.fechaParteDiario,
      Docentes: docentes
    }
  };

  const esperado = JSON.parse(docString);
  assert.deepStrictEqual(parteFormateado, esperado);
});