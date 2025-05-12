package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Designacion;

@Repository
public interface DesignacionRepository
        extends CrudRepository<Designacion, Integer>, PagingAndSortingRepository<Designacion, Integer> {

    @Query("SELECT d FROM Designacion d WHERE d.cargo.nombre = :nombre")
    Designacion findByNombreDeCargo(@Param("nombre") String nombre);

}
