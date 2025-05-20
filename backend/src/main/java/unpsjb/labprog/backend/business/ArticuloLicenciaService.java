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
import unpsjb.labprog.backend.model.ArticuloLicencia;

@Service
public class ArticuloLicenciaService {
    @Autowired
    ArticuloLicenciaRepository repository;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

    public List<ArticuloLicencia> findAll() {
        List<ArticuloLicencia> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public ArticuloLicencia findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<ArticuloLicencia> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<ArticuloLicencia> findOneByArticulo(String articulo) {
        return repository.findByArticulo(articulo);
    }

    public List<ArticuloLicencia> searchByTerm(String term) {
        return repository.searchByTerm(term);
    }

    @Transactional
    public ArticuloLicencia save(ArticuloLicencia e) {
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public String getMensajeAgregar(ArticuloLicencia a) {
        return mensaje.getMensajeAgregarArticuloLicencia(a);
    }

    public String getMensajeActualizar(ArticuloLicencia a) {
        return mensaje.getMensajeActualizarArticuloLicencia(a);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeArticuloLicencia(id);
    }

    public String getMensajeNoEncontrado(int id) {
        return mensaje.getMensajeArticuloLicenciaNoEncontrado(id);
    }
}
