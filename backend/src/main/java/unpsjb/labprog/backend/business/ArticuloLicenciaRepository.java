package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.ArticuloLicencia;

@Repository
public interface ArticuloLicenciaRepository
                extends CrudRepository<ArticuloLicencia, Integer>,
                PagingAndSortingRepository<ArticuloLicencia, Integer> {

        @Query("SELECT a FROM ArticuloLicencia a WHERE a.articulo = ?1")
        List<ArticuloLicencia> findByArticulo(String articulo);

        @Query("SELECT a FROM ArticuloLicencia a WHERE LOWER(a.articulo) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(a.descripcion) LIKE LOWER(CONCAT('%', ?1, '%'))")
        List<ArticuloLicencia> searchByTerm(String term);

}
