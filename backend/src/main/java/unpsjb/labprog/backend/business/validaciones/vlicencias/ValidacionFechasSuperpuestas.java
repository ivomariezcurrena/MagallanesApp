package unpsjb.labprog.backend.business.validaciones.vlicencias;

import java.util.List;

import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

public class ValidacionFechasSuperpuestas implements Validable {

    @Override
    public void validar(Licencia licencia) {
        List<Licencia> superpuestas = PluginDependencies.licenciaRepository.verificarFechas(
                licencia.getPersona().getDni(),
                licencia.getArticulo(),
                licencia.getPedidoDesde(),
                licencia.getPedidoHasta(),
                licencia.getId() == 0 ? -1 : licencia.getId());
        if (!superpuestas.isEmpty()) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaSuperposicion(licencia);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
    }

}
