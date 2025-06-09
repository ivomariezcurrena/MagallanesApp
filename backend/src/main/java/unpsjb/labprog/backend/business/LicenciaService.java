package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.business.utils.Validador;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.utils.ReportePersona;

@Service
public class LicenciaService {
    @Autowired
    LicenciaRepository repository;

    @Autowired
    Validador validador;

    @Autowired
    MensajeFormateador mensaje;

    @Autowired
    @Lazy
    DesignacionRepository designacionRepository;

    @Autowired
    @Lazy
    PersonaRepository personaRepository;

    public List<Licencia> findAll() {
        List<Licencia> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Licencia findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Licencia> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Page<Licencia> findAllValidas(int page, int size) {
        return repository.findAllValidas(PageRequest.of(page, size));
    }

    @Transactional
    public Licencia save(Licencia e) {
        validador.validar(e);
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public String getMensajeAgregar(Licencia l) {
        return mensaje.getMensajeLicenciaOtorgada(l);
    }

    public String getMensajeActualizar(Licencia l) {
        return mensaje.getMensajeActualizarLicencia(l);
    }

    public String getMensajeEliminacion(int id) {
        return mensaje.getMensajeEliminacionDeLicencia(id);
    }

    public String getMensajeNoEncontrada(int id) {
        return mensaje.getMensajeLicenciaNoEncontrada(id);
    }

    public Page<Licencia> findAllDesdeFecha(LocalDateTime fechaDesde, int page, int size) {
        return repository.findAllDesdeFecha(fechaDesde, PageRequest.of(page, size));
    }

    public List<Licencia> findLicenciasVigentesEnFecha(LocalDateTime fecha) {
        return repository.findLicenciasVigentesEnFecha(fecha);
    }

    /**
     * Busca la designación suplente asociada a una licencia.
     *
     * @param licenciaId ID de la licencia para la cual se busca el suplente.
     * @return La primera {@link Designacion} suplente encontrada, o {@code null} si
     *         no existe.
     */
    public Designacion findDesignacionSuplente(int licenciaId) {
        Licencia licencia = findById(licenciaId);
        if (!esLicenciaValida(licencia)) {
            return null;
        }

        Designacion designacionOriginal = obtenerPrimeraDesignacion(licencia);
        if (designacionOriginal == null) {
            return null;
        }

        List<Designacion> suplentes = buscarSuplentes(licencia, designacionOriginal);
        return suplentes.isEmpty() ? null : suplentes.get(0);
    }

    private boolean esLicenciaValida(Licencia licencia) {
        return licencia != null && licencia.getDesignaciones() != null && !licencia.getDesignaciones().isEmpty();
    }

    private Designacion obtenerPrimeraDesignacion(Licencia licencia) {
        return licencia.getDesignaciones().stream().findFirst().orElse(null);
    }

    private List<Designacion> buscarSuplentes(Licencia licencia, Designacion designacionOriginal) {
        return designacionRepository.findDesignacionesSuplentes(
                designacionOriginal.getCargo().getId(),
                licencia.getPedidoDesde(),
                licencia.getPedidoHasta(),
                designacionOriginal.getId());
    }

    public Page<Licencia> findAllAnio(LocalDateTime fechaDesde, int page, int size) {
        LocalDateTime inicioAnio = fechaDesde.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime inicioSiguienteAnio = inicioAnio.plusYears(1);
        return repository.findAllAnio(inicioAnio, inicioSiguienteAnio, PageRequest.of(page, size));
    }

    public List<Licencia> getValidas(int anio) {
        return repository.getValidas(anio);
    }

    public List<Licencia> getInvalidas(int anio) {
        return repository.getInvalidas(anio);
    }

    public Page<ReportePersona> reporteDeConcepto(LocalDateTime fechaDesde, int page, int size) {
        int anio = fechaDesde.getYear();
        // busco las personas que tuvieron una designacion ese año
        List<Integer> personas = designacionRepository.findPersonasConDesignacionEnAnio(anio);

        List<ReportePersona> reporte = new ArrayList<>();

        for (Integer dni : personas) {
            // busca las licencias de la persona en el año
            List<Licencia> licencias = repository.findAllByPersonaAndAnio(dni, anio);
            ReportePersona aReporte = new ReportePersona();
            aReporte.id = dni;

            // buscamos la persona ya que puede o no tener lciencia
            Persona persona = personaRepository.findById(dni).orElse(null);
            aReporte.nombre = persona.getNombre();
            aReporte.apellido = persona.getApellido();

            // cantidad de licencias que se tomo
            aReporte.cantidadLicencias = licencias.size();

            // calcular total de dias de licencia
            int totalDiasLicencia = 0;
            for (Licencia licencia : licencias) {
                LocalDate desde = licencia.getPedidoDesde().toLocalDate();
                LocalDate hasta = licencia.getPedidoHasta().toLocalDate();
                // Limitar los días al año consultado
                LocalDate desdeA = desde.isBefore(LocalDate.of(anio, 1, 1)) ? LocalDate.of(anio, 1, 1) : desde;
                LocalDate hastaA = hasta.isAfter(LocalDate.of(anio, 12, 31)) ? LocalDate.of(anio, 12, 31) : hasta;
                int dias = (int) (hastaA.toEpochDay() - desdeA.toEpochDay()) + 1;
                totalDiasLicencia += Math.max(dias, 0);
            }
            aReporte.totalDias = totalDiasLicencia;

            // buscar la designacion de la persona
            List<Designacion> designaciones = designacionRepository.findDesignacionesActivasEnPeriodo(
                    dni,
                    LocalDateTime.of(anio, 1, 1, 0, 0),
                    LocalDateTime.of(anio, 12, 31, 23, 59));

            int diasTrabajados = 0;
            for (Designacion aDesignacion : designaciones) {
                LocalDate desde = aDesignacion.getFechaInicio().toLocalDate();
                LocalDate hasta = aDesignacion.getFechaFin() != null ? aDesignacion.getFechaFin().toLocalDate()
                        : LocalDate.of(anio, 12, 31);
                LocalDate desdeA = desde.isBefore(LocalDate.of(anio, 1, 1)) ? LocalDate.of(anio, 1, 1) : desde;
                LocalDate hastaA = hasta.isAfter(LocalDate.of(anio, 12, 31)) ? LocalDate.of(anio, 12, 31) : hasta;
                // calcula la cantidad de dias de esa designacion dentro del año:
                int dias = (int) (hastaA.toEpochDay() - desdeA.toEpochDay()) + 1;
                // suma al total de dias trabajados
                diasTrabajados += Math.max(dias, 0);
            }

            int diasDePresencia = diasTrabajados - aReporte.totalDias;
            aReporte.porcentajePresencia = diasTrabajados > 0 ? Math.round((diasDePresencia * 100f) / diasTrabajados)
                    : 0;
            reporte.add(aReporte);
        }
        int start = page * size;
        int end = Math.min(start + size, reporte.size());
        List<ReportePersona> pageContent = start < end ? reporte.subList(start, end) : new ArrayList<>();
        return new PageImpl<>(pageContent, PageRequest.of(page, size), reporte.size());
    }

}
