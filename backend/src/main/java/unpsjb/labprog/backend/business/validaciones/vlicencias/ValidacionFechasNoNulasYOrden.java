package unpsjb.labprog.backend.business.validaciones.vlicencias;

import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

public class ValidacionFechasNoNulasYOrden implements Validable {

    @Override
    public void validar(Licencia licencia) {
        if (licencia.getPedidoDesde() == null) {
            String mensaje = PluginDependencies.mensajeFormateador.getFechaDesdeNull();
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        if (licencia.getPedidoHasta() == null) {
            String mensaje = PluginDependencies.mensajeFormateador.getFechaDesdeNull();
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        if (licencia.getPedidoDesde().isAfter(licencia.getPedidoHasta())) {
            String mensaje = PluginDependencies.mensajeFormateador.getFechasInversas();
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
    }

}
