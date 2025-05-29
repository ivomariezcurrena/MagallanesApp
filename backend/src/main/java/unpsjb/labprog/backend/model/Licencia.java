package unpsjb.labprog.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDateTime pedidoDesde;
    private LocalDateTime pedidoHasta;
    private String domicilio;
    private boolean certificadoMedico;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToMany
    @JoinTable(name = "licencia_designacion", joinColumns = @JoinColumn(name = "licencia_id"), inverseJoinColumns = @JoinColumn(name = "designacion_id"))
    private Collection<Designacion> designaciones;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "dni")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private ArticuloLicencia articulo;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "licencia_id")
    private Collection<Log> logs = new ArrayList<>();

}
