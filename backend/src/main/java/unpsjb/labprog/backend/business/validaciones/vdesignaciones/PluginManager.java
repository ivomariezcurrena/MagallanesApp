package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginManager {

    public static List<DesignacionValidable> cargarPlugins() {
        List<DesignacionValidable> plugins = new ArrayList<>();
        ServiceLoader<DesignacionValidable> loader = ServiceLoader.load(DesignacionValidable.class);
        for (DesignacionValidable plugin : loader) {
            plugins.add(plugin);
        }
        return plugins;
    }
}
