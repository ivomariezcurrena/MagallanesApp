package unpsjb.labprog.backend.business.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Persona;

@Component
public class ValidarPersona {

    public void validar(Persona persona) {
        if (persona.getDni() <= 0) {
            throw new IllegalArgumentException("El DNI debe ser un número positivo");
        }

        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (persona.getApellido() == null || persona.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }

    }
}