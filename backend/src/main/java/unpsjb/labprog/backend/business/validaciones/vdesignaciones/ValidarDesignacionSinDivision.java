package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import unpsjb.labprog.backend.model.Designacion;

@TipoDesignacionValido(tipo = TipoDesignacionValido.Tipo.SIN_DIVISION)
public class ValidarDesignacionSinDivision implements DesignacionValidable {

    @Override
    public void validar(Designacion designacion) {
        var existentes = PluginDependencies.designacionRepository.findDesignacionActivaOSolapada(
                designacion.getCargo().getNombre(), designacion.getFechaInicio(), designacion.getFechaFin(),
                designacion.getId());

        for (var e : existentes) {
            var licencias = PluginDependencies.licenciaRepository.findLicenciasEnPeriodo(
                    e.getId(), designacion.getFechaInicio(), designacion.getFechaFin());
            if (!VerificarLicenciasEnPeriodo.verificar(licencias, designacion.getFechaInicio(),
                    designacion.getFechaFin())) {
                throw new IllegalArgumentException(
                        PluginDependencies.mensajeFormateador.getErrorDesignacionYaExisteCargo(
                                designacion.getPersona().getNombre(),
                                designacion.getPersona().getApellido(),
                                designacion.getCargo().getNombre(),
                                e.getPersona().getNombre(),
                                e.getPersona().getApellido()));
            }
        }

    }

}
