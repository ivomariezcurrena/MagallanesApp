package unpsjb.labprog.backend.business.utils.vlicencias;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ArticuloValido {
    String[] value();
}