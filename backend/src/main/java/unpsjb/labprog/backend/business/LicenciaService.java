package unpsjb.labprog.backend.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.validaciones.Validador;
import unpsjb.labprog.backend.business.validaciones.vlicencias.LicenciaHelper;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.utils.StringNormalizer;

@Service
public class LicenciaService {
    @Autowired
    LicenciaRepository repository;

    @Autowired
    Validador validador;

    @Autowired
    LicenciaHelper licenciaHelper;

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
        licenciaHelper.cargarEntidades(e);
        licenciaHelper.cargarDesignaciones(e);
        validador.validar(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Page<Licencia> findAllDesdeFecha(LocalDateTime fechaDesde, int page, int size) {
        return repository.findAllDesdeFecha(fechaDesde, PageRequest.of(page, size));
    }

    public List<Licencia> findLicenciasVigentesEnFecha(LocalDateTime fecha) {
        return repository.findLicenciasVigentesEnFecha(fecha);
    }

    public List<Licencia> findAllAnio(LocalDateTime fechaDesde) {
        LocalDateTime inicioAnio = fechaDesde.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime inicioSiguienteAnio = inicioAnio.plusYears(1);
        return repository.findAllAnio(inicioAnio, inicioSiguienteAnio);
    }

    public List<Licencia> getValidas(int anio) {
        return repository.getValidas(anio);
    }

    public List<Licencia> getInvalidas(int anio) {
        return repository.getInvalidas(anio);
    }

    public Page<Licencia> search(String term, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String likeTerm = "%" + StringNormalizer.normalize(term) + "%";
        return repository.search(likeTerm, pageable);
    }

}
