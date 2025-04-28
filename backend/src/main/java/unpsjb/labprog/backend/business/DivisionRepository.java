package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Division;

@Repository
public interface DivisionRepository
                extends CrudRepository<Division, Integer>, PagingAndSortingRepository<Division, Integer> {

        @Query("""
                            SELECT e
                            FROM Division e
                            WHERE FUNCTION('unaccent', UPPER(e.orientacion)) LIKE FUNCTION('unaccent', :term)
                        """)
        Page<Division> search(@Param("term") String term, Pageable pageable);

        @Query("SELECT DISTINCT e.orientacion FROM Division e ORDER BY e.orientacion")
        List<String> findAllOrientaciones();
}
