package unpsjb.labprog.backend.business.validaciones.vlicencias;

import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

public class ValidacionDesignaciones implements Validable {

    @Override
    public void validar(Licencia licencia) {
        if (licencia.getPersona() == null || licencia.getPersona().getDesignaciones() == null
                || licencia.getPersona().getDesignaciones().isEmpty()) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaSinCargo(licencia);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        boolean tieneDesignacionEseDia = licencia.getPersona().getDesignaciones().stream().anyMatch(designacion -> {
            return (designacion.getFechaInicio() == null
                    || !licencia.getPedidoHasta().isBefore(designacion.getFechaInicio()))
                    && (designacion.getFechaFin() == null
                            || !licencia.getPedidoDesde().isAfter(designacion.getFechaFin()));
        });
        if (!tieneDesignacionEseDia) {
            String mensaje = PluginDependencies.mensajeFormateador.getErrorLicenciaSinDesignacionEseDia(licencia);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
    }

}
