package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private static final String BASE =
        "SELECT j.*, eq.nombre_equipo AS nombre_equipo, jd.nombre_disciplina AS nombre_disciplina "
      + "FROM jugador j "
      + "LEFT JOIN equipo eq ON j.id_equipo = eq.id_equipo "
      + "LEFT JOIN juego_disciplina jd ON j.id_juego = jd.id_juego ";

    public List<Jugador> listar() {
        List<Jugador> lista = new ArrayList<>();
        String sql = BASE + "ORDER BY j.id_jugador";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Jugador> listarClasificados() {
        List<Jugador> lista = new ArrayList<>();
        String sql = BASE + "ORDER BY jd.nombre_disciplina, j.categoria, j.rama, j.nombre_completo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Jugador> listarPoolPorDisciplina(int idJuego) {
        List<Jugador> lista = new ArrayList<>();
        String sql = BASE + "WHERE j.id_juego = ? AND j.id_equipo IS NULL ORDER BY j.id_jugador";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Jugador obtenerPorId(int id) {
        String sql = BASE + "WHERE j.id_jugador = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertar(Jugador j) {
        String sql = "INSERT INTO jugador (nombre_completo, id_equipo, id_juego, categoria, rama, edad) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getNombreCompleto());
            asignarEntero(ps, 2, j.getIdEquipo());
            asignarEntero(ps, 3, j.getIdJuego());
            ps.setString(4, j.getCategoria());
            ps.setString(5, j.getRama());
            asignarEntero(ps, 6, j.getEdad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Jugador j) {
        String sql = "UPDATE jugador SET nombre_completo=?, id_equipo=?, id_juego=?, categoria=?, rama=?, edad=? "
                   + "WHERE id_jugador=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getNombreCompleto());
            asignarEntero(ps, 2, j.getIdEquipo());
            asignarEntero(ps, 3, j.getIdJuego());
            ps.setString(4, j.getCategoria());
            ps.setString(5, j.getRama());
            asignarEntero(ps, 6, j.getEdad());
            ps.setInt(7, j.getIdJugador());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean asignarEquipo(int idJugador, int idEquipo) {
        String sql = "UPDATE jugador SET id_equipo=? WHERE id_jugador=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEquipo);
            ps.setInt(2, idJugador);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM jugador WHERE id_jugador = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM jugador";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private void asignarEntero(PreparedStatement ps, int idx, Integer valor) throws SQLException {
        if (valor == null || valor <= 0) ps.setNull(idx, java.sql.Types.INTEGER);
        else ps.setInt(idx, valor);
    }

    private Jugador mapear(ResultSet rs) throws SQLException {
        Jugador j = new Jugador();
        j.setIdJugador(rs.getInt("id_jugador"));
        j.setNombreCompleto(rs.getString("nombre_completo"));
        int idEquipo = rs.getInt("id_equipo");
        j.setIdEquipo(rs.wasNull() ? null : idEquipo);
        int idJuego = rs.getInt("id_juego");
        j.setIdJuego(rs.wasNull() ? null : idJuego);
        j.setCategoria(rs.getString("categoria"));
        j.setRama(rs.getString("rama"));
        int edad = rs.getInt("edad");
        j.setEdad(rs.wasNull() ? null : edad);
        j.setNombreEquipo(rs.getString("nombre_equipo"));
        j.setNombreDisciplina(rs.getString("nombre_disciplina"));
        return j;
    }
}
