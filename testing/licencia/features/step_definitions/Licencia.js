const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const { hacerGET, hacerPOST, formatearFecha } = require('../support/helpers');

let licenciaRequest = {};
let apiResponse = {};
let personas = [];
let designaciones = [];
let designacionesAsignadas = [];
let resultadoServicio = {};

Given('el docente con DNI {int}, nombre {string} y apellido {string}', function (dni, nombre, apellido) {
  licenciaRequest = { dni, nombre, apellido };
});

Given('que existe la persona', function (dataTable) {
  personas = dataTable.hashes();
  personas.forEach(persona => {
    hacerGET(`http://backend:8080/personas/${persona.DNI || persona.dni}`, "Persona no encontrada");
  });
});

Given('que existen las siguientes instancias de designación asignada', function (dataTable) {
  designaciones = dataTable.hashes();
  const tipo = designaciones[0].TipoDesignacion || designaciones[0].tipoDesignacion || "cargo";
  const nombre = designaciones[0].NombreTipoDesignacion || designaciones[0].nombreTipoDesignacion;
  const cargo = hacerGET(
    `http://backend:8080/cargos/buscar-por-nombre-y-tipo?nombre=${encodeURIComponent(nombre)}&tipo=${encodeURIComponent(tipo)}`,
    "Cargo no encontrado"
  );
  designaciones[0].cargo = {
    id: cargo.id,
    nombre: cargo.nombre,
    tipoDesignacion: cargo.tipoDesignacion,
    division: cargo.division
  };
});

Given('que la instancia de designación está asignada a la persona', function (dataTable) {
  const persona = dataTable.hashes()[0];
  designacionesAsignadas.push(persona);
});

Given('que la instancia de designación está asignada a la persona con licencia {string} comprendida en el período desde {string} hasta {string}',
  function (articulo, desde, hasta) {
    designacionesAsignadas.push({
      articulo,
      desdeLicencia: desde,
      hastaLicencia: hasta
    });
  }
);

When('solicita una licencia artículo {string} con descripción {string} para el período {string} {string}',
  function (articulo, descripcion, desde, hasta) {
    licenciaRequest = {
      ...licenciaRequest,
      articulo,
      descripcion,
      fechaDesde: formatearFecha(desde),
      fechaHasta: formatearFecha(hasta),
    };

    try {
      const articuloResponse = hacerGET(
        `http://backend:8080/articulolicencias/buscar/${encodeURIComponent(articulo)}`,
        "Artículo no encontrado"
      );

      const articuloId = articuloResponse[0]?.id;
      if (!articuloId) {
        apiResponse = {
          status: 404,
          message: `No se encontró el artículo con el valor: ${articulo}`
        };
        return;
      }

      const body = {
        persona: { dni: licenciaRequest.dni },
        articulo: { id: articuloId },
        pedidoDesde: licenciaRequest.fechaDesde,
        pedidoHasta: licenciaRequest.fechaHasta,
        certificadoMedico: licenciaRequest.certificadoMedico ?? true
      };

      apiResponse = hacerPOST('http://backend:8080/licencias', body);
    } catch (err) {
      apiResponse = {
        status: 500,
        message: err.message
      };
    }
  }
);

When('se solicita el servicio de designación de la persona al cargo en el período comprendido desde {string} hasta {string}',
  function (desde, hasta) {
    const designacion = {
      persona: {
        dni: personas[0].DNI || personas[0].dni,
        nombre: personas[0].Nombre || personas[0].nombre,
        apellido: personas[0].Apellido || personas[0].apellido
      },
      cargo: designaciones[0].cargo,
      fechaInicio: formatearFecha(desde),
      fechaFin: formatearFecha(hasta),
      situacionRevista: "Suplente"
    };

    resultadoServicio = hacerPOST('http://backend:8080/designaciones', designacion);
    resultadoServicio = {
      StatusCode: resultadoServicio.status,
      StatusText: resultadoServicio.message || ""
    };
  }
);

Then('debería obtener la siguiente resultado de {int} y {string}', function (statusEsperado, mensajeEsperado) {
  assert.strictEqual(apiResponse.status, statusEsperado,
    `Esperado status ${statusEsperado} pero se recibió ${apiResponse.status}`);
  assert.strictEqual(apiResponse.message, mensajeEsperado,
    `Esperado mensaje "${mensajeEsperado}" pero se recibió "${apiResponse.message}"`);
});

Then('se recupera el mensaje', function (docString) {
  const esperado = JSON.parse(docString);
  assert.strictEqual(
    resultadoServicio.StatusCode,
    esperado.StatusCode,
    `Esperado StatusCode ${esperado.StatusCode} pero se recibió ${resultadoServicio.StatusCode}`
  );
  assert.strictEqual(
    resultadoServicio.StatusText,
    esperado.StatusText,
    `Esperado StatusText "${esperado.StatusText}" pero se recibió "${resultadoServicio.StatusText}"`
  );
});
