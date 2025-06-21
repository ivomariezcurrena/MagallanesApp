package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.TipoDesignacion;

public class ValidacionesDeFormato implements DesignacionValidable {
    @Override
    public void validar(Designacion designacion) {
        if (designacion.getCargo() == null) {
            throw new IllegalArgumentException("El cargo no puede ser nulo");
        }

        if (designacion.getPersona() == null) {
            throw new IllegalArgumentException("La persona no puede ser nula");
        }

        if (designacion.getCargo().getTipoDesignacion() == TipoDesignacion.ESPACIO_CURRICULAR
                && designacion.getCargo().getDivision() == null) {
            throw new IllegalArgumentException("El espacio curricular debe tener una división asignada");
        }

    }
}
