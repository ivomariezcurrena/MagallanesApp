package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import unpsjb.labprog.backend.model.TipoDesignacion;

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
        Designacion aDesignacionOrNull = service.findById(id);
        return (aDesignacionOrNull != null) ? Response.ok(
                aDesignacionOrNull)
                : Response.notFound("Designacion id " + id + " no encontrada");
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Designacion aDesignacion) {
        try {

            if (aDesignacion.getCargo() == null) {
                throw new IllegalArgumentException("El cargo no puede ser nulo");
            }
            Designacion savedDesignacion = service.save(aDesignacion);
            String mensaje;
            if (aDesignacion.getCargo().getTipoDesignacion() != TipoDesignacion.CARGO) {
                mensaje = String.format(
                        "%s %s ha sido designado/a a la asignatura %s a la división %dº %dº turno %s exitosamente",
                        savedDesignacion.getPersona().getNombre(),
                        savedDesignacion.getPersona().getApellido(),
                        savedDesignacion.getCargo().getNombre(),
                        savedDesignacion.getCargo().getDivision().getAnio(),
                        savedDesignacion.getCargo().getDivision().getNumDivision(),
                        savedDesignacion.getCargo().getDivision().getTurno());

            } else {
                mensaje = String.format("%s %s ha sido designado/a como %s exitosamente",
                        savedDesignacion.getPersona().getNombre(),
                        savedDesignacion.getPersona().getApellido(),
                        savedDesignacion.getCargo().getNombre());
            }
            return Response.ok(mensaje);
        } catch (DataIntegrityViolationException e) {
            Throwable root = e;
            while (root.getCause() != null && root.getCause() != root) {
                root = root.getCause();
            }
            return Response.dbError("Error de integridad: " + root.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // ESTO TE MUESTRA EL STACKTRACE REAL EN CONSOLA
            return Response.dbError("Error inesperado al guardar la designación: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Designacion aDesignacion) {
        try {
            service.save(aDesignacion);
            return Response.ok(aDesignacion);
        } catch (Exception e) {
            return Response.dbError("Error al actualizar la designacion");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Designacion aDesignacionOrNull = service.findById(id);
        if (aDesignacionOrNull != null) {
            service.delete(id);
            return Response.ok("Designacion " + id + " eliminada correctamente");
        } else {
            return Response.notFound("Designacion id " + id + " no encontrada");
        }
    }
}
