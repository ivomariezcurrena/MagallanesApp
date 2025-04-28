package unpsjb.labprog.backend.model;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Persona {
    @Id
    private int dni;

    private String cuil;
    private String nombre;
    private String apellido;
    private String titulo;
    private char sexo;
    private String domicilio;
    private String telefono;

    @OneToMany(mappedBy = "persona")
    private Collection<Designacion> desiganciones;

}
