package unpsjb.labprog.backend.business.utils;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.utils.vlicencias.PluginProvider;
import unpsjb.labprog.backend.business.utils.vlicencias.Validable;
import unpsjb.labprog.backend.model.Licencia;

@Component
public class ValidarLicencia {

    private final PluginProvider pluginProvider;

    @Autowired
    public ValidarLicencia(PluginProvider pluginProvider) {
        this.pluginProvider = pluginProvider;
    }

    public void validar(Licencia licencia) {
        String articulo = licencia.getArticulo() != null ? licencia.getArticulo().getArticulo() : null;
        List<Validable> validaciones = pluginProvider.getPlugins(articulo);
        for (Validable validacion : validaciones) {
            validacion.validar(licencia);
        }
    }
}
