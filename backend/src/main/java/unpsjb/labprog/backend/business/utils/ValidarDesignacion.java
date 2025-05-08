package unpsjb.labprog.backend.business.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.TipoDesignacion;

@Component
public class ValidarDesignacion {

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
    }
}