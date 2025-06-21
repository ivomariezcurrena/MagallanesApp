package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import java.time.LocalDateTime;
import java.util.List;

import unpsjb.labprog.backend.model.Licencia;

public class VerificarLicenciasEnPeriodo {
    public static boolean verificar(List<Licencia> licencias, LocalDateTime desde, LocalDateTime hasta) {
        if (licencias == null || licencias.isEmpty())
            return false;
        LocalDateTime actual = desde;
        while (!actual.isAfter(hasta)) {
            final LocalDateTime dia = actual; // variable efectivamente final para la lambda
            boolean cubierto = licencias.stream()
                    .anyMatch(l -> (l.getPedidoDesde().isEqual(dia) || l.getPedidoDesde().isBefore(dia)) &&
                            (l.getPedidoHasta().isEqual(dia) || l.getPedidoHasta().isAfter(dia)));
            if (!cubierto)
                return false;
            actual = actual.plusDays(1);
        }
        return true;
    }
}
