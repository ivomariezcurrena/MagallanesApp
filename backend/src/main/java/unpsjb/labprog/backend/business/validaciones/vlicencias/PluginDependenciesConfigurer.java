package unpsjb.labprog.backend.business.validaciones.vlicencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.validaciones.MensajeFormateador;

@Component("pluginDependenciesConfigurerVlicencias")
public class PluginDependenciesConfigurer {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    private MensajeFormateador mensajeFormateador;

    @PostConstruct
    public void init() {
        PluginDependencies.licenciaRepository = licenciaRepository;
        PluginDependencies.mensajeFormateador = mensajeFormateador;
    }
}
