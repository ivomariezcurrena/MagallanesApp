package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.TipoDesignacion;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer>, PagingAndSortingRepository<Cargo, Integer> {

    @Query("""
                SELECT e
                FROM Cargo e
                WHERE
                    FUNCTION('unaccent', UPPER(e.nombre)) LIKE :term
            """)
    Page<Cargo> search(@Param("term") String term, Pageable pageable);

    @Query("select e from Cargo e where e.nombre like ?1")
    List<Cargo> search(String term);

    Cargo findByNombreIgnoreCaseAndTipoDesignacion(String nombre, TipoDesignacion tipo);

    @Query("""
                SELECT c FROM Cargo c
                WHERE UPPER(c.nombre) = UPPER(:nombre)
                  AND c.tipoDesignacion = :tipo
                  AND c.division.anio = :anio
                  AND c.division.numDivision = :numero
                  AND c.division.turno = :turno
            """)
    Cargo findByNombreTipoDivision(
            @Param("nombre") String nombre,
            @Param("tipo") TipoDesignacion tipo,
            @Param("anio") int anio,
            @Param("numero") int numero,
            @Param("turno") unpsjb.labprog.backend.model.Turno turno);

}