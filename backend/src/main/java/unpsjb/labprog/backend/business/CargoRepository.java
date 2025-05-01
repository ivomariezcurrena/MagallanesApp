package unpsjb.labprog.backend.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer>, PagingAndSortingRepository<Cargo, Integer> {

    @Query("""
                SELECT e
                FROM Cargo e
                WHERE
                    FUNCTION('unaccent', UPPER(e.nombre)) LIKE :term
            """)
    Page<Cargo> search(@Param("term") String term, Pageable pageable);
}