const request = require('sync-request');


function hacerGET(url, errorMessage) {
    const res = request('GET', url);
    if (res.statusCode !== 200) throw new Error(errorMessage);
    return JSON.parse(res.getBody('utf8')).data;
}

function formatearFecha(fecha) {
    return fecha ? `${fecha}T00:00:00` : null;
}

function urlBuscarCargo(nombre, tipo, anio = null, numero = null, turno = null) {
    if (tipo === 'ESPACIO_CURRICULAR' && anio && numero && turno) {
        return `http://backend:8080/cargos/buscar-por-nombre-tipo-division`
            + `?nombre=${encodeURIComponent(nombre)}`
            + `&tipo=${encodeURIComponent(tipo)}`
            + `&anio=${anio}`
            + `&numero=${numero}`
            + `&turno=${encodeURIComponent(turno)}`;
    }
    return `http://backend:8080/cargos/buscar-por-nombre-y-tipo`
        + `?nombre=${encodeURIComponent(nombre)}`
        + `&tipo=${encodeURIComponent(tipo)}`;
}

function urlBuscarDivision(anio, numero, turno) {
    return `http://backend:8080/divisiones/buscar-por-anio-numero-turno`
        + `?anio=${encodeURIComponent(anio)}`
        + `&numero=${encodeURIComponent(numero)}`
        + `&turno=${encodeURIComponent(turno)}`;
}

module.exports = {
    hacerGET,
    formatearFecha,
    urlBuscarCargo,
    urlBuscarDivision
};