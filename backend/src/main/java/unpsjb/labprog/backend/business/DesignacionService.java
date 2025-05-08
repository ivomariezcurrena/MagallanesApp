package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.business.utils.Validador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Division;

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

    @Transactional
    public Designacion save(Designacion e) {
        validador.validar(e);
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
