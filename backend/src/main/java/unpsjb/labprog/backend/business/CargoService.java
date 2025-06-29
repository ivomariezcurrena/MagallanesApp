package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.validaciones.MensajeFormateador;
import unpsjb.labprog.backend.business.validaciones.Validador;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.TipoDesignacion;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.utils.StringNormalizer;

@Service
public class CargoService {
    @Autowired
    CargoRepository repository;

    @Autowired
    @Lazy
    DivisionRepository divisionRepository;

    @Autowired
    DivisionService divisionService;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

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
        validador.validar(e);
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

    public List<Cargo> search(String term) {
        return repository.search("%" + term + "%");
    }

    public List<Cargo> buscarPorAnioYNumero(int anio, int numero) {
        Division division = divisionRepository.findByAnioNumeroDivision(anio, numero);
        List<Cargo> result = repository.findByDivision(division);
        return result;
    }

    public Cargo buscarPorNombreYTipo(String nombre, TipoDesignacion tipo) {
        return repository.findByNombreIgnoreCaseAndTipoDesignacion(nombre, tipo);
    }

    public Cargo buscarPorNombreTipoDivision(String nombre, TipoDesignacion tipo, int anio, int numero,
            Turno turno) {
        return repository.findByNombreTipoDivision(nombre, tipo, anio, numero, turno);
    }

    public List<Cargo> findByDivision(int anio, int numero) {
        Division division = divisionRepository.findByAnioNumeroDivision(anio, numero);
        return repository.findByDivision(division);
    }

    // MENSAJES
    public String getMensajeAgregar(Cargo c) {
        return mensaje.getMensajeAgregarCargo(c);
    }

    public String getMensajeActualizar(Cargo c) {
        return mensaje.getMensajeActualizarCargo(c);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeCargo(id);
    }

    public String getMensajeNoEncontrado(int id) {
        return mensaje.getMensajeCargoNoEncontrado(id);
    }
}