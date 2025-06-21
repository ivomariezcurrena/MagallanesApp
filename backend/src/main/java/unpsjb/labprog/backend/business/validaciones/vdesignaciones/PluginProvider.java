package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Designacion;

@Component("pluginProviderVdesignaciones")
public class PluginProvider {
    private final Map<TipoDesignacionValido.Tipo, List<DesignacionValidable>> porTipo = new HashMap<>();

    public PluginProvider() {
        for (DesignacionValidable v : PluginManager.cargarPlugins()) {
            TipoDesignacionValido anotacion = v.getClass().getAnnotation(TipoDesignacionValido.class);
            if (anotacion != null) {
                porTipo.computeIfAbsent(anotacion.tipo(), k -> new ArrayList<>()).add(v);
            }
        }
    }

    public List<DesignacionValidable> getPlugins(Designacion d) {
        TipoDesignacionValido.Tipo tipo = (d.getCargo() != null && d.getCargo().getDivision() != null)
                ? TipoDesignacionValido.Tipo.CON_DIVISION
                : TipoDesignacionValido.Tipo.SIN_DIVISION;
        return porTipo.getOrDefault(tipo, Collections.emptyList());
    }
}