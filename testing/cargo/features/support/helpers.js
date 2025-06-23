const request = require('sync-request');

function formatearFecha(fecha) {
    return fecha ? `${fecha}T00:00:00` : null;
}

function buscarDivision(anio, numero, turno) {
    const url = `http://backend:8080/divisiones/buscar-por-anio-numero-turno?anio=${anio}&numero=${numero}&turno=${encodeURIComponent(turno)}`;
    try {
        const res = request('GET', url);
        if (res.statusCode === 200) {
            const envelope = JSON.parse(res.getBody('utf8'));
            const division = envelope.data || envelope;
            return { id: division.id };
        }
    } catch (_) {
        // Ignorado intencionalmente
    }
    return null;
}

function crearDivisionPayload(anio, numero, turno) {
    return {
        anio: parseInt(anio),
        numDivision: parseInt(numero),
        orientacion: null,
        turno
    };
}

module.exports = {
    formatearFecha,
    buscarDivision,
    crearDivisionPayload
};