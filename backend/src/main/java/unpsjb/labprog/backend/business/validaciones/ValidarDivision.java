package unpsjb.labprog.backend.business.validaciones;

import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Division;

@Component
public class ValidarDivision {
    public void validar(Division division) {
        if (division.getAnio() <= 0) {
            throw new IllegalArgumentException("El año debe ser mayor a cero");
        }

        if (division.getNumDivision() <= 0) {
            throw new IllegalArgumentException("El número de división debe ser mayor a cero");
        }

        if (division.getTurno() == null) {
            throw new IllegalArgumentException("El turno no puede ser nulo");
        }

        if (division.getOrientacion() == null || division.getOrientacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La orientación no puede estar vacía");
        }
    }
}
