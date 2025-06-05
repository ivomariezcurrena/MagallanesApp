package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.ArticuloLicenciaRepository;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.PersonaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

@Component
public class ValidarLicencia {
    @Autowired
    @Lazy
    LicenciaRepository licenciaRepository;

    @Autowired
    @Lazy
    PersonaRepository personaRepository;

    @Autowired
    @Lazy
    ArticuloLicenciaRepository articuloLicenciaRepository;

    @Autowired
    @Lazy
    DesignacionRepository designacionRepository;

    private final List<Validable> workflowValidaciones;

    @Autowired
    public void setPluginDependencies(
            LicenciaRepository licenciaRepository,
            MensajeFormateador mensajeFormateador) {
        PluginDependencies.licenciaRepository = licenciaRepository;
        PluginDependencies.mensajeFormateador = mensajeFormateador;
    }

    public ValidarLicencia() {
        this.workflowValidaciones = PluginManager.cargarPlugins();
    }

    public void validar(Licencia licencia) {
        cargarEntidades(licencia);
        cargarDesignaciones(licencia);
        for (Validable validacion : workflowValidaciones) {
            validacion.validar(licencia);
        }
    }

    private void cargarEntidades(Licencia licencia) {
        if (licencia.getPersona() != null && licencia.getPersona().getDni() != 0) {
            licencia.setPersona(personaRepository.findById(licencia.getPersona().getDni()).orElse(null));
            licencia.setDomicilio(licencia.getPersona().getDomicilio());
        }
        if (licencia.getArticulo() != null && licencia.getArticulo().getId() != 0) {
            licencia.setArticulo(articuloLicenciaRepository.findById(licencia.getArticulo().getId()).orElse(null));
        }
    }

    private void cargarDesignaciones(Licencia licencia) {
        if (licencia.getPersona() != null && licencia.getPedidoDesde() != null && licencia.getPedidoHasta() != null) {
            List<Designacion> designaciones = designacionRepository.findDesignacionesActivasEnPeriodo(
                    licencia.getPersona().getDni(),
                    licencia.getPedidoDesde(),
                    licencia.getPedidoHasta());
            licencia.setDesignaciones(designaciones);
        }
    }

}
