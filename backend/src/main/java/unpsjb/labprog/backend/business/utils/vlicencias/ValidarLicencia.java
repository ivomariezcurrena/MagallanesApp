package unpsjb.labprog.backend.business.utils.vlicencias;

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
import unpsjb.labprog.backend.model.Licencia;

@Component
public class ValidarLicencia {
    @Autowired
    @Lazy
    LicenciaRepository licenciaRepository;

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

        String articulo = licencia.getArticulo() != null ? licencia.getArticulo().getArticulo() : null;
        List<Validable> validaciones = workflowValidaciones.stream()
                .filter(v -> {
                    ArticuloValido anotacion = v.getClass().getAnnotation(ArticuloValido.class);
                    if (anotacion == null)
                        return true; // Sin anotación = global
                    for (String art : anotacion.value()) {
                        if (art.equals(articulo))
                            return true;
                    }
                    return false;
                })
                .toList();

        for (Validable validacion : validaciones) {
            validacion.validar(licencia);
        }
    }

}
