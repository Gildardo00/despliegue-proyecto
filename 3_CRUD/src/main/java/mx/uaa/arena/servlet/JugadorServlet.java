package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.EquipoDAO;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.dao.JugadorDAO;
import mx.uaa.arena.model.Jugador;
import java.io.IOException;

@WebServlet("/jugadores")
public class JugadorServlet extends HttpServlet {

    private final JugadorDAO dao = new JugadorDAO();
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final JuegoDisciplinaDAO juegoDAO = new JuegoDisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                req.setAttribute("equipos", equipoDAO.listar());
                req.setAttribute("disciplinas", juegoDAO.listar());
                req.getRequestDispatcher("jugadores/form.jsp").forward(req, resp);
                break;

            case "editar":
                req.setAttribute("jugador", dao.obtenerPorId(parseId(req)));
                req.setAttribute("equipos", equipoDAO.listar());
                req.setAttribute("disciplinas", juegoDAO.listar());
                req.getRequestDispatcher("jugadores/form.jsp").forward(req, resp);
                break;

            case "eliminar":
                boolean ok = dao.eliminar(parseId(req));
                resp.sendRedirect(req.getContextPath() + "/jugadores" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("jugadores/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombreCompleto");
        int idJuego = entero(req.getParameter("idJuego"), 0);
        int idEquipo = entero(req.getParameter("idEquipo"), 0);

        Jugador j = new Jugador();
        j.setNombreCompleto(nombre == null ? "" : nombre.trim());
        j.setIdJuego(idJuego > 0 ? idJuego : null);
        j.setIdEquipo(idEquipo > 0 ? idEquipo : null);
        j.setCategoria(textoODefecto(req.getParameter("categoria"), "Libre"));
        j.setRama(textoODefecto(req.getParameter("rama"), "Mixto"));
        Integer edad = enteroNullable(req.getParameter("edad"));
        j.setEdad(edad);

        String idTxt = req.getParameter("idJugador");
        boolean edicion = (idTxt != null && !idTxt.trim().isEmpty());

        if (j.getNombreCompleto().isEmpty() || idJuego == 0) {
            req.setAttribute("error", "El nombre y la disciplina son obligatorios.");
            if (edicion) { j.setIdJugador(Integer.parseInt(idTxt)); req.setAttribute("jugador", j); }
            req.setAttribute("equipos", equipoDAO.listar());
            req.setAttribute("disciplinas", juegoDAO.listar());
            req.getRequestDispatcher("jugadores/form.jsp").forward(req, resp);
            return;
        }

        if (!edicion) {
            dao.insertar(j);
        } else {
            j.setIdJugador(Integer.parseInt(idTxt));
            dao.actualizar(j);
        }
        resp.sendRedirect(req.getContextPath() + "/jugadores");
    }

    private int parseId(HttpServletRequest req) { return Integer.parseInt(req.getParameter("id")); }

    private int entero(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }

    private Integer enteroNullable(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return null; }
    }

    private String textoODefecto(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s.trim();
    }
}
