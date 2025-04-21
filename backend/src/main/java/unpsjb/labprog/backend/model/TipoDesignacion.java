package unpsjb.labprog.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TipoDesignacion {

    @Id
    private String nombre; // Ej: "CARGO" o "ESPACIO CURRICULAR"
}