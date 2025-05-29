package unpsjb.labprog.backend.business.utils.vlicencias;

import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.model.Log;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AgregarLog {
    public static void agregarLog(Licencia licencia, String mensaje, int estatus) {
        Log log = new Log();
        log.setMensaje(mensaje);
        log.setEstatus(estatus);
        log.setFecha(LocalDateTime.now());
        licencia.getLogs().add(log);
    }

    public static String obtenerMensajeUltimoLog(Licencia licencia) {
        List<Log> logs = new ArrayList<>(licencia.getLogs());
        if (!logs.isEmpty()) {
            Log ultimoLog = logs.get(logs.size() - 1);
            return ultimoLog.getMensaje();
        }
        return "No hay logs disponibles.";
    }
}
