package unpsjb.labprog.backend.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Licencia;

@Repository
public interface LicenciaRepository
        extends CrudRepository<Licencia, Integer>, PagingAndSortingRepository<Licencia, Integer> {

}
