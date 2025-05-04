package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Division;

@Service
public class DesignacionService {

    @Autowired
    DesignacionRepository repository;
    @Autowired
    DivisionService divisionService;

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
        validarCargo(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public void validarCargo(Designacion e) {
        if (e.getCargo() != null && e.getCargo().getDivision() != null) {
            int idDivision = e.getCargo().getDivision().getId();
            Division divisionCompleta = divisionService.findById(idDivision);
            e.getCargo().setDivision(divisionCompleta);
        }
    }
}
