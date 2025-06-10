package unpsjb.labprog.backend.business.reporteUtils;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.business.DesignacionRepository;
import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.PersonaRepository;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.utils.ReportePersona;

@Component
public class ReportePersonaFactory {

    private final LicenciaRepository licenciaRepository;
    private final DesignacionRepository designacionRepository;
    private final PersonaRepository personaRepository;

    public ReportePersonaFactory(LicenciaRepository licenciaRepository,
            DesignacionRepository designacionRepository,
            PersonaRepository personaRepository) {
        this.licenciaRepository = licenciaRepository;
        this.designacionRepository = designacionRepository;
        this.personaRepository = personaRepository;
    }

    public ReportePersona crearReportePersona(Integer dni, int anio) {
        String nombre = obtenerNombre(dni);
        String apellido = obtenerApellido(dni);

        List<Licencia> licencias = obtenerLicencias(dni, anio);
        int cantidadLicencias = licencias.size();
        int totalDias = ReporteUtils.calcularTotalDiasLicencia(licencias, anio);

        List<Designacion> designaciones = obtenerDesignaciones(dni, anio);
        int diasTrabajados = ReporteUtils.calcularDiasTrabajados(designaciones, anio);
        int porcentajePresencia = ReporteUtils.calcularPorcentajePresencia(diasTrabajados, totalDias);

        return new ReportePersona.Builder()
                .id(dni)
                .nombre(nombre)
                .apellido(apellido)
                .cantidadLicencias(cantidadLicencias)
                .totalDias(totalDias)
                .porcentajePresencia(porcentajePresencia)
                .build();
    }

    private String obtenerNombre(Integer dni) {
        Persona persona = personaRepository.findById(dni).orElse(null);
        return persona != null ? persona.getNombre() : "";
    }

    private String obtenerApellido(Integer dni) {
        Persona persona = personaRepository.findById(dni).orElse(null);
        return persona != null ? persona.getApellido() : "";
    }

    private List<Licencia> obtenerLicencias(Integer dni, int anio) {
        return licenciaRepository.findAllByPersonaAndAnio(dni, anio);
    }

    private List<Designacion> obtenerDesignaciones(Integer dni, int anio) {
        return designacionRepository.findDesignacionesActivasEnPeriodo(
                dni,
                LocalDateTime.of(anio, 1, 1, 0, 0),
                LocalDateTime.of(anio, 12, 31, 23, 59));
    }

}
