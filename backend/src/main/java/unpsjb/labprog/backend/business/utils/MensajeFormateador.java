package unpsjb.labprog.backend.business.utils;

import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.model.TipoDesignacion;
import unpsjb.labprog.backend.model.Turno;

@Component
public class MensajeFormateador {

    // MENSAJES DE PERSONA //
    public String getMensajeAgregarPersona(Persona persona) {
        String mensaje = String.format("%s %s con DNI %d ingresado/a correctamente",
                persona.getNombre(),
                persona.getApellido(),
                persona.getDni());
        return mensaje;
    }

    public String getMensajeActualizarPersona(Persona persona) {
        return String.format("%s %s con DNI %d actualizado/a correctamente",
                persona.getNombre(),
                persona.getApellido(),
                persona.getDni());
    }

    public String getMensajeEliminarPersona(int dni) {
        return "Persona dni " + dni + " eliminada";
    }

    public String getMensajePersonaNoEncontrada(int dni) {
        return "Persona dni " + dni + " no encontrada";
    }

    public String getMensajeDniDuplicado() {
        return "No se puede utilizar ese DNI porque ya existe otra persona con el mismo";
    }

    // MENSAJE DE DIVISION //
    public String getMensajeAgregarDivision(Division d) {
        return String.format("División %d %d turno %s ingresada correctamente",
                d.getAnio(), d.getNumDivision(), d.getTurno());
    }

    public String getMensajeActualizarDivision(Division d) {
        return String.format("División %d %d turno %s actualizada correctamente",
                d.getAnio(), d.getNumDivision(), d.getTurno());
    }

    public String getMensajeEliminacionDeDivision(int id) {
        return "División " + id + " eliminada correctamente";
    }

    public String getMensajeDivisionNoEncontrada(int id) {
        return "División id " + id + " no encontrada";
    }

    // MENSAJES DE CARGO //
    public String getMensajeAgregarCargo(Cargo c) {
        if (c.getTipoDesignacion() == TipoDesignacion.CARGO) {
            return String.format("Cargo de %s ingresado correctamente", c.getNombre());
        } else {
            Division d = c.getDivision();
            return String.format(
                    "Espacio Curricular %s para la división %dº %dº Turno %s ingresado correctamente",
                    c.getNombre(),
                    d.getAnio(),
                    d.getNumDivision(),
                    d.getTurno());
        }
    }

    public String getMensajeActualizarCargo(Cargo c) {
        return String.format("Cargo %s actualizado correctamente", c.getNombre());
    }

    public String getMensajeEliminacionDeCargo(int id) {
        return "Cargo " + id + " eliminado correctamente";
    }

    public String getMensajeCargoNoEncontrado(int id) {
        return "Cargo id " + id + " no encontrado";
    }

    public String getErrorCargoNoDebeTenerDivision(Cargo c) {
        return String.format(
                "Cargo de %s es CARGO y no corresponde asignar división",
                c.getNombre());
    }

    public String getErrorEspacioCurricularSinDivision(Cargo c) {
        return String.format(
                "Espacio Curricular %s falta asignar división",
                c.getNombre());
    }

    // MENSAJES DE DESIGNACION //
    public String getMensajeAgregarDesignacion(Designacion d) {
        if (d.getCargo().getTipoDesignacion() != TipoDesignacion.CARGO) {
            return String.format(
                    "%s %s ha sido designado/a a la asignatura %s a la división %dº %dº turno %s exitosamente",
                    d.getPersona().getNombre(),
                    d.getPersona().getApellido(),
                    d.getCargo().getNombre(),
                    d.getCargo().getDivision().getAnio(),
                    d.getCargo().getDivision().getNumDivision(),
                    d.getCargo().getDivision().getTurno());
        } else {
            return String.format(
                    "%s %s ha sido designado/a como %s exitosamente",
                    d.getPersona().getNombre(),
                    d.getPersona().getApellido(),
                    d.getCargo().getNombre());
        }
    }

    public String getMensajeActualizarDesignacion(Designacion d) {
        return getMensajeAgregarDesignacion(d)
                .replace("ingresado/a correctamente", "actualizado/a correctamente");
    }

    public String getMensajeEliminacionDeDesignacion(int id) {
        return "Designación " + id + " eliminada correctamente";
    }

    public String getMensajeDesignacionNoEncontrada(int id) {
        return "Designación id " + id + " no encontrada";
    }

    public String getErrorCargoNuloEnDesignacion() {
        return "El cargo no puede ser nulo";
    }

    public String getErrorPersonaNulaEnDesignacion() {
        return "La persona no puede ser nula";
    }

    public String getErrorDivisionFaltanteEnDesignacion() {
        return "El espacio curricular debe tener una división asignada";
    }

    // NUEVO: Mensaje de error para designación ya existente en el período
    public String getErrorDesignacionYaExisteCargo(String nombrePersona, String apellidoPersona, String nombreCargo,
            String nombreOcupante, String apellidoOcupante) {
        return String.format(
                "%s %s NO ha sido designado/a como %s. pues el cargo solicitado lo ocupa %s %s para el período",
                nombrePersona,
                apellidoPersona,
                nombreCargo,
                nombreOcupante,
                apellidoOcupante);
    }

    public String getErrorDesignacionYaExisteEspacioCurricular(String nombrePersona, String apellidoPersona,
            String nombreEspacio, int anio, int numDivision, Turno turno, String nombreOcupante,
            String apellidoOcupante) {
        return String.format(
                "%s %s NO ha sido designado/a debido a que la asignatura %s de la división %dº %dº turno %s lo ocupa %s %s para el período",
                nombrePersona,
                apellidoPersona,
                nombreEspacio,
                anio,
                numDivision,
                turno,
                nombreOcupante,
                apellidoOcupante);
    }

    public String getMensajeDesignacionEnReemplazo(String nombre, String apellido, String cargo,
            String nombreReemplazado, String apellidoReemplazado) {
        return String.format("%s %s ha sido designado/a al cargo %s exitosamente, en reemplazo de %s %s",
                nombre, apellido, cargo, nombreReemplazado, apellidoReemplazado);
    }

    // MENSAJE LICENCIA

    public String getMensajeAgregarLicencia(Licencia l) {
        return "Todo ok";
    }

    public String getMensajeActualizarLicencia(Licencia l) {
        return getMensajeAgregarLicencia(l)
                .replace("ingresado/a correctamente", "actualizado/a correctamente");
    }

    public String getMensajeEliminacionDeLicencia(int id) {
        return "Designación " + id + " eliminada correctamente";
    }

    public String getMensajeLicenciaNoEncontrada(int id) {
        return "Designación id " + id + " no encontrada";
    }

    public String getErrorCargoNuloEnLicencia() {
        return "El cargo no puede ser nulo";
    }

    public String getErrorPersonaNulaEnLicencia() {
        return "La persona no puede ser nula";
    }

    public String getErrorDivisionFaltanteEnLicencia() {
        return "El espacio curricular debe tener una división asignada";
    }

    public String getMensajeLicenciaOtorgada(Licencia l) {
        return String.format("Se otorga Licencia artículo %s a %s %s",
                l.getArticulo().getArticulo(),
                l.getPersona().getNombre(),
                l.getPersona().getApellido());
    }

    public String getErrorLicenciaSuperposicion(Licencia l) {
        return String.format(
                "NO se otorga Licencia artículo %s a %s %s debido a que ya posee una licencia en el mismo período",
                l.getArticulo().getArticulo(),
                l.getPersona().getNombre(),
                l.getPersona().getApellido());
    }

    public String getErrorLicenciaTopeDias(Licencia l, int tope) {
        return String.format(
                "NO se otorga Licencia artículo %s a %s %s debido a que supera el tope de %d días de licencia",
                l.getArticulo().getArticulo(),
                l.getPersona().getNombre(),
                l.getPersona().getApellido(),
                tope);
    }

    public String getErrorLicenciaTopeDias36AAnual(Licencia l, int tope) {
        return String.format(
                "NO se otorga Licencia artículo 36A a %s %s debido a que supera el tope de %d días de licencia por año",
                l.getPersona().getNombre(),
                l.getPersona().getApellido(),
                tope);
    }

    public String getErrorLicenciaSinCargo(Licencia l) {
        return String.format(
                "NO se otorga Licencia artículo %s a %s %s debido a que el agente no posee ningún cargo en la institución",
                l.getArticulo().getArticulo(),
                l.getPersona().getNombre(),
                l.getPersona().getApellido());
    }

    public String getErrorLicenciaSinDesignacionEseDia(Licencia l) {
        return String.format(
                "NO se otorga Licencia artículo %s a %s %s debido a que el agente no tiene designación ese día en la institución",
                l.getArticulo().getArticulo(),
                l.getPersona().getNombre(),
                l.getPersona().getApellido());
    }

    // MENSAJES ARTICULO LICENCIA //
    public String getMensajeAgregarArticuloLicencia(unpsjb.labprog.backend.model.ArticuloLicencia a) {
        return String.format("Artículo de licencia '%s' ingresado correctamente", a.getArticulo());
    }

    public String getMensajeActualizarArticuloLicencia(unpsjb.labprog.backend.model.ArticuloLicencia a) {
        return String.format("Artículo de licencia '%s' actualizado correctamente", a.getArticulo());
    }

    public String getMensajeEliminacionDeArticuloLicencia(int id) {
        return "Artículo de licencia " + id + " eliminado correctamente";
    }

    public String getMensajeArticuloLicenciaNoEncontrado(int id) {
        return "Artículo de licencia id " + id + " no encontrado";
    }
}
