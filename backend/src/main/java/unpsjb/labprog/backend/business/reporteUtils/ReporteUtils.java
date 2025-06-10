package unpsjb.labprog.backend.business.reporteUtils;

import java.time.LocalDate;
import java.util.List;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.model.Designacion;

public class ReporteUtils {

    public static int calcularTotalDiasLicencia(List<Licencia> licencias, int anio) {
        int totalDiasLicencia = 0;
        for (Licencia licencia : licencias) {
            LocalDate desde = licencia.getPedidoDesde().toLocalDate();
            LocalDate hasta = licencia.getPedidoHasta().toLocalDate();
            LocalDate desdeA = desde.isBefore(LocalDate.of(anio, 1, 1)) ? LocalDate.of(anio, 1, 1) : desde;
            LocalDate hastaA = hasta.isAfter(LocalDate.of(anio, 12, 31)) ? LocalDate.of(anio, 12, 31) : hasta;
            int dias = (int) (hastaA.toEpochDay() - desdeA.toEpochDay()) + 1;
            totalDiasLicencia += Math.max(dias, 0);
        }
        return totalDiasLicencia;
    }

    public static int calcularDiasTrabajados(List<Designacion> designaciones, int anio) {
        int diasTrabajados = 0;
        for (Designacion aDesignacion : designaciones) {
            LocalDate desde = aDesignacion.getFechaInicio().toLocalDate();
            LocalDate hasta = aDesignacion.getFechaFin() != null ? aDesignacion.getFechaFin().toLocalDate()
                    : LocalDate.of(anio, 12, 31);
            LocalDate desdeA = desde.isBefore(LocalDate.of(anio, 1, 1)) ? LocalDate.of(anio, 1, 1) : desde;
            LocalDate hastaA = hasta.isAfter(LocalDate.of(anio, 12, 31)) ? LocalDate.of(anio, 12, 31) : hasta;
            int dias = (int) (hastaA.toEpochDay() - desdeA.toEpochDay()) + 1;
            diasTrabajados += Math.max(dias, 0);
        }
        return diasTrabajados;
    }

    public static int calcularPorcentajePresencia(int diasTrabajados, int totalDiasLicencia) {
        int diasDePresencia = diasTrabajados - totalDiasLicencia;
        return diasTrabajados > 0 ? Math.round((diasDePresencia * 100f) / diasTrabajados) : 0;
    }
}