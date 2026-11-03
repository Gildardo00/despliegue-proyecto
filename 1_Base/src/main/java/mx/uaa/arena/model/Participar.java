package mx.uaa.arena.model;

public class Participar {
    private int idJugador;

    private int idPartido;

    private int numeroP;

    private int golesIndividuales;

    private boolean partidaGanada;

    private String nombreJugador;

    private String nombreEquipo;

    private String etiquetaPartido;

    public Participar() {}

    public int getIdJugador() { return idJugador; }

    public void setIdJugador(int idJugador) { this.idJugador = idJugador; }

    public int getIdPartido() { return idPartido; }

    public void setIdPartido(int idPartido) { this.idPartido = idPartido; }

    public int getNumeroP() { return numeroP; }

    public void setNumeroP(int numeroP) { this.numeroP = numeroP; }

    public int getGolesIndividuales() { return golesIndividuales; }

    public void setGolesIndividuales(int golesIndividuales) { this.golesIndividuales = golesIndividuales; }

    public boolean isPartidaGanada() { return partidaGanada; }

    public void setPartidaGanada(boolean partidaGanada) { this.partidaGanada = partidaGanada; }

    public String getNombreJugador() { return nombreJugador; }

    public void setNombreJugador(String nombreJugador) { this.nombreJugador = nombreJugador; }

    public String getNombreEquipo() { return nombreEquipo; }

    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    public String getEtiquetaPartido() { return etiquetaPartido; }

    public void setEtiquetaPartido(String etiquetaPartido) { this.etiquetaPartido = etiquetaPartido; }
}
