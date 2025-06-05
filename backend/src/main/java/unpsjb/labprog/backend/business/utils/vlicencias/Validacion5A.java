package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;
import java.util.List;

import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

public class Validacion5A implements Validable {

    private final int topeDias = 30;

    @Override
    public void validar(Licencia licencia) {
        if (!"5A".equals(licencia.getArticulo().getArticulo())) {
            return; // Solo aplica para el artículo 5A
        }
        int anio = licencia.getPedidoDesde().getYear();
        List<Licencia> licenciasAnio = PluginDependencies.licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int diasUsados = 0;
        for (Licencia l : licenciasAnio) {
            if (licencia.getId() != 0 && licencia.getId() == l.getId())
                continue;
            diasUsados += (int) ChronoUnit.DAYS.between(l.getPedidoDesde().toLocalDate(),
                    l.getPedidoHasta().toLocalDate()) + 1;
        }

        int diasActual = (int) ChronoUnit.DAYS.between(
                licencia.getPedidoDesde().toLocalDate(),
                licencia.getPedidoHasta().toLocalDate()) + 1;

        int total = diasUsados + diasActual;

        if (!licencia.isCertificadoMedico()) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaSinCertificado(licencia);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        if (total > topeDias) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeDias);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        if (licencia.getEstado() != Estado.Invalido) {
            String mensaje = PluginDependencies.mensajeFormateador.getMensajeLicenciaOtorgada(licencia);
            licencia.setEstado(Estado.Valido);
            AgregarLog.agregarLog(licencia, mensaje, 200);
        }
    }
}
