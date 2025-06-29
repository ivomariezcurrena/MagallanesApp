package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.business.validaciones.MensajeFormateador;
import unpsjb.labprog.backend.business.validaciones.Validador;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.utils.StringNormalizer;

import org.springframework.data.domain.Pageable;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository repository;

    @Autowired
    MensajeFormateador mensaje;

    @Autowired
    Validador validador;

    public List<Persona> findAll() {
        List<Persona> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Persona findByDni(int dni) {
        return repository.findById(dni).orElse(null);
    }

    public Page<Persona> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Persona save(Persona e) {
        validador.validar(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int dni) {
        repository.deleteById(dni);
    }

    public Page<Persona> searchPage(String term, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String likeTerm = "%" + StringNormalizer.normalize(term) + "%";
        return repository.search(likeTerm, pageable);
    }

    public List<Persona> search(String term) {
        return repository.search("%" + term + "%");
    }

    public String getMensajeAgregar(Persona persona) {
        return mensaje.getMensajeAgregarPersona(persona);
    }

    public String getMensajeActualizar(Persona persona) {
        return mensaje.getMensajeActualizarPersona(persona);
    }

    public String getMensajeEliminar(int dni) {
        return mensaje.getMensajeEliminarPersona(dni);
    }

    public String getMensajeNoEncontrada(int dni) {
        return mensaje.getMensajePersonaNoEncontrada(dni);
    }

    public String getMensajeDniDuplicado() {
        return mensaje.getMensajeDniDuplicado();
    }
}
