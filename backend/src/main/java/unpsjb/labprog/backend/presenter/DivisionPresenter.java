package unpsjb.labprog.backend.presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.model.Division;

@RestController
@RequestMapping("divisiones")

public class DivisionPresenter {
    @Autowired
    DivisionService service;

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
        Division aDivisionOrNull = service.findById(id);
        return (aDivisionOrNull != null) ? Response.ok(aDivisionOrNull)
                : Response.notFound("Division id " + id + " no encontrada");
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Division aDivision) {
        try {
            Division savedDivision = service.save(aDivision);
            String mensaje = String.format("División %d %d turno %s ingresada correctamente",
                    savedDivision.getAnio(),
                    savedDivision.getNumDivision(),
                    savedDivision.getTurno());
            return Response.ok(mensaje);
        } catch (Exception e) {
            return Response.dbError("Error al guardar la división");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Division aDivision) {
        try {
            Division updatedDivision = service.save(aDivision);
            String mensaje = String.format("División %d %d turno %s actualizada correctamente",
                    updatedDivision.getAnio(),
                    updatedDivision.getNumDivision(),
                    updatedDivision.getTurno());
            return Response.ok(mensaje);
        } catch (Exception e) {
            return Response.dbError("Error al actualizar la división");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Division aDivisionOrNull = service.findById(id);
        if (aDivisionOrNull != null) {
            service.delete(id);
            return Response.ok("División " + id + " eliminada correctamente");
        } else {
            return Response.notFound("División id " + id + " no encontrada");
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Object> search(
            @RequestParam("term") String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return Response.ok(service.search(term, page, size));
    }

    @RequestMapping(value = "/orientaciones", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrientaciones() {
        return Response.ok(service.findAllOrientaciones());
    }
}
