package unpsjb.labprog.backend.business.horarioUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.CargoService;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Horario;
import unpsjb.labprog.backend.utils.Horarios;

@Component
public class HorarioFactory {
    @Autowired
    DivisionService service;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private DesignacionService designacionService;

    public List<Horarios> crearHorarios(int id) {
        Division division = service.findById(id);

        // Buscamos cargos y designaciones
        List<Cargo> cargos = cargoService.findByDivision(division.getAnio(), division.getNumDivision());
        List<Designacion> todasDesignaciones = designacionService.findAll();

        List<Horarios> horarios = new ArrayList<>();

        for (Cargo cargo : cargos) {
            Designacion designacion = todasDesignaciones.stream()
                    .filter(d -> d.getCargo() != null && d.getCargo().getId() == cargo.getId())
                    .findFirst()
                    .orElse(null);

            String docente = (designacion != null && designacion.getPersona() != null)
                    ? designacion.getPersona().getNombre() + " " + designacion.getPersona().getApellido()
                    : "Sin docente";

            int designacionId = (designacion != null) ? designacion.getId() : -1;

            for (Horario h : cargo.getHorarios()) {
                Horarios horario = new Horarios();
                horario.setId(cargo.getId());
                horario.setDia(h.getDia());
                horario.setHora(h.getHora());
                horario.setDocente(docente);
                horario.setNombre(cargo.getNombre());
                horario.setDesignacionId(designacionId);
                horarios.add(horario);
            }
        }
        return horarios;
    }
}
