package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.ArticuloLicenciaRepository;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.PersonaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

@Component
public class ValidarLicencia {
    @Autowired
    @Lazy
    LicenciaRepository licenciaRepository;

    @Autowired
    @Lazy
    PersonaRepository personaRepository;

    @Autowired
    @Lazy
    ArticuloLicenciaRepository articuloLicenciaRepository;

    @Autowired
    MensajeFormateador mensajeFormateador;

    @Autowired
    @Lazy
    DesignacionRepository designacionRepository;

    @Autowired
    ReglaValidacionFactory reglaValidacionFactory;

    public void validar(Licencia licencia) {
        cargarEntidades(licencia);
        validarFechasNoNulasYOrden(licencia);
        cargarDesignaciones(licencia);
        validarDesignaciones(licencia);
        validarFechasSuperpuestas(licencia);
        validarReglaArticulo(licencia);
    }

    private void cargarEntidades(Licencia licencia) {
        if (licencia.getPersona() != null && licencia.getPersona().getDni() != 0) {
            licencia.setPersona(personaRepository.findById(licencia.getPersona().getDni()).orElse(null));
            licencia.setDomicilio(licencia.getPersona().getDomicilio());
        }
        if (licencia.getArticulo() != null && licencia.getArticulo().getId() != 0) {
            licencia.setArticulo(articuloLicenciaRepository.findById(licencia.getArticulo().getId()).orElse(null));
        }
    }

    private void validarFechasNoNulasYOrden(Licencia licencia) {
        if (licencia.getPedidoDesde() == null) {
            throw new IllegalArgumentException("La fecha desde no puede ser nula");
        }
        if (licencia.getPedidoHasta() == null) {
            throw new IllegalArgumentException("La fecha hasta no puede ser nula");
        }
        if (licencia.getPedidoDesde().isAfter(licencia.getPedidoHasta())) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
        }
    }

    private void cargarDesignaciones(Licencia licencia) {
        if (licencia.getPersona() != null && licencia.getPedidoDesde() != null && licencia.getPedidoHasta() != null) {
            List<Designacion> designaciones = designacionRepository.findDesignacionesActivasEnPeriodo(
                    licencia.getPersona().getDni(),
                    licencia.getPedidoDesde(),
                    licencia.getPedidoHasta());
            licencia.setDesignaciones(designaciones);
        }
    }

    private void validarDesignaciones(Licencia licencia) {
        if (licencia.getPersona() == null || licencia.getPersona().getDesignaciones() == null
                || licencia.getPersona().getDesignaciones().isEmpty()) {
            throw new IllegalArgumentException(mensajeFormateador.getErrorLicenciaSinCargo(licencia));
        }
        boolean tieneDesignacionEseDia = licencia.getPersona().getDesignaciones().stream().anyMatch(designacion -> {
            return (designacion.getFechaInicio() == null
                    || !licencia.getPedidoHasta().isBefore(designacion.getFechaInicio()))
                    && (designacion.getFechaFin() == null
                            || !licencia.getPedidoDesde().isAfter(designacion.getFechaFin()));
        });
        if (!tieneDesignacionEseDia) {
            throw new IllegalArgumentException(mensajeFormateador.getErrorLicenciaSinDesignacionEseDia(licencia));
        }
    }

    private void validarFechasSuperpuestas(Licencia licencia) {
        List<Licencia> superpuestas = licenciaRepository.verificarFechas(
                licencia.getPersona().getDni(),
                licencia.getArticulo(),
                licencia.getPedidoDesde(),
                licencia.getPedidoHasta(),
                licencia.getId() == 0 ? -1 : licencia.getId());
        if (!superpuestas.isEmpty()) {
            throw new IllegalArgumentException(mensajeFormateador.getErrorLicenciaSuperposicion(licencia));
        }
    }

    private void validarReglaArticulo(Licencia licencia) {
        Validable regla = reglaValidacionFactory.getRegla(licencia.getArticulo().getArticulo());
        if (regla != null) {
            regla.validar(licencia);
        } else {
            throw new IllegalArgumentException(
                    "No se encontró una regla de validación para el artículo: " + licencia.getArticulo().getArticulo());
        }
    }
}
