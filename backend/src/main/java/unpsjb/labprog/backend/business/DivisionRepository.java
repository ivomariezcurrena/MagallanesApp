package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Turno;

@Repository
public interface DivisionRepository
    extends CrudRepository<Division, Integer>, PagingAndSortingRepository<Division, Integer> {

  @Query("""
          SELECT e
          FROM Division e
          WHERE FUNCTION('unaccent', UPPER(e.orientacion)) LIKE FUNCTION('unaccent', :term)
      """)
  Page<Division> searchPage(@Param("term") String term, Pageable pageable);

  @Query("SELECT DISTINCT e.orientacion FROM Division e ORDER BY e.orientacion")
  List<String> findAllOrientaciones();

  @Query("""
          SELECT d
            FROM Division d
           WHERE d.anio = :anio
             AND d.numDivision = :numDivision
             AND d.turno = :turno
      """)
  Optional<Division> findByAnioNumDivisionAndTurno(
      @Param("anio") int anio,
      @Param("numDivision") int numDivision,
      @Param("turno") Turno turno);

  @Query("select e from Division e where e.orientacion like ?1")
  List<Division> search(String term);
}
