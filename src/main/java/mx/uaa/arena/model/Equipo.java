package mx.uaa.arena.model;

public class Equipo {
    private int idEquipo;
    private String nombreEquipo;
    private int idJuego;
    private String categoria;
    private String rama;
    private int seed;
    private String fechaRegistro;
    private String nombreDisciplina;

    public Equipo() {}

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    public int getIdJuego() { return idJuego; }
    public void setIdJuego(int idJuego) { this.idJuego = idJuego; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getRama() { return rama; }
    public void setRama(String rama) { this.rama = rama; }

    public int getSeed() { return seed; }
    public void setSeed(int seed) { this.seed = seed; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getNombreDisciplina() { return nombreDisciplina; }
    public void setNombreDisciplina(String nombreDisciplina) { this.nombreDisciplina = nombreDisciplina; }
}
