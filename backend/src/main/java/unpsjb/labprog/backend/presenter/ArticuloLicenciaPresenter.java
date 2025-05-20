package unpsjb.labprog.backend.presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ArticuloLicenciaService;
import unpsjb.labprog.backend.model.ArticuloLicencia;

@RestController
@RequestMapping("articulolicencias")
public class ArticuloLicenciaPresenter {

    @Autowired
    ArticuloLicenciaService service;

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

    @RequestMapping(value = "buscar/{articulo}", method = RequestMethod.GET)
    public ResponseEntity<Object> findByArticulo(@PathVariable("articulo") String articulo) {
        try {
            List<ArticuloLicencia> resultado = service.findOneByArticulo(articulo);
            if (resultado == null) {
                return Response.notFound("No se encontró el artículo con el valor: " + articulo);
            }
            return Response.ok(resultado);
        } catch (Exception e) {
            return Response.dbError("Error al buscar por artículo: " + e.getMessage());
        }
    }

    @RequestMapping(value = "search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        try {
            List<ArticuloLicencia> resultado = service.searchByTerm(term);
            return Response.ok(resultado);
        } catch (Exception e) {
            return Response.dbError("Error al buscar por artículo: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        ArticuloLicencia a = service.findById(id);
        return (a != null)
                ? Response.ok(a)
                : Response.notFound(service.getMensajeNoEncontrado(id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ArticuloLicencia articuloLicencia) {
        try {
            ArticuloLicencia saved = service.save(articuloLicencia);
            return Response.ok(service.getMensajeAgregar(saved));
        } catch (IllegalArgumentException e) {
            return Response.internalServerError(null, e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error inesperado al guardar el artículo: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody ArticuloLicencia articuloLicencia) {
        try {
            ArticuloLicencia updated = service.save(articuloLicencia);
            return Response.ok(service.getMensajeActualizar(updated));
        } catch (IllegalArgumentException e) {
            return Response.notImplemented(null, e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error al actualizar el artículo");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        ArticuloLicencia a = service.findById(id);
        if (a != null) {
            try {
                service.delete(id);
                return Response.ok(service.getMensajeEliminacion(id));
            } catch (Exception e) {
                return Response.dbError("Error al eliminar el artículo");
            }
        } else {
            return Response.notFound(service.getMensajeNoEncontrado(id));
        }
    }
}
