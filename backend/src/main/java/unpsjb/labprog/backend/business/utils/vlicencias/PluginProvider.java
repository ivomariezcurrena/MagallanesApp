package unpsjb.labprog.backend.business.utils.vlicencias;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Component
public class PluginProvider {
    private final Map<String, List<Validable>> porArticulo = new HashMap<>();
    private final List<Validable> globales = new ArrayList<>();

    public PluginProvider() {
        for (Validable v : PluginManager.cargarPlugins()) {
            ArticuloValido anotacion = v.getClass().getAnnotation(ArticuloValido.class);
            if (anotacion == null) {
                globales.add(v);
            } else {
                for (String art : anotacion.value()) {
                    porArticulo.computeIfAbsent(art, k -> new ArrayList<>()).add(v);
                }
            }
        }
    }

    public List<Validable> getPlugins(String articulo) {
        List<Validable> resultado = new ArrayList<>(globales);
        if (articulo != null && porArticulo.containsKey(articulo)) {
            resultado.addAll(porArticulo.get(articulo));
        }
        return resultado;
    }
}
