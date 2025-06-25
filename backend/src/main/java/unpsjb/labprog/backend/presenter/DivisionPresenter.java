package unpsjb.labprog.backend.presenter;

import java.util.ArrayList;
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
import unpsjb.labprog.backend.business.CargoService;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.business.horarioUtils.HorarioFactory;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Horario;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.utils.Horarios;

@RestController
@RequestMapping("divisiones")
public class DivisionPresenter {
    @Autowired
    DivisionService service;

    @Autowired
    HorarioFactory horarioFactory;

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
            Division saved = service.save(aDivision);
            return Response.ok(service.getMensajeAgregar(saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(501).body(e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error al guardar la división");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Division aDivision) {
        try {
            Division updated = service.save(aDivision);
            return Response.ok(service.getMensajeActualizar(updated));
        } catch (Exception e) {
            return Response.dbError("Error al actualizar la división");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Division d = service.findById(id);
        if (d != null) {
            service.delete(id);
            return Response.ok(service.getMensajeEliminacion(id));
        } else {
            return Response.notFound(service.getMensajeNoEncontrada(id));
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Object> searchPage(
            @RequestParam("term") String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return Response.ok(service.searchPage(term, page, size));
    }

    @RequestMapping(value = "/orientaciones", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrientaciones() {
        return Response.ok(service.findAllOrientaciones());
    }

    @RequestMapping(value = "/buscar-por-anio-numero-turno", method = RequestMethod.GET)
    public ResponseEntity<Object> buscarPorAnioNumeroTurno(
            @RequestParam int anio,
            @RequestParam int numero,
            @RequestParam Turno turno) {

        Division d = service.findByAnioNumeroTurno(anio, numero, turno);
        if (d != null) {
            return Response.ok(d);
        } else {
            return Response.notFound(
                    String.format("No se encontró división %dº %dº turno %s", anio, numero, turno));
        }
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/{id}/horario", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerHorario(@PathVariable("id") int id) {
        return Response.ok(horarioFactory.crearHorarios(id));
    }
}
