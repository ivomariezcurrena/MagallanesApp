package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;
import java.util.List;

import unpsjb.labprog.backend.model.Licencia;

public class LicenciaDiasHelper {

    /**
     * Cuenta los dias que estuvo de licencia en un año
     * 
     * @return cantidad de días de licencia
     */
    public static int contarDiasEnAnio(List<Licencia> licencias, Licencia actual) {
        int dias = 0;
        for (Licencia l : licencias) {
            if (actual.getId() != 0 && actual.getId() == l.getId())
                continue;
            dias += (int) ChronoUnit.DAYS.between(l.getPedidoDesde().toLocalDate(),
                    l.getPedidoHasta().toLocalDate()) + 1;
        }
        return dias;
    }

    /**
     * Calcula los días de una licencia, sumando 1 al total para incluir el día
     * final.
     * 
     * @return cantidad de días de la licencia
     */
    public static int contarDias(Licencia licencia) {
        return (int) ChronoUnit.DAYS.between(licencia.getPedidoDesde().toLocalDate(),
                licencia.getPedidoHasta().toLocalDate()) + 1;
    }

    /**
     * Calcula los días usados en el año y en el mes para una persona y artículo,
     * excluyendo la licencia actual si es update.
     * 
     * @return int[] donde [0]=días año, [1]=días mes
     */
    public static int[] contarDiasEnAnioYMes(List<Licencia> licencias, Licencia actual, int anio, int mes) {
        int diasUsadosAnio = 0;
        int diasUsadosMes = 0;
        for (Licencia l : licencias) {
            // Si es update, no sumar la misma licencia
            if (actual.getId() != 0 && actual.getId() == l.getId())
                continue;
            int desdeMes = l.getPedidoDesde().getMonthValue();
            int hastaMes = l.getPedidoHasta().getMonthValue();
            int desdeAnio = l.getPedidoDesde().getYear();
            int hastaAnio = l.getPedidoHasta().getYear();

            int dias = (int) ChronoUnit.DAYS.between(l.getPedidoDesde().toLocalDate(),
                    l.getPedidoHasta().toLocalDate()) + 1;
            diasUsadosAnio += dias;

            // Sumar solo los días del mismo mes y año
            if (desdeAnio == anio && hastaAnio == anio && desdeMes == mes && hastaMes == mes) {
                diasUsadosMes += dias;
            } else if (desdeAnio == anio && hastaAnio == anio && (desdeMes <= mes && hastaMes >= mes)) {
                // Si la licencia abarca el mes actual, sumar solo los días que caen en el mes
                int desdeDia = (l.getPedidoDesde().getMonthValue() == mes) ? l.getPedidoDesde().getDayOfMonth() : 1;
                int hastaDia = (l.getPedidoHasta().getMonthValue() == mes) ? l.getPedidoHasta().getDayOfMonth()
                        : actual.getPedidoDesde().toLocalDate().withMonth(mes).lengthOfMonth();
                diasUsadosMes += (hastaDia - desdeDia + 1);
            }
        }
        return new int[] { diasUsadosAnio, diasUsadosMes };
    }
}
