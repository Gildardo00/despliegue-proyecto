package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PalabraProhibidaDAO {

    public List<String> listar() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT palabra FROM palabra_prohibida ORDER BY palabra";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(rs.getString("palabra"));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean agregar(String palabra) {
        String sql = "INSERT IGNORE INTO palabra_prohibida (palabra) VALUES (?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, palabra.trim().toLowerCase());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(String palabra) {
        String sql = "DELETE FROM palabra_prohibida WHERE palabra = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, palabra.trim().toLowerCase());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
