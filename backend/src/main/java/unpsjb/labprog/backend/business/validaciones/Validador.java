package unpsjb.labprog.backend.business.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class Validador {
    private final Map<Class<?>, ValidadorGenerico<?>> validadores = new HashMap<>();

    @Autowired
    ValidarPersona validarPersona;

    @Autowired
    ValidarDivision validarDivision;

    @Autowired
    ValidarCargo validarCargo;

    @Autowired
    ValidarDesignacion validarDesignacion;

    @Autowired
    ValidarLicencia validarLicencia;

    @PostConstruct
    public void init() {
        validadores.put(unpsjb.labprog.backend.model.Persona.class, validarPersona);
        validadores.put(unpsjb.labprog.backend.model.Division.class, validarDivision);
        validadores.put(unpsjb.labprog.backend.model.Cargo.class, validarCargo);
        validadores.put(unpsjb.labprog.backend.model.Designacion.class, validarDesignacion);
        validadores.put(unpsjb.labprog.backend.model.Licencia.class, validarLicencia);
    }

    @SuppressWarnings("unchecked")
    public <T> void validar(T entidad) {
        ValidadorGenerico<T> validador = (ValidadorGenerico<T>) validadores.get(entidad.getClass());
        if (validador == null) {
            throw new IllegalArgumentException("No hay validador para " + entidad.getClass());
        }
        validador.validar(entidad);
    }
}
