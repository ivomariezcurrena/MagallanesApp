package unpsjb.labprog.backend.business.validaciones;

public interface ValidadorGenerico<T> {
    void validar(T entidad);
}