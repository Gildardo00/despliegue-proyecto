package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracionDAO {

    public String obtener(String clave, String porDefecto) {
        String sql = "SELECT valor FROM configuracion WHERE clave = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, clave);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("valor");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return porDefecto;
    }

    public boolean guardar(String clave, String valor) {
        String sql = "INSERT INTO configuracion (clave, valor) VALUES (?, ?) "
                   + "ON DUPLICATE KEY UPDATE valor = VALUES(valor)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, clave);
            ps.setString(2, valor);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean inscripcionesAbiertas() {
        return "true".equalsIgnoreCase(obtener("inscripciones_abiertas", "true"));
    }

    public boolean setInscripcionesAbiertas(boolean abiertas) {
        return guardar("inscripciones_abiertas", abiertas ? "true" : "false");
    }

    public String fechaCierre() {
        return obtener("fecha_cierre_inscripciones", "");
    }

    public boolean setFechaCierre(String fecha) {
        return guardar("fecha_cierre_inscripciones", fecha == null ? "" : fecha);
    }
}
