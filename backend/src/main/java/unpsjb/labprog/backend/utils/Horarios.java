package unpsjb.labprog.backend.utils;

public class Horarios {
    public int id;
    private String dia;
    private int hora;
    private String docente;
    private String nombre;
    private int designacionId;

    public Horarios() {
    }

    public int getId() {
        return id;
    }

    public String getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocente() {
        return docente;
    }

    public int getDesignacionId() {
        return designacionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDesignacionId(int designacionId) {
        this.designacionId = designacionId;
    }

}
