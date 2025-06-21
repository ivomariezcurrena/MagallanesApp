package unpsjb.labprog.backend.business.validaciones.vlicencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.ArticuloLicenciaRepository;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.PersonaRepository;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

@Component
public class LicenciaHelper {
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    ArticuloLicenciaRepository articuloLicenciaRepository;

    @Autowired
    DesignacionRepository designacionRepository;

    public void cargarEntidades(Licencia licencia) {
        if (licencia.getPersona() != null && licencia.getPersona().getDni() != 0) {
            licencia.setPersona(personaRepository.findById(licencia.getPersona().getDni()).orElse(null));
            licencia.setDomicilio(licencia.getPersona().getDomicilio());
        }
        if (licencia.getArticulo() != null && licencia.getArticulo().getId() != 0) {
            licencia.setArticulo(articuloLicenciaRepository.findById(licencia.getArticulo().getId()).orElse(null));
        }
    }

    public void cargarDesignaciones(Licencia licencia) {
        if (licencia.getPersona() != null && licencia.getPedidoDesde() != null && licencia.getPedidoHasta() != null) {
            List<Designacion> designaciones = designacionRepository.findDesignacionesActivasEnPeriodo(
                    licencia.getPersona().getDni(),
                    licencia.getPedidoDesde(),
                    licencia.getPedidoHasta());
            licencia.setDesignaciones(designaciones);
        }
    }
}
