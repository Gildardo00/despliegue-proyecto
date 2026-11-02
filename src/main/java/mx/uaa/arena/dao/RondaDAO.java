package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Ronda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RondaDAO {
    public List<Ronda> listar() {
        List<Ronda> lista = new ArrayList<>();
        String sql = "SELECT * FROM ronda ORDER BY id_ronda";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Ronda obtenerPorId(int id) {
        String sql = "SELECT * FROM ronda WHERE id_ronda = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertar(Ronda r) {
        String sql = "INSERT INTO ronda (nombre_ronda, estatus) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombreRonda());
            ps.setString(2, r.getEstatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Ronda r) {
        String sql = "UPDATE ronda SET nombre_ronda=?, estatus=? WHERE id_ronda=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombreRonda());
            ps.setString(2, r.getEstatus());
            ps.setInt(3, r.getIdRonda());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM ronda WHERE id_ronda = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM ronda";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Ronda mapear(ResultSet rs) throws SQLException {
        Ronda r = new Ronda();
        r.setIdRonda(rs.getInt("id_ronda"));
        r.setNombreRonda(rs.getString("nombre_ronda"));
        r.setEstatus(rs.getString("estatus"));
        return r;
    }
}
