package unpsjb.labprog.backend.business.utils.vlicencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class LicenciaMensajeService {

    @Autowired
    MensajeFormateador mensaje;

    public String getMensajeAgregar(Licencia l) {
        return mensaje.getMensajeLicenciaOtorgada(l);
    }

    public String getMensajeActualizar(Licencia l) {
        return mensaje.getMensajeActualizarLicencia(l);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeLicencia(id);
    }

    public String getMensajeNoEncontrada(int id) {
        return mensaje.getMensajeLicenciaNoEncontrada(id);
    }
}