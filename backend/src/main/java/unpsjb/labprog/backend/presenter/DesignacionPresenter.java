package unpsjb.labprog.backend.presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.model.Designacion;

@RestController
@RequestMapping("designaciones")
public class DesignacionPresenter {
    @Autowired
    DesignacionService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Designacion d = service.findById(id);
        return (d != null)
                ? Response.ok(d)
                : Response.notFound(service.getMensajeNoEncontrada(id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Designacion aDesignacion) {
        try {
            Designacion saved = service.save(aDesignacion);
            return Response.ok(saved, service.getMensajeAgregar(saved));
        } catch (IllegalArgumentException e) {
            return Response.internalServerError(e.getMessage(), e);
        } catch (Exception e) {
            return Response.dbError("Error inesperado al guardar la designación: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Designacion aDesignacion) {
        try {
            Designacion updated = service.save(aDesignacion);
            return Response.ok(service.getMensajeActualizar(updated));
        } catch (IllegalArgumentException e) {
            return Response.notImplemented(null, e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error al actualizar la designación");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Designacion d = service.findById(id);
        if (d != null) {
            try {
                service.delete(id);
                return Response.ok(service.getMensajeEliminacion(id));
            } catch (Exception e) {
                return Response.dbError("Error al eliminar la designación");
            }
        } else {
            return Response.notFound(service.getMensajeNoEncontrada(id));
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Object> search(
            @RequestParam("term") String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return Response.ok(service.search(term, page, size));
    }

}
