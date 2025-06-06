package unpsjb.labprog.backend.business.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.model.TipoDesignacion;

@Component
public class ValidarDesignacion {

    @Autowired
    @Lazy
    DesignacionService service;

    @Autowired
    @Lazy
    DesignacionRepository repository;

    @Autowired
    @Lazy
    LicenciaRepository licenciaRepository;

    @Autowired
    private MensajeFormateador mensaje;

    public void validar(Designacion d) {
        if (d.getCargo() == null) {
            throw new IllegalArgumentException("El cargo no puede ser nulo");
        }

        if (d.getPersona() == null) {
            throw new IllegalArgumentException("La persona no puede ser nula");
        }

        if (d.getCargo().getTipoDesignacion() == TipoDesignacion.ESPACIO_CURRICULAR
                && d.getCargo().getDivision() == null) {
            throw new IllegalArgumentException("El espacio curricular debe tener una división asignada");
        }
        // validar el tema de designar un cargo
        validarUnicidadYFechas(d);
    }

    public void validarUnicidadYFechas(Designacion d) {
        if (d.getCargo().getDivision() == null) {
            validarDesignacionSinDivision(d);
        } else {
            validarDesignacionConDivision(d);
        }
    }

    private boolean licenciasCubrenPeriodo(List<Licencia> licencias, LocalDateTime desde, LocalDateTime hasta) {
        if (licencias == null || licencias.isEmpty())
            return false;
        LocalDateTime actual = desde;
        while (!actual.isAfter(hasta)) {
            final LocalDateTime dia = actual; // variable efectivamente final para la lambda
            boolean cubierto = licencias.stream()
                    .anyMatch(l -> (l.getPedidoDesde().isEqual(dia) || l.getPedidoDesde().isBefore(dia)) &&
                            (l.getPedidoHasta().isEqual(dia) || l.getPedidoHasta().isAfter(dia)));
            if (!cubierto)
                return false;
            actual = actual.plusDays(1);
        }
        return true;
    }

    private void validarDesignacionSinDivision(Designacion d) {
        var existentes = repository.findDesignacionActivaOSolapada(
                d.getCargo().getNombre(), d.getFechaInicio(), d.getFechaFin(), d.getId());

        for (var e : existentes) {
            var licencias = licenciaRepository.findLicenciasEnPeriodo(
                    e.getId(), d.getFechaInicio(), d.getFechaFin());
            if (!licenciasCubrenPeriodo(licencias, d.getFechaInicio(), d.getFechaFin())) {
                throw new IllegalArgumentException(mensaje.getErrorDesignacionYaExisteCargo(
                        d.getPersona().getNombre(),
                        d.getPersona().getApellido(),
                        d.getCargo().getNombre(),
                        e.getPersona().getNombre(),
                        e.getPersona().getApellido()));
            }
        }
    }

    private void validarDesignacionConDivision(Designacion d) {
        var div = d.getCargo().getDivision();
        var existentes = repository.findSolapamientoEnDivision(
                d.getCargo().getNombre(),
                div.getAnio(),
                div.getNumDivision(),
                div.getTurno(),
                d.getFechaInicio(),
                d.getFechaFin(),
                d.getId());

        for (var e : existentes) {
            var licencias = licenciaRepository.findLicenciasEnPeriodo(
                    e.getId(), d.getFechaInicio(), d.getFechaFin());
            if (!licenciasCubrenPeriodo(licencias, d.getFechaInicio(), d.getFechaFin())) {
                throw new IllegalArgumentException(mensaje.getErrorDesignacionYaExisteEspacioCurricular(
                        d.getPersona().getNombre(),
                        d.getPersona().getApellido(),
                        d.getCargo().getNombre(),
                        div.getAnio(),
                        div.getNumDivision(),
                        div.getTurno(),
                        e.getPersona().getNombre(),
                        e.getPersona().getApellido()));
            }
        }
    }
}
