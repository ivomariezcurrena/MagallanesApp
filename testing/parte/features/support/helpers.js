const request = require('sync-request');

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

module.exports = {
  mapLicenciaToDocente
};
