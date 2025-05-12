package unpsjb.labprog.backend.business.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.CargoService;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.TipoDesignacion;

@Component
public class ValidarDesignacion {

    @Autowired
    @Lazy
    DesignacionService service;

    @Autowired
    @Lazy
    CargoService cargoService;

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

        Designacion designacionCargoExistente = service.findByCargo(d.getCargo().getNombre());
        if (d.getCargo().getDivision() == null) {
            if (designacionCargoExistente != null) {
                throw new IllegalArgumentException(mensaje.getErrorDesignacionYaExisteCargo(d.getPersona().getNombre(),
                        d.getPersona().getApellido(), d.getCargo().getNombre(),
                        designacionCargoExistente.getPersona().getNombre(),
                        designacionCargoExistente.getPersona().getApellido()));
            }
        } else if (designacionCargoExistente.getCargo().getDivision().getAnio() == d.getCargo().getDivision()
                .getAnio()
                && designacionCargoExistente.getCargo().getDivision().getNumDivision() == d.getCargo()
                        .getDivision().getNumDivision()
                && designacionCargoExistente.getCargo().getDivision().getTurno() == d.getCargo()
                        .getDivision().getTurno()) {
            throw new IllegalArgumentException(mensaje.getErrorDesignacionYaExisteEspacioCurricular(
                    d.getPersona().getNombre(),
                    d.getPersona().getApellido(), d.getCargo().getNombre(),
                    d.getCargo().getDivision().getAnio(),
                    d.getCargo().getDivision().getNumDivision(),
                    d.getCargo().getDivision().getTurno(),
                    designacionCargoExistente.getPersona().getNombre(),
                    designacionCargoExistente.getPersona().getApellido()));
        }

    }

}
