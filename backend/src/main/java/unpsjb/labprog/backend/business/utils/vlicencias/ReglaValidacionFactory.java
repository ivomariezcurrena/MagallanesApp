package unpsjb.labprog.backend.business.utils.vlicencias;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;

@Component
public class ReglaValidacionFactory {

    private final Map<String, Validable> reglas = new HashMap<>();

    @Autowired
    public ReglaValidacionFactory(LicenciaRepository licenciaRepository, MensajeFormateador mensajeFormateador) {
        reglas.put("5A", new Validacion1(licenciaRepository, mensajeFormateador, 30));
        reglas.put("23A", new Validacion1(licenciaRepository, mensajeFormateador, 30));
        reglas.put("36A", new Validacion2(licenciaRepository, mensajeFormateador, 2, 6));
        // Agrega más reglas según sea necesario
    }

    public Validable getRegla(String articulo) {
        return reglas.get(articulo);
    }
}