package unpsjb.labprog.backend.business.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.TipoDesignacion;

@Component
public class ValidarCargo {

    @Autowired
    @Lazy
    private DivisionService divisionService;

    @Autowired
    private MensajeFormateador mensaje;

    public void validar(Cargo cargo) {
        if (cargo.getTipoDesignacion() == TipoDesignacion.CARGO && cargo.getDivision() != null) {
            throw new IllegalArgumentException(mensaje.getErrorCargoNoDebeTenerDivision(cargo));
        }

        if (cargo.getTipoDesignacion() == TipoDesignacion.ESPACIO_CURRICULAR) {
            if (cargo.getDivision() == null || cargo.getDivision().getId() == 0) {
                throw new IllegalArgumentException(mensaje.getErrorEspacioCurricularSinDivision(cargo));
            }

            Division division = divisionService.findById(cargo.getDivision().getId());
            if (division == null) {
                throw new IllegalArgumentException("División id " + cargo.getDivision().getId() + " no encontrada");
            }
            cargo.setDivision(division);
        } else {
            cargo.setDivision(null);
        }
    }
}
