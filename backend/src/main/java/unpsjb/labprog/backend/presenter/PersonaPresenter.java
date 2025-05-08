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
import org.springframework.web.bind.annotation.RequestParam;

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
        Persona persona = service.findByDni(dni);
        return (persona != null)
                ? Response.ok(persona)
                : Response.notFound(service.getMensajeNoEncontrada(dni));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Persona aPersona) {
        try {
            Persona saved = service.save(aPersona);
            return Response.ok(service.getMensajeAgregar(saved));
        } catch (IllegalArgumentException e) {
            return Response.notImplemented(null, service.getMensajeNoEncontrada(0));
        } catch (DataIntegrityViolationException e) {
            return Response.dbError(service.getMensajeDniDuplicado());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Persona aPersona) {
        try {
            Persona updated = service.save(aPersona);
            return Response.ok(service.getMensajeActualizar(updated));
        } catch (DataIntegrityViolationException e) {
            return Response.dbError("Error al actualizar persona: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{dni}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("dni") int dni) {
        Persona persona = service.findByDni(dni);
        if (persona == null) {
            return Response.notFound(service.getMensajeNoEncontrada(dni));
        }
        service.delete(dni);
        return Response.ok(service.getMensajeEliminar(dni));
    }

    // este metodo es para buscar el termino puesto en la barra de busqueda,
    // devuelve la pagina con los resultados
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Object> searchPage(
            @RequestParam("term") String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return Response.ok(service.searchPage(term, page, size));
    }

    // busca por dni
    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }
}