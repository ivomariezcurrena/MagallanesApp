package unpsjb.labprog.backend.business;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
                        "AND l.pedidoDesde <= ?4 AND l.pedidoHasta >= ?3 AND l.id <> ?5 AND l.estado = 'Valido'")
        List<Licencia> verificarFechas(int dni, ArticuloLicencia articulo, LocalDateTime desde, LocalDateTime hasta,
                        int idActual);

        @Query("SELECT l FROM Licencia l WHERE l.persona.dni = ?1 AND l.articulo.id = ?2 " +
                        "AND YEAR(l.pedidoDesde) = ?3 AND l.estado = 'Valido'")
        List<Licencia> findAllByPersonaAndArticuloAndAnio(int dni, int articuloId, int anio);

        @Query("""
                            SELECT l FROM Licencia l
                            JOIN l.designaciones d
                            WHERE d.id = :designacionId
                            AND l.estado = 'Valido'
                            AND l.pedidoDesde <= :hasta
                            AND l.pedidoHasta >= :desde
                        """)
        List<Licencia> findLicenciasEnPeriodo(
                        @Param("designacionId") int designacionId,
                        @Param("desde") LocalDateTime desde,
                        @Param("hasta") LocalDateTime hasta);

        @Query("SELECT l FROM Licencia l WHERE l.pedidoHasta >= :fechaDesde AND l.pedidoDesde <= :fechaDesde AND l.estado = 'Valido'")
        Page<Licencia> findAllDesdeFecha(
                        @Param("fechaDesde") LocalDateTime fechaDesde,
                        Pageable pageable);

        @Query("SELECT l FROM Licencia l WHERE l.estado = 'Valido'")
        Page<Licencia> findAllValidas(Pageable pageable);

        @Query("""
                            SELECT l FROM Licencia l
                            WHERE l.estado = 'Valido'
                                AND l.pedidoDesde <= :fecha
                                AND l.pedidoHasta >= :fecha
                        """)
        List<Licencia> findLicenciasVigentesEnFecha(@Param("fecha") LocalDateTime fecha);

}
