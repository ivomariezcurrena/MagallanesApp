package unpsjb.labprog.backend.business.validaciones.vdesignaciones;

import unpsjb.labprog.backend.model.Designacion;

@TipoDesignacionValido(tipo = TipoDesignacionValido.Tipo.CON_DIVISION)
public class ValidarDesignacionConDivision implements DesignacionValidable {

    @Override
    public void validar(Designacion designacion) {
        var div = designacion.getCargo().getDivision();
        var existentes = PluginDependencies.designacionRepository.findSolapamientoEnDivision(
                designacion.getCargo().getNombre(),
                div.getAnio(),
                div.getNumDivision(),
                div.getTurno(),
                designacion.getFechaInicio(),
                designacion.getFechaFin(),
                designacion.getId());

        for (var e : existentes) {
            var licencias = PluginDependencies.licenciaRepository.findLicenciasEnPeriodo(
                    e.getId(), designacion.getFechaInicio(), designacion.getFechaFin());
            if (!VerificarLicenciasEnPeriodo.verificar(licencias, designacion.getFechaInicio(),
                    designacion.getFechaFin())) {
                throw new IllegalArgumentException(
                        PluginDependencies.mensajeFormateador.getErrorDesignacionYaExisteEspacioCurricular(
                                designacion.getPersona().getNombre(),
                                designacion.getPersona().getApellido(),
                                designacion.getCargo().getNombre(),
                                div.getAnio(),
                                div.getNumDivision(),
                                div.getTurno(),
                                e.getPersona().getNombre(),
                                e.getPersona().getApellido()));
            }
        }
    }

}
