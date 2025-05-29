package unpsjb.labprog.backend.business.utils.vlicencias;

import java.time.temporal.ChronoUnit;

import unpsjb.labprog.backend.business.LicenciaRepository;
import unpsjb.labprog.backend.business.utils.MensajeFormateador;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.model.Log;

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

        if (!licencia.isCertificadoMedico()) {
            String mensaje = mensajeFormateador.getErrorLicenciaSinCertificado(licencia);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        if (total > topeDias) {
            String mensaje = mensajeFormateador.getErrorLicenciaTopeDias(licencia, topeDias);
            licencia.setEstado(Estado.Invalido);
            AgregarLog.agregarLog(licencia, mensaje, 500);
            return;
        }
        if (licencia.getEstado() != Estado.Invalido) {
            String mensaje = mensajeFormateador.getMensajeLicenciaOtorgada(licencia);
            licencia.setEstado(Estado.Valido);
            AgregarLog.agregarLog(licencia, mensaje, 200);
        }

    }
}
