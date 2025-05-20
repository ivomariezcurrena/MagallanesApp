package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;
import java.util.List;

import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Licencia;

public class Validacion2 implements Validable {

    private final LicenciaRepository licenciaRepository;
    private final MensajeFormateador mensajeFormateador;
    private final int topeMes;
    private final int topeAnio;

    public Validacion2(LicenciaRepository licenciaRepository, MensajeFormateador mensajeFormateador,
            int topeMes, int topeAnio) {
        this.licenciaRepository = licenciaRepository;
        this.mensajeFormateador = mensajeFormateador;
        this.topeMes = topeMes;
        this.topeAnio = topeAnio;
    }

    @Override
    public void validar(Licencia licencia) {
        int anio = licencia.getPedidoDesde().getYear();
        int mes = licencia.getPedidoDesde().getMonthValue();

        List<Licencia> licenciasAnio = licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int diasUsadosAnio = 0;
        int diasUsadosMes = 0;
        for (Licencia l : licenciasAnio) {
            // Si es update, no sumar la misma licencia
            if (licencia.getId() != 0 && licencia.getId() == l.getId())
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
                        : licencia.getPedidoDesde().toLocalDate().withMonth(mes).lengthOfMonth();
                diasUsadosMes += (hastaDia - desdeDia + 1);
            }
        }

        int diasActual = (int) ChronoUnit.DAYS.between(
                licencia.getPedidoDesde().toLocalDate(),
                licencia.getPedidoHasta().toLocalDate()) + 1;

        // Control anual
        if (diasUsadosAnio + diasActual > topeAnio) {
            throw new IllegalArgumentException(
                    mensajeFormateador.getErrorLicenciaTopeDias36AAnual(licencia, topeAnio));
        }

        // Control mensual
        if (diasUsadosMes + diasActual > topeMes) {
            throw new IllegalArgumentException(
                    mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeMes));
        }
    }

}
