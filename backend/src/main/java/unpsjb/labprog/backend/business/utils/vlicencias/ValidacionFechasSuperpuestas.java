package unpsjb.labprog.backend.business.utils.vlicencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;

public class ValidacionFechasSuperpuestas implements Validable {
    @Autowired
    @Lazy
    LicenciaRepository licenciaRepository;

    @Autowired
    MensajeFormateador mensajeFormateador;

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
