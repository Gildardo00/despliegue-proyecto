package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.JuegoDisciplina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JuegoDisciplinaDAO {

    public List<JuegoDisciplina> listar() {
        List<JuegoDisciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM juego_disciplina ORDER BY id_juego";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<JuegoDisciplina> listarActivas() {
        List<JuegoDisciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM juego_disciplina WHERE activa = 1 ORDER BY nombre_disciplina";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public JuegoDisciplina obtenerPorId(int id) {
        String sql = "SELECT * FROM juego_disciplina WHERE id_juego = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertar(JuegoDisciplina jd) {
        String sql = "INSERT INTO juego_disciplina (nombre_disciplina, regla_puntuacion, tipo_competencia, modalidad, activa) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, jd.getNombreDisciplina());
            ps.setString(2, jd.getReglaPuntuacion());
            ps.setString(3, jd.getTipoCompetencia());
            ps.setString(4, jd.getModalidad());
            ps.setInt(5, jd.isActiva() ? 1 : 0);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(JuegoDisciplina jd) {
        String sql = "UPDATE juego_disciplina SET nombre_disciplina=?, regla_puntuacion=?, tipo_competencia=?, modalidad=?, activa=? "
                   + "WHERE id_juego=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, jd.getNombreDisciplina());
            ps.setString(2, jd.getReglaPuntuacion());
            ps.setString(3, jd.getTipoCompetencia());
            ps.setString(4, jd.getModalidad());
            ps.setInt(5, jd.isActiva() ? 1 : 0);
            ps.setInt(6, jd.getIdJuego());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM juego_disciplina WHERE id_juego = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM juego_disciplina";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private JuegoDisciplina mapear(ResultSet rs) throws SQLException {
        JuegoDisciplina jd = new JuegoDisciplina();
        jd.setIdJuego(rs.getInt("id_juego"));
        jd.setNombreDisciplina(rs.getString("nombre_disciplina"));
        jd.setReglaPuntuacion(rs.getString("regla_puntuacion"));
        jd.setTipoCompetencia(rs.getString("tipo_competencia"));
        jd.setModalidad(rs.getString("modalidad"));
        jd.setActiva(rs.getInt("activa") == 1);
        return jd;
    }
}
