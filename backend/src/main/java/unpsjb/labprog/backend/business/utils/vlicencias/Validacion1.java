package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;

import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Licencia;
import java.util.List;

public class Validacion1 implements Validable {

    private final LicenciaRepository licenciaRepository;
    private final MensajeFormateador mensajeFormateador;
    private final int topeDias;

    public Validacion1(LicenciaRepository licenciaRepository, MensajeFormateador mensajeFormateador, int topeDias) {
        this.licenciaRepository = licenciaRepository;
        this.mensajeFormateador = mensajeFormateador;
        this.topeDias = topeDias;
    }

    @Override
    public void validar(Licencia licencia) {
        int anio = licencia.getPedidoDesde().getYear();
        List<Licencia> licenciasAnio = licenciaRepository.findAllByPersonaAndArticuloAndAnio(
                licencia.getPersona().getDni(),
                licencia.getArticulo().getId(),
                anio);

        int diasUsados = 0;
        for (Licencia l : licenciasAnio) {
            if (licencia.getId() != 0 && licencia.getId() == l.getId())
                continue;
            diasUsados += (int) ChronoUnit.DAYS.between(l.getPedidoDesde().toLocalDate(),
                    l.getPedidoHasta().toLocalDate()) + 1;
        }

        int diasActual = (int) ChronoUnit.DAYS.between(
                licencia.getPedidoDesde().toLocalDate(),
                licencia.getPedidoHasta().toLocalDate()) + 1;

        int total = diasUsados + diasActual;

        if (total > topeDias) {
            throw new IllegalArgumentException(
                    mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeDias));
        }
    }
}
