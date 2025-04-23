package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Persona;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository repository;

    public List<Persona> findAll() {
        List<Persona> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Persona findByDni(int dni) {
        return repository.findById(dni).orElse(null);
    }

    @Transactional
    public Persona save(Persona e) {
        return repository.save(e);
    }
}
