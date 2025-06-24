package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.validaciones.vdesignaciones.TipoDesignacionValido.Tipo;
import unpsjb.labprog.backend.business.validaciones.vlicencias.ArticuloValido;
import unpsjb.labprog.backend.business.validaciones.vlicencias.Validable;
import unpsjb.labprog.backend.model.Designacion;

@Component("pluginProviderVdesignaciones")
public class PluginProvider {
    private final Map<TipoDesignacionValido.Tipo, List<DesignacionValidable>> porTipo = new HashMap<>();
    private final List<DesignacionValidable> globales = new ArrayList<>();

    public PluginProvider() {
        for (DesignacionValidable v : PluginManager.cargarPlugins()) {
            TipoDesignacionValido anotacion = v.getClass().getAnnotation(TipoDesignacionValido.class);
            if (anotacion == null) {
                globales.add(v);
            } else {
                porTipo.computeIfAbsent(anotacion.tipo(), k -> new ArrayList<>()).add(v);

            }
        }
    }

    public List<DesignacionValidable> getPlugins(Designacion d) {
        Tipo tipo = (d.getCargo() != null && d.getCargo().getDivision() != null)
                ? TipoDesignacionValido.Tipo.CON_DIVISION
                : TipoDesignacionValido.Tipo.SIN_DIVISION;

        List<DesignacionValidable> resultado = new ArrayList<>();
        resultado.addAll(globales); // <-- Esto es clave
        resultado.addAll(porTipo.getOrDefault(tipo, Collections.emptyList()));
        return resultado;
    }

}