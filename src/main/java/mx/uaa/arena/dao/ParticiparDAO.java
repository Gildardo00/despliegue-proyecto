package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Participar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticiparDAO {
    private static final String BASE =
        "SELECT pa.*, j.nombre_completo AS nombre_jugador, e.nombre_equipo AS nombre_equipo, "
      + "       CONCAT('Partido #', pt.id_partido, ' - Jornada ', jo.numero_jornada, ' (', pt.sede, ')') AS etiqueta_partido "
      + "FROM participar pa "
      + "JOIN jugador j  ON pa.id_jugador = j.id_jugador "
      + "JOIN equipo e   ON j.id_equipo = e.id_equipo "
      + "JOIN partido pt ON pa.id_partido = pt.id_partido "
      + "JOIN jornada jo ON pt.id_jornada = jo.id_jornada ";

    public List<Participar> listar() {
        List<Participar> lista = new ArrayList<>();
        String sql = BASE + "ORDER BY pa.id_partido, j.nombre_completo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Participar obtener(int idJugador, int idPartido) {
        String sql = BASE + "WHERE pa.id_jugador = ? AND pa.id_partido = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.setInt(2, idPartido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertar(Participar pa) {
        String sql = "INSERT INTO participar (id_jugador, id_partido, numero_p, goles_individuales, partida_ganada) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pa.getIdJugador());
            ps.setInt(2, pa.getIdPartido());
            ps.setInt(3, pa.getNumeroP());
            ps.setInt(4, pa.getGolesIndividuales());

            ps.setInt(5, pa.isPartidaGanada() ? 1 : 0);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Participar pa) {
        String sql = "UPDATE participar SET numero_p=?, goles_individuales=?, partida_ganada=? "
                   + "WHERE id_jugador=? AND id_partido=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pa.getNumeroP());
            ps.setInt(2, pa.getGolesIndividuales());
            ps.setInt(3, pa.isPartidaGanada() ? 1 : 0);

            ps.setInt(4, pa.getIdJugador());
            ps.setInt(5, pa.getIdPartido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int idJugador, int idPartido) {
        String sql = "DELETE FROM participar WHERE id_jugador = ? AND id_partido = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.setInt(2, idPartido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM participar";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Participar mapear(ResultSet rs) throws SQLException {
        Participar pa = new Participar();
        pa.setIdJugador(rs.getInt("id_jugador"));
        pa.setIdPartido(rs.getInt("id_partido"));
        pa.setNumeroP(rs.getInt("numero_p"));
        pa.setGolesIndividuales(rs.getInt("goles_individuales"));

        pa.setPartidaGanada(rs.getInt("partida_ganada") == 1);

        pa.setNombreJugador(rs.getString("nombre_jugador"));
        pa.setNombreEquipo(rs.getString("nombre_equipo"));
        pa.setEtiquetaPartido(rs.getString("etiqueta_partido"));
        return pa;
    }
}
