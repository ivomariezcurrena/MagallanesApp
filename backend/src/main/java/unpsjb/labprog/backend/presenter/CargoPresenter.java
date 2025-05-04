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
import unpsjb.labprog.backend.model.Division;
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
        Cargo aCargoOrNull = service.findById(id);
        return (aCargoOrNull != null) ? Response.ok(aCargoOrNull)
                : Response.notFound("Cargo id " + id + " no encontrado");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Cargo cargo) {
        try {
            // 1) CARGO con división → 501
            if (cargo.getTipoDesignacion() == TipoDesignacion.CARGO
                    && cargo.getDivision() != null) {
                String msg = String.format(
                        "Cargo de %s es CARGO y no corresponde asignar división",
                        cargo.getNombre());
                return Response.notImplemented(null, msg);
            }

            // 2) ESPACIO_CURRICULAR sin división válida → 501
            if (cargo.getTipoDesignacion() == TipoDesignacion.ESPACIO_CURRICULAR
                    && (cargo.getDivision() == null
                            || cargo.getDivision().getId() == 0)) {
                String msg = String.format(
                        "Espacio Curricular %s falta asignar división",
                        cargo.getNombre());
                return Response.notImplemented(null, msg);
            }

            // 4) Guardar normalmente
            Cargo savedCargo = service.save(cargo);

            // 5) Armar mensaje 200
            String mensaje;
            if (savedCargo.getTipoDesignacion() == TipoDesignacion.CARGO) {
                mensaje = String.format("Cargo de %s ingresado correctamente",
                        savedCargo.getNombre());
            } else {
                Division d = savedCargo.getDivision();
                mensaje = String.format(
                        "Espacio Curricular %s para la división %dº %dº Turno %s ingresado correctamente",
                        savedCargo.getNombre(),
                        d.getAnio(),
                        d.getNumDivision(),
                        d.getTurno());
            }
            return Response.ok(mensaje);

        } catch (IllegalArgumentException e) {
            // Si tu service lanza este tipo de excepción para otros errores
            return ResponseEntity.status(501).body(e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error al designar el cargo");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Cargo aCargo) {
        try {
            Cargo updatedCargo = service.save(aCargo);
            String mensaje = String.format("Cargo %s actualizado correctamente", updatedCargo.getNombre());
            return Response.ok(mensaje);
        } catch (Exception e) {
            return Response.dbError("Error al actualizar el cargo");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            service.delete(id);
            return Response.ok("Cargo eliminado correctamente");
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
}