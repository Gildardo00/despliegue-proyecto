package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Enfrentamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EnfrentamientoDAO {

    private static final String BASE =
        "SELECT en.*, ea.nombre_equipo AS nombre_a, eb.nombre_equipo AS nombre_b "
      + "FROM enfrentamiento en "
      + "LEFT JOIN equipo ea ON en.id_equipo_a = ea.id_equipo "
      + "LEFT JOIN equipo eb ON en.id_equipo_b = eb.id_equipo ";

    public List<Enfrentamiento> listarPorGrupo(int idJuego, String categoria, String rama) {
        List<Enfrentamiento> lista = new ArrayList<>();
        String sql = BASE + "WHERE en.id_juego = ? AND en.categoria = ? AND en.rama = ? ORDER BY en.orden, en.id_enfrentamiento";
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

    public boolean eliminarPorGrupo(int idJuego, String categoria, String rama) {
        String sql = "DELETE FROM enfrentamiento WHERE id_juego = ? AND categoria = ? AND rama = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            ps.setString(2, categoria);
            ps.setString(3, rama);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean insertar(Enfrentamiento en) {
        String sql = "INSERT INTO enfrentamiento "
                   + "(id_juego, categoria, rama, formato, fase, orden, id_equipo_a, id_equipo_b, seed_a, seed_b, es_bye) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, en.getIdJuego());
            ps.setString(2, en.getCategoria());
            ps.setString(3, en.getRama());
            ps.setString(4, en.getFormato());
            ps.setString(5, en.getFase());
            ps.setInt(6, en.getOrden());
            if (en.getIdEquipoA() == null) ps.setNull(7, Types.INTEGER); else ps.setInt(7, en.getIdEquipoA());
            if (en.getIdEquipoB() == null) ps.setNull(8, Types.INTEGER); else ps.setInt(8, en.getIdEquipoB());
            ps.setInt(9, en.getSeedA());
            ps.setInt(10, en.getSeedB());
            ps.setInt(11, en.isEsBye() ? 1 : 0);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Enfrentamiento mapear(ResultSet rs) throws SQLException {
        Enfrentamiento en = new Enfrentamiento();
        en.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
        en.setIdJuego(rs.getInt("id_juego"));
        en.setCategoria(rs.getString("categoria"));
        en.setRama(rs.getString("rama"));
        en.setFormato(rs.getString("formato"));
        en.setFase(rs.getString("fase"));
        en.setOrden(rs.getInt("orden"));
        int a = rs.getInt("id_equipo_a");
        en.setIdEquipoA(rs.wasNull() ? null : a);
        int b = rs.getInt("id_equipo_b");
        en.setIdEquipoB(rs.wasNull() ? null : b);
        en.setSeedA(rs.getInt("seed_a"));
        en.setSeedB(rs.getInt("seed_b"));
        en.setEsBye(rs.getInt("es_bye") == 1);
        en.setNombreEquipoA(rs.getString("nombre_a"));
        en.setNombreEquipoB(rs.getString("nombre_b"));
        return en;
    }
}
