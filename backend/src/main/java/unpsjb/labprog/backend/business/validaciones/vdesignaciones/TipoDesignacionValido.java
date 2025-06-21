package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TipoDesignacionValido {
    Tipo tipo();

    enum Tipo {
        CON_DIVISION,
        SIN_DIVISION
    }
}
