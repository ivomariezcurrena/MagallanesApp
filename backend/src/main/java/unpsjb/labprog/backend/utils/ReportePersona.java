package unpsjb.labprog.backend.utils;

public class ReportePersona {
    public int id;
    public String nombre;
    public String apellido;
    public int cantidadLicencias;
    public int totalDias;
    public int porcentajePresencia;

    public static class Builder {
        private final ReportePersona persona = new ReportePersona();

        public Builder id(int id) {
            persona.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            persona.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            persona.apellido = apellido;
            return this;
        }

        public Builder cantidadLicencias(int cantidadLicencias) {
            persona.cantidadLicencias = cantidadLicencias;
            return this;
        }

        public Builder totalDias(int totalDias) {
            persona.totalDias = totalDias;
            return this;
        }

        public Builder porcentajePresencia(int porcentajePresencia) {
            persona.porcentajePresencia = porcentajePresencia;
            return this;
        }

        public ReportePersona build() {
            return persona;
        }
    }
}