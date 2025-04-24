package unpsjb.labprog.backend.business;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.model.Persona;

@Repository
public interface PersonaRepository
        extends CrudRepository<Persona, Integer>, PagingAndSortingRepository<Persona, Integer> {

    @Query("SELECT e FROM Persona e WHERE UPPER(e.nombre) LIKE ?1")
    List<Persona> search(String term);
}
