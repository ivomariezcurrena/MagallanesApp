package unpsjb.labprog.backend.business;

import java.time.LocalDateTime;
import java.util.List;

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
                        SELECT d FROM Designacion d
                        WHERE d.cargo.nombre = :nombreCargo
                        AND (:designacionId IS NULL OR d.id != :designacionId)
                        AND (
                            (d.fechaFin IS NULL AND CAST(:nuevaFechaFin AS java.time.LocalDateTime) IS NULL)
                            OR
                            (
                                COALESCE(d.fechaFin, :nuevaFechaFin) >= :nuevaFechaInicio
                                AND
                                COALESCE(:nuevaFechaFin, d.fechaFin) >= d.fechaInicio
                            )
                        )
                        """)
        List<Designacion> findDesignacionActivaOSolapada(
                        @Param("nombreCargo") String nombreCargo,
                        @Param("nuevaFechaInicio") LocalDateTime nuevaFechaInicio,
                        @Param("nuevaFechaFin") LocalDateTime nuevaFechaFin,
                        @Param("designacionId") Integer designacionId);

        // Para cargos con division
        @Query("""
                            SELECT d FROM Designacion d
                            WHERE d.cargo.nombre = :nombreCargo
                            AND (:designacionId IS NULL OR d.id != :designacionId)
                            AND d.cargo.division.anio = :anio
                            AND d.cargo.division.numDivision = :numDivision
                            AND d.cargo.division.turno = :turno
                            AND (
                                (d.fechaFin IS NULL AND CAST(:nuevaFechaFin AS java.time.LocalDateTime) IS NULL)
                                OR
                                (
                                    COALESCE(d.fechaFin, :nuevaFechaFin) >= :nuevaFechaInicio
                                    AND
                                    COALESCE(:nuevaFechaFin, d.fechaFin) >= d.fechaInicio
                                )
                            )
                        """)
        List<Designacion> findSolapamientoEnDivision(
                        @Param("nombreCargo") String nombreCargo,
                        @Param("anio") int anio,
                        @Param("numDivision") int numDivision,
                        @Param("turno") Turno turno,
                        @Param("nuevaFechaInicio") LocalDateTime nuevaFechaInicio,
                        @Param("nuevaFechaFin") LocalDateTime nuevaFechaFin,
                        @Param("designacionId") Integer designacionId);

        @Query("SELECT d FROM Designacion d WHERE d.persona.dni = :dni")
        List<Designacion> findByPersonaDni(@Param("dni") int dni);

        @Query("SELECT d FROM Designacion d WHERE d.persona.dni = :dni AND " +
                        "(d.fechaFin IS NULL OR d.fechaFin >= :desde) AND d.fechaInicio <= :hasta")
        List<Designacion> findDesignacionesActivasEnPeriodo(
                        @Param("dni") int dni,
                        @Param("desde") LocalDateTime desde,
                        @Param("hasta") LocalDateTime hasta);

        @Query("""
                            SELECT d FROM Designacion d
                            WHERE d.cargo.id = :cargoId
                            AND d.id <> :designacionId
                            AND (d.fechaFin >= :desde)
                            AND d.fechaInicio <= :hasta
                        """)
        List<Designacion> findDesignacionesSuplentes(
                        @Param("cargoId") int cargoId,
                        @Param("desde") LocalDateTime desde,
                        @Param("hasta") LocalDateTime hasta,
                        @Param("designacionId") Integer designacionId);
}
