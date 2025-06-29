package unpsjb.labprog.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "persona_id",
        "cargo_id",
        "fechaInicio",
        "fechaFin"
}))
@Getter
@Setter
@NoArgsConstructor
public class Designacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String situacionRevista;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
}
