package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.ArticuloLicencia;
import unpsjb.labprog.backend.model.Licencia;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LicenciaRepository
                extends CrudRepository<Licencia, Integer>, PagingAndSortingRepository<Licencia, Integer> {

        @Query("SELECT l FROM Licencia l WHERE l.persona.dni = ?1 AND l.articulo = ?2 " +
                        "AND l.pedidoDesde <= ?4 AND l.pedidoHasta >= ?3 AND l.id <> ?5")
        List<Licencia> verificarFechas(int dni, ArticuloLicencia articulo, LocalDateTime desde, LocalDateTime hasta,
                        int idActual);

        @Query("SELECT l FROM Licencia l WHERE l.persona.dni = ?1 AND l.articulo.id = ?2 " +
                        "AND YEAR(l.pedidoDesde) = ?3")
        List<Licencia> findAllByPersonaAndArticuloAndAnio(int dni, int articuloId, int anio);

        @Query("""
                            SELECT l FROM Licencia l
                            JOIN l.designaciones d
                            WHERE d.id = :designacionId
                            AND l.pedidoDesde <= :hasta
                            AND l.pedidoHasta >= :desde
                        """)
        List<Licencia> findLicenciasEnPeriodo(
                        @Param("designacionId") int designacionId,
                        @Param("desde") LocalDateTime desde,
                        @Param("hasta") LocalDateTime hasta);

}
