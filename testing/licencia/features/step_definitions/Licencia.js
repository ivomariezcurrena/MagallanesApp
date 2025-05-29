const assert = require('assert');
const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');

let licenciaRequest = {};
let licenciaResponse = {};
let apiResponse = {};
let personas = [];
let designaciones = [];
let designacionesAsignadas = [];
let resultadoServicio = {};

Given('el docente con DNI {int}, nombre {string} y apellido {string}', function (dni, nombre, apellido) {
  licenciaRequest.dni = dni;
  licenciaRequest.nombre = nombre;
  licenciaRequest.apellido = apellido;
});

Given('que existe la persona', function (dataTable) {
  personas = dataTable.hashes();
  // Crear la persona en el backend si no existe
  personas.forEach(persona => {
    const res = request('GET', `http://backend:8080/personas/${persona.DNI || persona.dni}`);
    const parsed = JSON.parse(res.getBody('utf8'));
  });
});

Given('que existen las siguientes instancias de designación asignada', function (dataTable) {
  designaciones = dataTable.hashes();
  // Buscar y asignar el cargo real
  const tipo = designaciones[0].TipoDesignacion || designaciones[0].tipoDesignacion || "cargo";
  const nombre = designaciones[0].NombreTipoDesignacion || designaciones[0].nombreTipoDesignacion;
  const url = `http://backend:8080/cargos/buscar-por-nombre-y-tipo?nombre=${encodeURIComponent(nombre)}&tipo=${encodeURIComponent(tipo)}`;
  const res = request('GET', url);
  const cargo = JSON.parse(res.getBody('utf8')).data;
  if (!cargo) throw new Error(`Cargo no encontrado para nombre: ${nombre} y tipo: ${tipo}`);
  designaciones[0].cargo = {
    id: cargo.id,
    nombre: cargo.nombre,
    tipoDesignacion: cargo.tipoDesignacion,
    division: cargo.division
  };
});

Given('que la instancia de designación está asignada a la persona', function (dataTable) {
  // Guardar la persona asignada a la designación (sin licencia)
  const persona = dataTable.hashes()[0];
  designacionesAsignadas.push(persona);
});

// Sin tabla
Given(
  'que la instancia de designación está asignada a la persona con licencia {string} comprendida en el período desde {string} hasta {string}',
  function (articulo, desde, hasta) {
    let personaLicencia = {};
    personaLicencia.articulo = articulo;
    personaLicencia.desdeLicencia = desde;
    personaLicencia.hastaLicencia = hasta;
    designacionesAsignadas.push(personaLicencia);
  }
);


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
      certificadoMedico: licenciaRequest.certificadoMedico || true,
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

When('se solicita el servicio de designación de la persona al cargo en el período comprendido desde {string} hasta {string}', function (desde, hasta) {
  const designacion = {
    persona: {
      dni: personas[0].DNI || personas[0].dni,
      nombre: personas[0].Nombre || personas[0].nombre,
      apellido: personas[0].Apellido || personas[0].apellido
    },
    cargo: designaciones[0].cargo,
    fechaInicio: desde + "T00:00:00",
    fechaFin: hasta + "T00:00:00",
    situacionRevista:"Suplente",
  };
  const res = request('POST', 'http://backend:8080/designaciones', {
    json: designacion
  });
  const parsed = JSON.parse(res.getBody('utf8'));
  resultadoServicio = {
    StatusCode: parsed.status || parsed.StatusCode || 200,
    StatusText: parsed.message || parsed.mensaje || parsed.message || ""
  };
});

Then('debería obtener la siguiente resultado de {int} y {string}', function (statusEsperado, mensajeEsperado) {

  assert.strictEqual(
    apiResponse.status,
    statusEsperado,
    `Esperado status ${statusEsperado} pero se recibió ${apiResponse.status}`
  );

  assert.strictEqual(
    apiResponse.message,
    mensajeEsperado,
    `Esperado mensaje "${mensajeEsperado}" pero se recibió "${apiResponse.message}"`
  );
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
