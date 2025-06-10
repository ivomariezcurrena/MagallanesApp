package unpsjb.labprog.backend.presenter;

import java.time.LocalDateTime;

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
import unpsjb.labprog.backend.business.LicenciaService;
import unpsjb.labprog.backend.business.utils.vlicencias.AgregarLog;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

@RestController
@RequestMapping("licencias")
public class LicenciaPresenter {
    @Autowired
    LicenciaService service;

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

    @RequestMapping(value = "/page/validas", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPageValidas(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return Response.ok(service.findAllValidas(page, size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Licencia l = service.findById(id);
        return (l != null)
                ? Response.ok(l)
                : Response.notFound(service.getMensajeNoEncontrada(id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Licencia aLicencia) {
        try {
            Licencia saved = service.save(aLicencia);
            Licencia completa = service.findById(saved.getId());
            String ultimoLog = AgregarLog.obtenerMensajeUltimoLog(completa);
            if (completa.getEstado() == Estado.Invalido) {
                return Response.internalServerError(ultimoLog, completa);
            }
            return Response.ok(completa, ultimoLog);

        } catch (IllegalArgumentException e) {
            return Response.internalServerError(null, e.getMessage());
        } catch (Exception e) {
            return Response.dbError("Error inesperado al guardar la licencia: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Licencia aLicencia) {
        try {
            aLicencia.setEstado(Estado.Valido);
            Licencia updated = service.save(aLicencia);
            Licencia completa = service.findById(updated.getId());
            return Response.ok(completa);
        } catch (Exception e) {
            return Response.dbError("Error al actualizar la licencia");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Licencia l = service.findById(id);
        if (l != null) {
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

    @RequestMapping(value = "/desde", method = RequestMethod.GET)
    public ResponseEntity<Object> findAllDesdeFecha(
            @RequestParam("fecha") String fecha,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            LocalDateTime fechaDesde = LocalDateTime.parse(fecha);
            return Response.ok(service.findAllDesdeFecha(fechaDesde, page, size));
        } catch (Exception e) {
            return Response.internalServerError(null, "Formato de fecha inválido. Use yyyy-MM-ddTHH:mm:ss");
        }
    }

    @GetMapping("/vigentes-en-fecha")
    public ResponseEntity<Object> getLicenciasVigentesEnFecha(@RequestParam("fecha") String fecha) {
        try {
            LocalDateTime fechaConsulta = LocalDateTime.parse(fecha);
            return Response.ok(service.findLicenciasVigentesEnFecha(fechaConsulta));
        } catch (Exception e) {
            return Response.internalServerError(null, "Formato de fecha inválido. Use yyyy-MM-ddTHH:mm:ss");
        }
    }

    @GetMapping("/{id}/suplente")
    public ResponseEntity<Object> getPrimerSuplenteDeLicencia(@PathVariable("id") int id) {
        return Response.ok(service.findDesignacionSuplente(id));
    }

    @GetMapping("/anio")
    public ResponseEntity<Object> findAllByAAnio(
            @RequestParam("anio") String fechaDesde) {
        // Aquí parseas la fecha y llamas al servicio
        // Ejemplo:
        try {
            LocalDateTime fecha = LocalDateTime.parse(fechaDesde);
            return Response.ok(service.findAllAnio(fecha));
        } catch (Exception e) {
            return Response.internalServerError(null, "Formato de fecha inválido. Use yyyy-MM-ddTHH:mm:ss");
        }
    }

    @RequestMapping(value = "/reporte-concepto", method = RequestMethod.GET)
    public ResponseEntity<Object> reporteConcepto(
            @RequestParam("anio") String fechaDesde,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        try {
            LocalDateTime fecha = LocalDateTime.parse(fechaDesde);
            return Response.ok(service.reporteDeConcepto(fecha, page, size));
        } catch (Exception e) {
            return Response.internalServerError(null, "Error al generar el reporte de concepto: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/validas", method = RequestMethod.GET)
    public ResponseEntity<Object> getLicenciasValidas(
            @RequestParam("anio") String anio) {
        try {
            int aAnio = Integer.parseInt(anio.substring(0, 4));
            return Response.ok(service.getValidas(aAnio));
        } catch (Exception e) {
            return Response.internalServerError(null, "Error al obtener las licencias validas: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/invalidas", method = RequestMethod.GET)
    public ResponseEntity<Object> getLicenciasInvalidas(
            @RequestParam("anio") String anio) {
        try {
            int aAnio = Integer.parseInt(anio.substring(0, 4));
            return Response.ok(service.getInvalidas(aAnio));
        } catch (Exception e) {
            return Response.internalServerError(null, "Error al obtener las licencias inválidas: " + e.getMessage());
        }
    }
}
