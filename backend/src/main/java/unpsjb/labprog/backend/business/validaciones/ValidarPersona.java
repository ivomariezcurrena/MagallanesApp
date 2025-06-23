package unpsjb.labprog.backend.business.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.PersonaService;
import unpsjb.labprog.backend.model.Persona;

@Component
public class ValidarPersona implements ValidadorGenerico<Persona> {

    @Autowired
    @Lazy
    PersonaService service;

    public void validar(Persona persona) {
        if (persona.getDni() <= 0) {
            throw new IllegalArgumentException("El DNI debe ser un número positivo");
        }

        if (service.findByDni(persona.getDni()) != null) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (persona.getApellido() == null || persona.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }

    }
}