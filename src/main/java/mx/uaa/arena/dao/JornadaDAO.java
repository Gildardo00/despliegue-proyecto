package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Jornada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JornadaDAO {
    private static final String BASE =
        "SELECT j.*, r.nombre_ronda AS nombre_ronda "
      + "FROM jornada j JOIN ronda r ON j.id_ronda = r.id_ronda ";

    public List<Jornada> listar() {
        List<Jornada> lista = new ArrayList<>();

        String sql = BASE + "ORDER BY j.id_jornada";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Jornada obtenerPorId(int id) {
        String sql = BASE + "WHERE j.id_jornada = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertar(Jornada j) {
        String sql = "INSERT INTO jornada (numero_jornada, fecha_inicio, fecha_fin, id_ronda) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, j.getNumeroJornada());
            ps.setString(2, j.getFechaInicio());

            setFechaFin(ps, 3, j.getFechaFin());
            ps.setInt(4, j.getIdRonda());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Jornada j) {
        String sql = "UPDATE jornada SET numero_jornada=?, fecha_inicio=?, fecha_fin=?, id_ronda=? WHERE id_jornada=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, j.getNumeroJornada());
            ps.setString(2, j.getFechaInicio());
            setFechaFin(ps, 3, j.getFechaFin());
            ps.setInt(4, j.getIdRonda());
            ps.setInt(5, j.getIdJornada());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM jornada WHERE id_jornada = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM jornada";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private void setFechaFin(PreparedStatement ps, int idx, String fechaFin) throws SQLException {
        if (fechaFin == null || fechaFin.trim().isEmpty()) {
            ps.setNull(idx, java.sql.Types.DATE);
        } else {
            ps.setString(idx, fechaFin);
        }
    }

    private Jornada mapear(ResultSet rs) throws SQLException {
        Jornada j = new Jornada();
        j.setIdJornada(rs.getInt("id_jornada"));
        j.setNumeroJornada(rs.getInt("numero_jornada"));
        j.setFechaInicio(rs.getString("fecha_inicio"));
        j.setFechaFin(rs.getString("fecha_fin"));
        j.setIdRonda(rs.getInt("id_ronda"));

        j.setNombreRonda(rs.getString("nombre_ronda"));
        return j;
    }
}
