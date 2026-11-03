package mx.uaa.arena.model;

public class Partido {
    private int idPartido;

    private String sede;

    private int idJuego;

    private int idJornada;

    private String nombreDisciplina;

    private int numeroJornada;

    private String nombreRonda;

    private int idEquipoLocal;
    private String nombreLocal;

    private int idEquipoVisitante;
    private String nombreVisitante;

    private int golesLocal;
    private int golesVisitante;

    public Partido() {}

    public int getIdPartido() { return idPartido; }

    public void setIdPartido(int idPartido) { this.idPartido = idPartido; }

    public String getSede() { return sede; }

    public void setSede(String sede) { this.sede = sede; }

    public int getIdJuego() { return idJuego; }

    public void setIdJuego(int idJuego) { this.idJuego = idJuego; }

    public int getIdJornada() { return idJornada; }

    public void setIdJornada(int idJornada) { this.idJornada = idJornada; }

    public String getNombreDisciplina() { return nombreDisciplina; }

    public void setNombreDisciplina(String nombreDisciplina) { this.nombreDisciplina = nombreDisciplina; }

    public int getNumeroJornada() { return numeroJornada; }

    public void setNumeroJornada(int numeroJornada) { this.numeroJornada = numeroJornada; }

    public String getNombreRonda() { return nombreRonda; }

    public void setNombreRonda(String nombreRonda) { this.nombreRonda = nombreRonda; }

    public int getIdEquipoLocal() { return idEquipoLocal; }

    public void setIdEquipoLocal(int idEquipoLocal) { this.idEquipoLocal = idEquipoLocal; }

    public int getIdEquipoVisitante() { return idEquipoVisitante; }

    public void setIdEquipoVisitante(int idEquipoVisitante) { this.idEquipoVisitante = idEquipoVisitante; }

    public String getNombreLocal() { return nombreLocal; }

    public void setNombreLocal(String nombreLocal) { this.nombreLocal = nombreLocal; }

    public String getNombreVisitante() { return nombreVisitante; }

    public void setNombreVisitante(String nombreVisitante) { this.nombreVisitante = nombreVisitante; }

    public int getGolesLocal() { return golesLocal; }

    public void setGolesLocal(int golesLocal) { this.golesLocal = golesLocal; }

    public int getGolesVisitante() { return golesVisitante; }

    public void setGolesVisitante(int golesVisitante) { this.golesVisitante = golesVisitante; }
}
