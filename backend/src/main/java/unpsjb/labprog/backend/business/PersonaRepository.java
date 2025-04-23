package unpsjb.labprog.backend.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Persona;

@Repository
public interface PersonaRepository
                extends CrudRepository<Persona, Integer>, PagingAndSortingRepository<Persona, Integer> {

}
