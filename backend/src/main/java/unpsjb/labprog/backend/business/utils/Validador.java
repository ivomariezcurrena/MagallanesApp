package unpsjb.labprog.backend.business.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Persona;

@Component
public class Validador {
    @Autowired
    ValidarPersona validarPersona;

    @Autowired
    ValidarDivision validarDivision;

    @Autowired
    ValidarCargo validarCargo;

    @Autowired
    ValidarDesignacion validarDesignacion;

    public void validar(Object entidad) {
        if (entidad instanceof Persona persona) {
            validarPersona.validar(persona);
        }
        if (entidad instanceof Division division) {
            validarDivision.validar(division);
        }
        if (entidad instanceof Cargo cargo) {
            validarCargo.validar(cargo);
        }
        if (entidad instanceof Designacion designacion) {
            validarDesignacion.validar(designacion);
        }
    }
}
