const request = require('sync-request');

function hacerGET(url, mensajeError = 'Error en GET') {
  const res = request('GET', url);
  if (res.statusCode !== 200) throw new Error(mensajeError);
  return JSON.parse(res.getBody('utf8')).data;
}

function hacerPOST(url, body, mensajeError = 'Error en POST') {
  try {
    const res = request('POST', url, { json: body });
    const parsed = JSON.parse(res.getBody('utf8'));
    return {
      ...parsed,
      status: parsed.status || res.statusCode
    };
  } catch (error) {
    return {
      status: error.statusCode || 500,
      message: error.message || mensajeError
    };
  }
}

function formatearFecha(fecha) {
  return fecha ? `${fecha}T00:00:00` : null;
}

module.exports = {
  hacerGET,
  hacerPOST,
  formatearFecha,
};
