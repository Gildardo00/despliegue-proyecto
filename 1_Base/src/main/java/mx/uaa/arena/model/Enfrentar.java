package mx.uaa.arena.model;

public class Enfrentar {
    private int idEquipo;

    private int idPartido;

    private String condicion;

    private int golesTotalesLocal;

    private int golesTotalesVisitante;

    private String nombreEquipo;

    public Enfrentar() {}

    public int getIdEquipo() { return idEquipo; }

    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public int getIdPartido() { return idPartido; }

    public void setIdPartido(int idPartido) { this.idPartido = idPartido; }

    public String getCondicion() { return condicion; }

    public void setCondicion(String condicion) { this.condicion = condicion; }

    public int getGolesTotalesLocal() { return golesTotalesLocal; }

    public void setGolesTotalesLocal(int golesTotalesLocal) { this.golesTotalesLocal = golesTotalesLocal; }

    public int getGolesTotalesVisitante() { return golesTotalesVisitante; }

    public void setGolesTotalesVisitante(int golesTotalesVisitante) { this.golesTotalesVisitante = golesTotalesVisitante; }

    public String getNombreEquipo() { return nombreEquipo; }

    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
}
