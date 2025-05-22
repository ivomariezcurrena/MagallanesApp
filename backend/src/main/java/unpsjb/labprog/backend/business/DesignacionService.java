package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.business.utils.Validador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class DesignacionService {

    @Autowired
    DesignacionRepository repository;

    @Autowired
    DivisionService divisionService;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

    @Autowired
    LicenciaRepository licenciaRepository;

    public List<Designacion> findAll() {
        List<Designacion> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Designacion findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Designacion> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Designacion findByCargo(String cargo) {
        return repository.findByNombreDeCargo(cargo);
    }

    public List<Designacion> findByPersonaDni(int dni) {
        return repository.findByPersonaDni(dni);
    }

    @Transactional
    public Designacion save(Designacion e) {
        validador.validar(e);

        // Buscar designación solapada
        Optional<Designacion> solapada = repository.findDesignacionActivaOSolapada(
                e.getCargo().getNombre(),
                e.getFechaInicio(),
                e.getFechaFin(),
                e.getId() == 0 ? null : e.getId());

        if (solapada.isPresent()) {
            // Buscar licencias en ese periodo para la designación solapada
            List<Licencia> licencias = licenciaRepository.findLicenciasEnPeriodo(
                    solapada.get().getId(),
                    e.getFechaInicio(),
                    e.getFechaFin());
            if (licencias.isEmpty()) {
                throw new IllegalArgumentException("Ya existe una designación activa o solapada en ese periodo.");
            }
        }

        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public String getMensajeAgregar(Designacion d) {
        return mensaje.getMensajeAgregarDesignacion(d);
    }

    public String getMensajeActualizar(Designacion d) {
        return mensaje.getMensajeActualizarDesignacion(d);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeDesignacion(id);
    }

    public String getMensajeNoEncontrada(int id) {
        return mensaje.getMensajeDesignacionNoEncontrada(id);
    }
}
