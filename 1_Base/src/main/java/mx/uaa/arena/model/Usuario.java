package mx.uaa.arena.model;

public class Usuario {
    private int idUsuario;

    private String nombre;

    private String correo;

    private String usuario;

    private String contrasena;

    private String rol;

    private String fechaRegistro;

    public Usuario() {}

    public int getIdUsuario() { return idUsuario; }

    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }

    public String getFechaRegistro() { return fechaRegistro; }

    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
