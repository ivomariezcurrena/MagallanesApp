package unpsjb.labprog.backend.business.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.model.Designacion;
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

    private void validarDesignacionSinDivision(Designacion d) {
        var existente = repository.findDesignacionActivaOSolapada(
                d.getCargo().getNombre(), d.getFechaInicio(), d.getFechaFin(), d.getId());

        if (existente.isPresent()) {
            var e = existente.get();
            throw new IllegalArgumentException(mensaje.getErrorDesignacionYaExisteCargo(
                    d.getPersona().getNombre(),
                    d.getPersona().getApellido(),
                    d.getCargo().getNombre(),
                    e.getPersona().getNombre(),
                    e.getPersona().getApellido()));
        }
    }

    private void validarDesignacionConDivision(Designacion d) {
        var div = d.getCargo().getDivision();
        var existente = repository.findSolapamientoEnDivision(
                d.getCargo().getNombre(),
                div.getAnio(),
                div.getNumDivision(),
                div.getTurno(),
                d.getFechaInicio(),
                d.getFechaFin(),
                d.getId());

        if (existente.isPresent()) {
            var e = existente.get();
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
