package unpsjb.labprog.backend.business.utils.vdesignaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;

@Component("pluginDependenciesConfigurerVdesignaciones")
public class PluginDependenciesConfigurer {

    @Autowired
    private DesignacionRepository designacionRepository;

    @Autowired
    private MensajeFormateador mensajeFormateador;

    @Autowired
    private LicenciaRepository licenciaRepository;

    @PostConstruct
    public void init() {
        PluginDependencies.designacionRepository = designacionRepository;
        PluginDependencies.mensajeFormateador = mensajeFormateador;
        PluginDependencies.licenciaRepository = licenciaRepository;
    }
}
