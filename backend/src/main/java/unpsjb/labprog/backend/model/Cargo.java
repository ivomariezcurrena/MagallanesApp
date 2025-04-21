package unpsjb.labprog.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nombre;
    private int cargaHoraria;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name = "tipo_designacion_id")
    private TipoDesignacion tipoDesignacion;

    // Esta es opcional
    @ManyToOne
    @JoinColumn(name = "division_id", nullable = true)
    private Division division;

    @ManyToOne
    @JoinColumn(name = "horario_id")
    private Horario horario;

}
