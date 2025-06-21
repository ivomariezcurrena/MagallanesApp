package unpsjb.labprog.backend.business.validaciones.vlicencias;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginManager {
    public static List<Validable> cargarPlugins() {
        List<Validable> plugins = new ArrayList<>();
        ServiceLoader<Validable> loader = ServiceLoader.load(Validable.class);
        for (Validable plugin : loader) {
            plugins.add(plugin);
        }
        return plugins;
    }
}