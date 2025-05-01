package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.model.TipoDesignacion;
import unpsjb.labprog.backend.utils.StringNormalizer;

@Service
public class CargoService {
    @Autowired
    CargoRepository repository;

    @Autowired
    private DivisionService divisionService;

    public List<Cargo> findAll() {
        List<Cargo> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Cargo findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Cargo> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Cargo save(Cargo e) {
        validarCargo(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Page<Cargo> search(String term, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String likeTerm = "%" + StringNormalizer.normalize(term) + "%";
        return repository.search(likeTerm, pageable);
    }

    private void validarCargo(Cargo cargo) {
        if (cargo.getTipoDesignacion() == TipoDesignacion.CARGO && cargo.getDivision() != null) {
            throw new IllegalArgumentException("Un cargo de tipo CARGO no debe tener una división asignada.");
        }
        if (cargo.getTipoDesignacion() == TipoDesignacion.ESPACIO_CURRICULAR
                && cargo.getDivision() != null
                && cargo.getDivision().getId() > 0) {
            Division fullDiv = divisionService.findById(cargo.getDivision().getId());
            if (fullDiv == null) {
                throw new IllegalArgumentException("División id " + cargo.getDivision().getId() + " no encontrada");
            }
            cargo.setDivision(fullDiv);
        } else {
            cargo.setDivision(null);
        }
    }
}