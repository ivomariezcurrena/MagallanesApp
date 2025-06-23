package unpsjb.labprog.backend.business.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.validaciones.vdesignaciones.DesignacionValidable;
import unpsjb.labprog.backend.business.validaciones.vdesignaciones.PluginProvider;
import unpsjb.labprog.backend.model.Designacion;

@Component
public class ValidarDesignacion implements ValidadorGenerico<Designacion> {
    private final PluginProvider pluginProvider;

    @Autowired
    public ValidarDesignacion(PluginProvider pluginProvider) {
        this.pluginProvider = pluginProvider;
    }

    public void validar(Designacion d) {
        for (DesignacionValidable validacion : pluginProvider.getPlugins(d)) {
            validacion.validar(d);
        }
    }

}
