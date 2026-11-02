package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Partido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {
    private static final String BASE =
        "SELECT p.id_partido, p.sede, p.id_juego, p.id_jornada, "
      + "       jd.nombre_disciplina, "
      + "       jo.numero_jornada, r.nombre_ronda, "
      + "       loc.id_equipo AS id_local, eql.nombre_equipo AS nombre_local, "
      + "       vis.id_equipo AS id_visit, eqv.nombre_equipo AS nombre_visitante, "
      + "       loc.goles_totales_local AS goles_local, "
      + "       loc.goles_totales_visitante AS goles_visitante "
      + "FROM partido p "
      + "JOIN juego_disciplina jd ON p.id_juego = jd.id_juego "
      + "JOIN jornada jo ON p.id_jornada = jo.id_jornada "
      + "JOIN ronda r ON jo.id_ronda = r.id_ronda "
      + "LEFT JOIN enfrentar loc ON loc.id_partido = p.id_partido AND loc.condicion = 'Local' "
      + "LEFT JOIN equipo eql ON loc.id_equipo = eql.id_equipo "
      + "LEFT JOIN enfrentar vis ON vis.id_partido = p.id_partido AND vis.condicion = 'Visitante' "
      + "LEFT JOIN equipo eqv ON vis.id_equipo = eqv.id_equipo ";

    public List<Partido> listar() {
        List<Partido> lista = new ArrayList<>();
        String sql = BASE + "ORDER BY jo.numero_jornada, p.id_partido";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Partido obtenerPorId(int id) {
        String sql = BASE + "WHERE p.id_partido = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public int insertar(Partido p) {
        String sql = "INSERT INTO partido (sede, id_juego, id_jornada) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getSede());
            ps.setInt(2, p.getIdJuego());
            ps.setInt(3, p.getIdJornada());
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public boolean actualizar(Partido p) {
        String sql = "UPDATE partido SET sede=?, id_juego=?, id_jornada=? WHERE id_partido=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getSede());
            ps.setInt(2, p.getIdJuego());
            ps.setInt(3, p.getIdJornada());
            ps.setInt(4, p.getIdPartido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM partido WHERE id_partido = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM partido";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Partido mapear(ResultSet rs) throws SQLException {
        Partido p = new Partido();
        p.setIdPartido(rs.getInt("id_partido"));
        p.setSede(rs.getString("sede"));

        p.setIdJuego(rs.getInt("id_juego"));
        p.setIdJornada(rs.getInt("id_jornada"));

        p.setNombreDisciplina(rs.getString("nombre_disciplina"));
        p.setNumeroJornada(rs.getInt("numero_jornada"));
        p.setNombreRonda(rs.getString("nombre_ronda"));

        p.setIdEquipoLocal(rs.getInt("id_local"));
        p.setNombreLocal(rs.getString("nombre_local"));

        p.setIdEquipoVisitante(rs.getInt("id_visit"));
        p.setNombreVisitante(rs.getString("nombre_visitante"));

        p.setGolesLocal(rs.getInt("goles_local"));
        p.setGolesVisitante(rs.getInt("goles_visitante"));
        return p;
    }
}
