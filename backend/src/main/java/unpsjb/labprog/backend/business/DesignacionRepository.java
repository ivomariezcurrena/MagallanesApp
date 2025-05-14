package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Turno;

@Repository
public interface DesignacionRepository
        extends CrudRepository<Designacion, Integer>, PagingAndSortingRepository<Designacion, Integer> {

    @Query("SELECT d FROM Designacion d WHERE d.cargo.nombre = :nombre")
    Designacion findByNombreDeCargo(@Param("nombre") String nombre);

    // Para cargos sim division
    @Query("""
            SELECT d from Designacion d
            WHERE d.cargo.nombre = :nombreCargo
            AND(
            d.fechaFin IS NULL OR :nuevaFechaInicio <=d.fechaFin
            )
            """)
    Optional<Designacion> findDesignacionActivaOSolapada(
            @Param("nombreCargo") String nombreCargo,
            @Param("nuevaFechaInicio") LocalDateTime nuevaFechaInicio);

    // Para cargos con division
    @Query("""
                SELECT d FROM Designacion d
                WHERE d.cargo.nombre = :nombreCargo
                AND d.cargo.division.anio = :anio
                AND d.cargo.division.numDivision = :numDivision
                AND d.cargo.division.turno = :turno
                AND (
                    d.fechaFin IS NULL OR
                    :nuevaFechaInicio <= d.fechaFin
                )
            """)
    Optional<Designacion> findSolapamientoEnDivision(
            @Param("nombreCargo") String nombreCargo,
            @Param("anio") int anio,
            @Param("numDivision") int numDivision,
            @Param("turno") Turno turno,
            @Param("nuevaFechaInicio") LocalDateTime nuevaFechaInicio);
}
