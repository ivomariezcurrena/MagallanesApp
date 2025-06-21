package unpsjb.labprog.backend.business.validaciones.vlicencias;

import java.util.List;

import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

@ArticuloValido("5B")
public class Validacion5B implements Validable {
    private final int topeDias = 60;

    @Override
    public void validar(Licencia licencia) {
        int anio = licencia.getPedidoDesde().getYear();
        List<Licencia> licenciasAnio = PluginDependencies.licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int diasUsados = LicenciaDiasHelper.contarDiasEnAnio(licenciasAnio, licencia);

        int diasActual = LicenciaDiasHelper.contarDias(licencia);

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
