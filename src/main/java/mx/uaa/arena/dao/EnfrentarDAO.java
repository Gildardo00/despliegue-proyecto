package mx.uaa.arena.dao;

import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.model.Enfrentar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnfrentarDAO {
    public List<Enfrentar> listarPorPartido(int idPartido) {
        List<Enfrentar> lista = new ArrayList<>();

        String sql = "SELECT en.*, e.nombre_equipo AS nombre_equipo "
                   + "FROM enfrentar en JOIN equipo e ON en.id_equipo = e.id_equipo "
                   + "WHERE en.id_partido = ? "

                   + "ORDER BY (en.condicion = 'Local') DESC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean reemplazarParaPartido(int idPartido, int idLocal, int idVisitante,
                                         int golesLocal, int golesVisitante) {
        String del = "DELETE FROM enfrentar WHERE id_partido = ?";

        String ins = "INSERT INTO enfrentar (id_equipo, id_partido, condicion, "
                   + "goles_totales_local, goles_totales_visitante) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement pd = con.prepareStatement(del)) {
                    pd.setInt(1, idPartido);
                    pd.executeUpdate();
                }

                try (PreparedStatement pi = con.prepareStatement(ins)) {
                    pi.setInt(1, idLocal);
                    pi.setInt(2, idPartido);
                    pi.setString(3, "Local");
                    pi.setInt(4, golesLocal);
                    pi.setInt(5, golesVisitante);
                    pi.executeUpdate();

                    if (idVisitante != idLocal) {
                        pi.setInt(1, idVisitante);
                        pi.setInt(2, idPartido);
                        pi.setString(3, "Visitante");
                        pi.setInt(4, golesLocal);
                        pi.setInt(5, golesVisitante);
                        pi.executeUpdate();
                    }
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarPorPartido(int idPartido) {
        String sql = "DELETE FROM enfrentar WHERE id_partido = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Enfrentar mapear(ResultSet rs) throws SQLException {
        Enfrentar en = new Enfrentar();
        en.setIdEquipo(rs.getInt("id_equipo"));
        en.setIdPartido(rs.getInt("id_partido"));
        en.setCondicion(rs.getString("condicion"));
        en.setGolesTotalesLocal(rs.getInt("goles_totales_local"));
        en.setGolesTotalesVisitante(rs.getInt("goles_totales_visitante"));

        en.setNombreEquipo(rs.getString("nombre_equipo"));
        return en;
    }
}
