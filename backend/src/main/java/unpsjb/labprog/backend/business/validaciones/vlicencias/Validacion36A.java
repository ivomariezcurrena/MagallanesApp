package unpsjb.labprog.backend.business.validaciones.vlicencias;

import java.util.List;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

@ArticuloValido("36A")
public class Validacion36A implements Validable {

    private final int topeMes = 2;
    private final int topeAnio = 6;

    @Override
    public void validar(Licencia licencia) {
        int anio = licencia.getPedidoDesde().getYear();
        int mes = licencia.getPedidoDesde().getMonthValue();

        List<Licencia> licenciasAnio = PluginDependencies.licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int[] dias = LicenciaDiasHelper.contarDiasEnAnioYMes(licenciasAnio, licencia, anio, mes);
        int diasUsadosAnio = dias[0];
        int diasUsadosMes = dias[1];

        int diasActual = LicenciaDiasHelper.contarDias(licencia);

        // Control anual
        if (diasUsadosAnio + diasActual > topeAnio) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaTopeDias36AAnual(licencia, topeAnio);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }

        // Control mensual
        if (diasUsadosMes + diasActual > topeMes) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeMes);
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
