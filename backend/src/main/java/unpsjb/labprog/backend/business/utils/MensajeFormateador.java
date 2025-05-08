package unpsjb.labprog.backend.business.utils;

import unpsjb.labprog.backend.model.Persona;

public class MensajeFormateador {

    // MENSAJES DE PERSONA
    public String getMensajeExito(Persona persona) {
        String mensaje = String.format("%s %s con DNI %d actualizado/a correctamente",
                persona.getNombre(),
                persona.getApellido(),
                persona.getDni());
        return mensaje;
    }
}
