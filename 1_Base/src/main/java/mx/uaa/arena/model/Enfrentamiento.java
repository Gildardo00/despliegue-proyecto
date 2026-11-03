package mx.uaa.arena.model;

public class Enfrentamiento {
    private int idEnfrentamiento;
    private int idJuego;
    private String categoria;
    private String rama;
    private String formato;
    private String fase;
    private int orden;
    private Integer idEquipoA;
    private Integer idEquipoB;
    private int seedA;
    private int seedB;
    private boolean esBye;
    private String nombreEquipoA;
    private String nombreEquipoB;

    public Enfrentamiento() {}

    public int getIdEnfrentamiento() { return idEnfrentamiento; }
    public void setIdEnfrentamiento(int idEnfrentamiento) { this.idEnfrentamiento = idEnfrentamiento; }

    public int getIdJuego() { return idJuego; }
    public void setIdJuego(int idJuego) { this.idJuego = idJuego; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getRama() { return rama; }
    public void setRama(String rama) { this.rama = rama; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public String getFase() { return fase; }
    public void setFase(String fase) { this.fase = fase; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    public Integer getIdEquipoA() { return idEquipoA; }
    public void setIdEquipoA(Integer idEquipoA) { this.idEquipoA = idEquipoA; }

    public Integer getIdEquipoB() { return idEquipoB; }
    public void setIdEquipoB(Integer idEquipoB) { this.idEquipoB = idEquipoB; }

    public int getSeedA() { return seedA; }
    public void setSeedA(int seedA) { this.seedA = seedA; }

    public int getSeedB() { return seedB; }
    public void setSeedB(int seedB) { this.seedB = seedB; }

    public boolean isEsBye() { return esBye; }
    public void setEsBye(boolean esBye) { this.esBye = esBye; }

    public String getNombreEquipoA() { return nombreEquipoA; }
    public void setNombreEquipoA(String nombreEquipoA) { this.nombreEquipoA = nombreEquipoA; }

    public String getNombreEquipoB() { return nombreEquipoB; }
    public void setNombreEquipoB(String nombreEquipoB) { this.nombreEquipoB = nombreEquipoB; }
}
