package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.ArticuloLicenciaRepository;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.PersonaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

@Component
public class ValidarLicencia {
    @Autowired
    @Lazy
    LicenciaRepository licenciaRepository;

    @Autowired
    @Lazy
    PersonaRepository personaRepository;

    @Autowired
    @Lazy
    ArticuloLicenciaRepository articuloLicenciaRepository;

    @Autowired
    MensajeFormateador mensajeFormateador;

    @Autowired
    @Lazy
    DesignacionRepository designacionRepository;
    private final int TOPE_30_DIAS = 30;
    private final int TOPE_36A_MES = 2;
    private final int TOPE_36A_ANIO = 6;

    public void validar(Licencia licencia) {
        // Cargar persona y artículo completos si sólo vienen con id
        if (licencia.getPersona() != null && licencia.getPersona().getDni() != 0) {
            licencia.setPersona(personaRepository.findById(licencia.getPersona().getDni()).orElse(null));
            licencia.setDomicilio(licencia.getPersona().getDomicilio());
        }
        if (licencia.getArticulo() != null && licencia.getArticulo().getId() != 0) {
            licencia.setArticulo(articuloLicenciaRepository.findById(licencia.getArticulo().getId()).orElse(null));
        }
        if (licencia.getPersona() != null && licencia.getPedidoDesde() != null && licencia.getPedidoHasta() != null) {
            List<Designacion> designaciones = designacionRepository.findDesignacionesActivasEnPeriodo(
                    licencia.getPersona().getDni(),
                    licencia.getPedidoDesde(),
                    licencia.getPedidoHasta());
            licencia.setDesignaciones(designaciones);
        }
        if (licencia.getPersona() == null || licencia.getPersona().getDesignaciones() == null
                || licencia.getPersona().getDesignaciones().isEmpty()) {
            throw new IllegalArgumentException(mensajeFormateador.getErrorLicenciaSinCargo(licencia));
        }
        boolean tieneDesignacionEseDia = licencia.getPersona().getDesignaciones().stream().anyMatch(designacion -> {
            // Si la designación no tiene fechaFin, se considera activa hasta infinito
            return (designacion.getFechaInicio() == null
                    || !licencia.getPedidoHasta().isBefore(designacion.getFechaInicio()))
                    && (designacion.getFechaFin() == null
                            || !licencia.getPedidoDesde().isAfter(designacion.getFechaFin()));
        });

        if (!tieneDesignacionEseDia) {
            throw new IllegalArgumentException(mensajeFormateador.getErrorLicenciaSinDesignacionEseDia(licencia));
        }
        if (licencia.getPedidoDesde() == null) {
            throw new IllegalArgumentException("La fecha desde no puede ser nula");
        }

        if (licencia.getPedidoHasta() == null) {
            throw new IllegalArgumentException("La fecha hasta no puede ser nula");
        }

        if (licencia.getPedidoDesde().isAfter(licencia.getPedidoHasta())) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
        }

        validarFechas(licencia);
        if (licencia.getArticulo().getArticulo().equals("5A")
                || licencia.getArticulo().getArticulo().equals("23A")) {
            validarRegla30Dias(licencia, TOPE_30_DIAS);
        }
        if (licencia.getArticulo().getArticulo().equals("36A")) {
            validarRegla36A(licencia, TOPE_36A_MES, TOPE_36A_ANIO);
        }

    }

    public void validarFechas(Licencia licencia) {
        // Buscar licencias superpuestas para el mismo docente y artículo
        List<Licencia> superpuestas = licenciaRepository
                .verificarFechas(
                        licencia.getPersona().getDni(),
                        licencia.getArticulo(),
                        licencia.getPedidoDesde(),
                        licencia.getPedidoHasta(),
                        licencia.getId() == 0 ? -1 : licencia.getId() // Para nuevas, pasa -1
                );
        if (!superpuestas.isEmpty()) {
            throw new IllegalArgumentException(mensajeFormateador.getErrorLicenciaSuperposicion(licencia));
        }
    }

    private void validarRegla30Dias(Licencia licencia, int topeDias) {
        int anio = licencia.getPedidoDesde().getYear();

        List<Licencia> licenciasAnio = licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int diasUsados = 0;
        for (Licencia l : licenciasAnio) {
            // Si es update, no sumar la misma licencia
            if (licencia.getId() != 0 && licencia.getId() == l.getId())
                continue;
            diasUsados += (int) ChronoUnit.DAYS.between(l.getPedidoDesde().toLocalDate(),
                    l.getPedidoHasta().toLocalDate()) + 1;
        }

        int diasActual = (int) ChronoUnit.DAYS.between(
                licencia.getPedidoDesde().toLocalDate(),
                licencia.getPedidoHasta().toLocalDate()) + 1;

        int total = diasUsados + diasActual;

        if (total > topeDias) {
            throw new IllegalArgumentException(
                    mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeDias));
        }
    }

    private void validarRegla36A(Licencia licencia, int topeMes, int topeAnio) {
        int anio = licencia.getPedidoDesde().getYear();
        int mes = licencia.getPedidoDesde().getMonthValue();

        List<Licencia> licenciasAnio = licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int diasUsadosAnio = 0;
        int diasUsadosMes = 0;
        for (Licencia l : licenciasAnio) {
            // Si es update, no sumar la misma licencia
            if (licencia.getId() != 0 && licencia.getId() == l.getId())
                continue;
            int desdeMes = l.getPedidoDesde().getMonthValue();
            int hastaMes = l.getPedidoHasta().getMonthValue();
            int desdeAnio = l.getPedidoDesde().getYear();
            int hastaAnio = l.getPedidoHasta().getYear();

            int dias = (int) ChronoUnit.DAYS.between(l.getPedidoDesde().toLocalDate(),
                    l.getPedidoHasta().toLocalDate()) + 1;
            diasUsadosAnio += dias;

            // Sumar solo los días del mismo mes y año
            if (desdeAnio == anio && hastaAnio == anio && desdeMes == mes && hastaMes == mes) {
                diasUsadosMes += dias;
            } else if (desdeAnio == anio && hastaAnio == anio && (desdeMes <= mes && hastaMes >= mes)) {
                // Si la licencia abarca el mes actual, sumar solo los días que caen en el mes
                int desdeDia = (l.getPedidoDesde().getMonthValue() == mes) ? l.getPedidoDesde().getDayOfMonth() : 1;
                int hastaDia = (l.getPedidoHasta().getMonthValue() == mes) ? l.getPedidoHasta().getDayOfMonth()
                        : licencia.getPedidoDesde().toLocalDate().withMonth(mes).lengthOfMonth();
                diasUsadosMes += (hastaDia - desdeDia + 1);
            }
        }

        int diasActual = (int) ChronoUnit.DAYS.between(
                licencia.getPedidoDesde().toLocalDate(),
                licencia.getPedidoHasta().toLocalDate()) + 1;

        // Control anual
        if (diasUsadosAnio + diasActual > topeAnio) {
            throw new IllegalArgumentException(
                    mensajeFormateador.getErrorLicenciaTopeDias36AAnual(licencia, topeAnio));
        }

        // Control mensual
        if (diasUsadosMes + diasActual > topeMes) {
            throw new IllegalArgumentException(
                    mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeMes));
        }
    }

}
