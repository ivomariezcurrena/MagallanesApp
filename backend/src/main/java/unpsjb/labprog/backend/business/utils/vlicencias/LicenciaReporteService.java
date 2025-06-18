package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.reporteUtils.ReportePersonaFactory;
import unpsjb.labprog.backend.utils.ReportePersona;

@Service
public class LicenciaReporteService {
    @Autowired
    private DesignacionRepository designacionRepository;
    @Autowired
    private ReportePersonaFactory reportePersonaFactory;

    public Page<ReportePersona> reporteDeConcepto(LocalDateTime fechaDesde, int page, int size) {
        int anio = fechaDesde.getYear();
        Page<Integer> dnisPage = designacionRepository.findPersonasConDesignacionEnAnio(anio,
                PageRequest.of(page, size));
        List<ReportePersona> reporte = dnisPage.stream()
                .map(dni -> reportePersonaFactory.crearReportePersona(dni, anio))
                .collect(Collectors.toList());
        return new PageImpl<>(reporte, dnisPage.getPageable(), dnisPage.getTotalElements());
    }
}
