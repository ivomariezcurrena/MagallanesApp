package unpsjb.labprog.backend.business.validaciones.vlicencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.LicenciaService;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

import java.util.List;

@Service
public class SuplenteService {

    @Autowired
    DesignacionRepository designacionRepository;

    @Autowired
    @Lazy
    LicenciaService licenciaService;

    /**
     * Busca la designación suplente asociada a una licencia.
     *
     * @param licenciaId ID de la licencia para la cual se busca el suplente.
     * @return La primera {@link Designacion} suplente encontrada, o {@code null} si
     *         no existe.
     */
    public Designacion findDesignacionSuplente(int licenciaId) {
        Licencia licencia = licenciaService.findById(licenciaId);
        if (!esLicenciaValida(licencia)) {
            return null;
        }

        Designacion designacionOriginal = obtenerPrimeraDesignacion(licencia);
        if (designacionOriginal == null) {
            return null;
        }

        List<Designacion> suplentes = buscarSuplentes(licencia, designacionOriginal);
        return suplentes.isEmpty() ? null : suplentes.get(0);
    }

    private boolean esLicenciaValida(Licencia licencia) {
        return licencia != null && licencia.getDesignaciones() != null && !licencia.getDesignaciones().isEmpty();
    }

    private Designacion obtenerPrimeraDesignacion(Licencia licencia) {
        return licencia.getDesignaciones().stream().findFirst().orElse(null);
    }

    private List<Designacion> buscarSuplentes(Licencia licencia, Designacion designacionOriginal) {
        return designacionRepository.findDesignacionesSuplentes(
                designacionOriginal.getCargo().getId(),
                licencia.getPedidoDesde(),
                licencia.getPedidoHasta(),
                designacionOriginal.getId());
    }
}
