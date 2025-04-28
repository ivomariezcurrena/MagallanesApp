package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.Division;
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
}
