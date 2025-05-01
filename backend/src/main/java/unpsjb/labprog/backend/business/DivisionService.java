package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.utils.StringNormalizer;

@Service
public class DivisionService {
    @Autowired
    DivisionRepository repository;

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
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Page<Division> search(String term, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String likeTerm = "%" + term.trim().toUpperCase() + "%";
        return repository.search(likeTerm, pageable);
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

}
