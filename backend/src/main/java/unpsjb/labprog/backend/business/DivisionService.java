package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.business.utils.Validador;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Turno;

@Service
public class DivisionService {
    @Autowired
    DivisionRepository repository;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

    public List<Division> findAll() {
        List<Division> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Division findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Division> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Division save(Division e) {
        validador.validar(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Page<Division> searchPage(String term, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String likeTerm = "%" + term.trim().toUpperCase() + "%";
        return repository.searchPage(likeTerm, pageable);
    }

    public List<String> findAllOrientaciones() {
        return repository.findAllOrientaciones();
    }

    public Division findByAnioNumeroTurno(int anio, int numDivision, Turno turno) {
        return repository
                .findByAnioNumDivisionAndTurno(anio, numDivision, turno)
                .orElse(null);
    }

    public List<Division> search(String term) {
        return repository.search("%" + term + "%");
    }

    public String getMensajeAgregar(Division d) {
        return mensaje.getMensajeAgregarDivision(d);
    }

    public String getMensajeActualizar(Division d) {
        return mensaje.getMensajeActualizarDivision(d);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeDivision(id);
    }

    public String getMensajeNoEncontrada(int id) {
        return mensaje.getMensajeDivisionNoEncontrada(id);
    }

}
