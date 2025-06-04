package unpsjb.labprog.backend.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.business.utils.Validador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class LicenciaService {
    @Autowired
    LicenciaRepository repository;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

    @Autowired
    @Lazy
    DesignacionRepository designacionRepository;

    public List<Licencia> findAll() {
        List<Licencia> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Licencia findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Licencia> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Page<Licencia> findAllValidas(int page, int size) {
        return repository.findAllValidas(PageRequest.of(page, size));
    }

    @Transactional
    public Licencia save(Licencia e) {
        validador.validar(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public String getMensajeAgregar(Licencia l) {
        return mensaje.getMensajeLicenciaOtorgada(l);
    }

    public String getMensajeActualizar(Licencia l) {
        return mensaje.getMensajeActualizarLicencia(l);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeLicencia(id);
    }

    public String getMensajeNoEncontrada(int id) {
        return mensaje.getMensajeLicenciaNoEncontrada(id);
    }

    public Page<Licencia> findAllDesdeFecha(LocalDateTime fechaDesde, int page, int size) {
        return repository.findAllDesdeFecha(fechaDesde, PageRequest.of(page, size));
    }

    public List<Licencia> findLicenciasVigentesEnFecha(LocalDateTime fecha) {
        return repository.findLicenciasVigentesEnFecha(fecha);
    }

    /**
     * Busca la designación suplente asociada a una licencia.
     *
     * @param licenciaId ID de la licencia para la cual se busca el suplente.
     * @return La primera {@link Designacion} suplente encontrada, o {@code null} si
     *         no existe.
     */
    public Designacion findDesignacionSuplente(int licenciaId) {
        Licencia licencia = findById(licenciaId);
        if (!esLicenciaValida(licencia)) {
            return null;
        }

        Designacion designacionOriginal = obtenerPrimeraDesignacion(licencia);
        if (designacionOriginal == null) {
            return null;
        }

        List<Designacion> suplentes = buscarSuplentes(licencia, designacionOriginal);
        return suplentes.isEmpty() ? null : suplentes.get(0);
    }

    private boolean esLicenciaValida(Licencia licencia) {
        return licencia != null && licencia.getDesignaciones() != null && !licencia.getDesignaciones().isEmpty();
    }

    private Designacion obtenerPrimeraDesignacion(Licencia licencia) {
        return licencia.getDesignaciones().stream().findFirst().orElse(null);
    }

    private List<Designacion> buscarSuplentes(Licencia licencia, Designacion designacionOriginal) {
        return designacionRepository.findDesignacionesSuplentes(
                designacionOriginal.getCargo().getId(),
                licencia.getPedidoDesde(),
                licencia.getPedidoHasta(),
                designacionOriginal.getId());
    }
}
