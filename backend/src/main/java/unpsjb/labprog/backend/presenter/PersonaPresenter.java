package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PersonaService;
import unpsjb.labprog.backend.model.Persona;

@RestController
@RequestMapping("personas")
public class PersonaPresenter {
    @Autowired
    PersonaService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());

    }

    @RequestMapping(value = "/{dni}", method = RequestMethod.GET)
    public ResponseEntity<Object> findByDni(@PathVariable("dni") int dni) {
        Persona aPersonaOrNull = service.findByDni(dni);
        return (aPersonaOrNull != null) ? Response.ok(aPersonaOrNull)
                : Response.notFound("Persona dni " + dni + " no encontrada");
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Persona aPersona) {
        try {
            Persona savedPersona = service.save(aPersona);
            // Formatear el mensaje según lo requerido en Persona.feature
            String mensaje = String.format("%s %s con DNI %d ingresado/a correctamente",
                    savedPersona.getNombre(),
                    savedPersona.getApellido(),
                    savedPersona.getDni());

            return Response.ok(mensaje);
        } catch (DataIntegrityViolationException e) {
            return Response.dbError("No se puede utilizar ese dni porque ya existe otra persona con el mismo");
        }
    }
}
