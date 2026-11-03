package mx.uaa.arena.model;

public class JuegoDisciplina {
    private int idJuego;
    private String nombreDisciplina;
    private String reglaPuntuacion;
    private String tipoCompetencia;
    private String modalidad;
    private boolean activa;

    public JuegoDisciplina() {}

    public int getIdJuego() { return idJuego; }
    public void setIdJuego(int idJuego) { this.idJuego = idJuego; }

    public String getNombreDisciplina() { return nombreDisciplina; }
    public void setNombreDisciplina(String nombreDisciplina) { this.nombreDisciplina = nombreDisciplina; }

    public String getReglaPuntuacion() { return reglaPuntuacion; }
    public void setReglaPuntuacion(String reglaPuntuacion) { this.reglaPuntuacion = reglaPuntuacion; }

    public String getTipoCompetencia() { return tipoCompetencia; }
    public void setTipoCompetencia(String tipoCompetencia) { this.tipoCompetencia = tipoCompetencia; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}
