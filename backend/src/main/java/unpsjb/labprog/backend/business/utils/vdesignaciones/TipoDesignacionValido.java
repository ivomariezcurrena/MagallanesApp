package unpsjb.labprog.backend.business.utils.vdesignaciones;

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
