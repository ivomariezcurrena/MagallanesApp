package unpsjb.labprog.backend.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.business.utils.Validador;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class LicenciaService {
    @Autowired
    LicenciaRepository repository;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

    public List<Licencia> findAll() {
        List<Licencia> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Licencia findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Licencia> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Page<Licencia> findAllValidas(int page, int size) {
        return repository.findAllValidas(PageRequest.of(page, size));
    }

    @Transactional
    public Licencia save(Licencia e) {
        validador.validar(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public String getMensajeAgregar(Licencia l) {
        return mensaje.getMensajeLicenciaOtorgada(l);
    }

    public String getMensajeActualizar(Licencia l) {
        return mensaje.getMensajeActualizarLicencia(l);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeLicencia(id);
    }

    public String getMensajeNoEncontrada(int id) {
        return mensaje.getMensajeLicenciaNoEncontrada(id);
    }

    public Page<Licencia> findAllDesdeFecha(LocalDateTime fechaDesde, int page, int size) {
        return repository.findAllDesdeFecha(fechaDesde, PageRequest.of(page, size));
    }

}
