package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.CargoService;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.TipoDesignacion;
import unpsjb.labprog.backend.model.Turno;

@RestController
@RequestMapping("cargos")
public class CargoPresenter {
    @Autowired
    CargoService service;

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
        Cargo c = service.findById(id);
        return (c != null) ? Response.ok(c)
                : Response.notFound(service.getMensajeNoEncontrado(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Cargo cargo) {
        try {
            Cargo saved = service.save(cargo);
            return Response.ok(service.getMensajeAgregar(saved));
        } catch (IllegalArgumentException e) {
            // <- VALIDACIÓN personalizada
            return Response.notImplemented(null, e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error al designar el cargo");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Cargo aCargo) {
        try {
            Cargo updated = service.save(aCargo);
            return Response.ok(service.getMensajeActualizar(updated));
        } catch (Exception e) {
            return Response.dbError("Error al actualizar el cargo");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            service.delete(id);
            return Response.ok(service.getMensajeEliminacion(id));
        } catch (Exception e) {
            return Response.dbError("Error al eliminar el cargo");
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Object> search(
            @RequestParam("term") String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return Response.ok(service.search(term, page, size));
    }

    @GetMapping("/buscar-por-nombre-y-tipo")
    public ResponseEntity<Object> buscarPorNombreYTipo(
            @RequestParam String nombre,
            @RequestParam TipoDesignacion tipo) {

        Cargo cargo = service.buscarPorNombreYTipo(nombre, tipo);
        if (cargo != null) {
            return Response.ok(cargo);
        } else {
            return Response.notFound("No se encontró un cargo con ese nombre y tipo");
        }
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @GetMapping("/buscar-por-nombre-tipo-division")
    public ResponseEntity<Object> buscarPorNombreTipoDivision(
            @RequestParam String nombre,
            @RequestParam TipoDesignacion tipo,
            @RequestParam int anio,
            @RequestParam int numero,
            @RequestParam Turno turno) {

        Cargo cargo = service.buscarPorNombreTipoDivision(nombre, tipo, anio, numero, turno);
        if (cargo != null) {
            return Response.ok(cargo);
        } else {
            return Response.notFound("No se encontró un cargo con ese nombre, tipo y división");
        }
    }
}