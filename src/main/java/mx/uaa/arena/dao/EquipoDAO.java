package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Equipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private static final String BASE =
        "SELECT e.*, jd.nombre_disciplina AS nombre_disciplina "
      + "FROM equipo e LEFT JOIN juego_disciplina jd ON e.id_juego = jd.id_juego ";

    public List<Equipo> listar() {
        List<Equipo> lista = new ArrayList<>();
        String sql = BASE + "ORDER BY e.id_equipo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Equipo> listarPorGrupo(int idJuego, String categoria, String rama) {
        List<Equipo> lista = new ArrayList<>();
        String sql = BASE + "WHERE e.id_juego = ? AND e.categoria = ? AND e.rama = ? "
                   + "ORDER BY CASE WHEN e.seed = 0 THEN 999999 ELSE e.seed END, e.id_equipo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            ps.setString(2, categoria);
            ps.setString(3, rama);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Equipo obtenerPorId(int id) {
        String sql = BASE + "WHERE e.id_equipo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean existeNombre(String nombre, int idExcluir) {
        String sql = "SELECT id_equipo FROM equipo WHERE nombre_equipo = ? AND id_equipo <> ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { e.printStackTrace(); return true; }
    }

    public int insertar(Equipo e) {
        String sql = "INSERT INTO equipo (nombre_equipo, id_juego, categoria, rama, seed) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNombreEquipo());
            asignarJuego(ps, 2, e.getIdJuego());
            ps.setString(3, e.getCategoria());
            ps.setString(4, e.getRama());
            ps.setInt(5, e.getSeed());
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return -1;
    }

    public boolean actualizar(Equipo e) {
        String sql = "UPDATE equipo SET nombre_equipo=?, id_juego=?, categoria=?, rama=?, seed=? WHERE id_equipo=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombreEquipo());
            asignarJuego(ps, 2, e.getIdJuego());
            ps.setString(3, e.getCategoria());
            ps.setString(4, e.getRama());
            ps.setInt(5, e.getSeed());
            ps.setInt(6, e.getIdEquipo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM equipo WHERE id_equipo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM equipo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) { ex.printStackTrace(); }
        return 0;
    }

    private void asignarJuego(PreparedStatement ps, int idx, int idJuego) throws SQLException {
        if (idJuego <= 0) ps.setNull(idx, java.sql.Types.INTEGER);
        else ps.setInt(idx, idJuego);
    }

    private Equipo mapear(ResultSet rs) throws SQLException {
        Equipo e = new Equipo();
        e.setIdEquipo(rs.getInt("id_equipo"));
        e.setNombreEquipo(rs.getString("nombre_equipo"));
        int idJuego = rs.getInt("id_juego");
        e.setIdJuego(rs.wasNull() ? 0 : idJuego);
        e.setCategoria(rs.getString("categoria"));
        e.setRama(rs.getString("rama"));
        e.setSeed(rs.getInt("seed"));
        e.setFechaRegistro(rs.getString("fecha_registro"));
        e.setNombreDisciplina(rs.getString("nombre_disciplina"));
        return e;
    }
}
