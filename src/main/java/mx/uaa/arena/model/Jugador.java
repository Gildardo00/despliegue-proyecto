package mx.uaa.arena.model;

public class Jugador {
    private int idJugador;
    private String nombreCompleto;
    private Integer idEquipo;
    private Integer idJuego;
    private String categoria;
    private String rama;
    private Integer edad;
    private String nombreEquipo;
    private String nombreDisciplina;

    public Jugador() {}

    public int getIdJugador() { return idJugador; }
    public void setIdJugador(int idJugador) { this.idJugador = idJugador; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }

    public Integer getIdJuego() { return idJuego; }
    public void setIdJuego(Integer idJuego) { this.idJuego = idJuego; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getRama() { return rama; }
    public void setRama(String rama) { this.rama = rama; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    public String getNombreDisciplina() { return nombreDisciplina; }
    public void setNombreDisciplina(String nombreDisciplina) { this.nombreDisciplina = nombreDisciplina; }
}
