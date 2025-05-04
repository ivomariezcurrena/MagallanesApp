package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Persona;

@Repository
public interface PersonaRepository
    extends CrudRepository<Persona, Integer>, PagingAndSortingRepository<Persona, Integer> {

  @Query("""
          SELECT e
          FROM Persona e
          WHERE
            FUNCTION('unaccent', UPPER(e.nombre)) LIKE :term
            OR FUNCTION('unaccent', UPPER(e.apellido)) LIKE :term
            OR CAST(e.dni AS string) LIKE :term
      """)
  Page<Persona> search(@Param("term") String term, Pageable pageable);

  @Query("select e from Persona e where e.nombre like ?1")
  List<Persona> search(String term);
}
