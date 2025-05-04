package unpsjb.labprog.backend.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Designacion;

@Repository
public interface DesignacionRepository
                extends CrudRepository<Designacion, Integer>, PagingAndSortingRepository<Designacion, Integer> {

}
