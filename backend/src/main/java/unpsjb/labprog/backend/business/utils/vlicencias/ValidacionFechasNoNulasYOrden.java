package unpsjb.labprog.backend.business.utils.vlicencias;

import unpsjb.labprog.backend.model.Licencia;

public class ValidacionFechasNoNulasYOrden implements Validable {

    @Override
    public void validar(Licencia licencia) {
        if (licencia.getPedidoDesde() == null) {
            throw new IllegalArgumentException("La fecha desde no puede ser nula");
        }
        if (licencia.getPedidoHasta() == null) {
            throw new IllegalArgumentException("La fecha hasta no puede ser nula");
        }
        if (licencia.getPedidoDesde().isAfter(licencia.getPedidoHasta())) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
        }
    }

}
