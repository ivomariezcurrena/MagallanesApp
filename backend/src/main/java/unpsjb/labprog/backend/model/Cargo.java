package unpsjb.labprog.backend.model;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "nombre", "fechaInicio", "tipo_designacion",
        "division_id" }))
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

    @Column(nullable = true)
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_designacion")
    private TipoDesignacion tipoDesignacion;

    @ManyToOne(optional = true)
    private Division division;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "cargo_id", nullable = false)
    private Collection<Horario> horarios;

}
