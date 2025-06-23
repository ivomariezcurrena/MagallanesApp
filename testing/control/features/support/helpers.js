const request = require('sync-request');

function hacerGET(url) {
    const res = request('GET', url);
    if (res.statusCode !== 200) throw new Error(`Error al hacer GET a ${url}`);
    return JSON.parse(res.getBody('utf8')).data;
}

function formatearFecha(fecha) {
    return fecha ? `${fecha}T00:00:00` : null;
}

function construirURLBusquedaCargo(nombre, tipo) {
    return `http://backend:8080/cargos/buscar-por-nombre-y-tipo?nombre=${encodeURIComponent(nombre)}&tipo=${encodeURIComponent(tipo)}`;
}

function construirURLBusquedaDivision(anio, numero, turno) {
    return `http://backend:8080/divisiones/buscar-por-anio-numero-turno?anio=${encodeURIComponent(anio)}&numero=${encodeURIComponent(numero)}&turno=${encodeURIComponent(turno)}`;
}

module.exports = {
    hacerGET,
    formatearFecha,
    construirURLBusquedaCargo,
    construirURLBusquedaDivision
};
