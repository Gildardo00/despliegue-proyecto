package mx.uaa.arena.model;

public class Jornada {
    private int idJornada;

    private int numeroJornada;

    private String fechaInicio;

    private String fechaFin;

    private int idRonda;

    private String nombreRonda;

    public Jornada() {}

    public int getIdJornada() { return idJornada; }

    public void setIdJornada(int idJornada) { this.idJornada = idJornada; }

    public int getNumeroJornada() { return numeroJornada; }

    public void setNumeroJornada(int numeroJornada) { this.numeroJornada = numeroJornada; }

    public String getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }

    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public int getIdRonda() { return idRonda; }

    public void setIdRonda(int idRonda) { this.idRonda = idRonda; }

    public String getNombreRonda() { return nombreRonda; }

    public void setNombreRonda(String nombreRonda) { this.nombreRonda = nombreRonda; }
}
