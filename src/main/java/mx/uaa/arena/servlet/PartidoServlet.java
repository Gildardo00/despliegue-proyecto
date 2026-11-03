package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.EnfrentarDAO;
import mx.uaa.arena.dao.EquipoDAO;
import mx.uaa.arena.dao.JornadaDAO;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.dao.PartidoDAO;
import mx.uaa.arena.model.Partido;
import java.io.IOException;

@WebServlet("/partidos")
public class PartidoServlet extends HttpServlet {
    private final PartidoDAO         dao         = new PartidoDAO();
    private final EnfrentarDAO       enfrentarDAO = new EnfrentarDAO();
    private final JuegoDisciplinaDAO juegoDAO    = new JuegoDisciplinaDAO();
    private final JornadaDAO         jornadaDAO  = new JornadaDAO();
    private final EquipoDAO          equipoDAO   = new EquipoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":

                cargarCombos(req);
                req.getRequestDispatcher("partidos/form.jsp").forward(req, resp);
                break;

            case "editar":

                req.setAttribute("partido", dao.obtenerPorId(parseId(req)));
                cargarCombos(req);
                req.getRequestDispatcher("partidos/form.jsp").forward(req, resp);
                break;

            case "eliminar":

                boolean ok = dao.eliminar(parseId(req));
                resp.sendRedirect(req.getContextPath() + "/partidos" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("partidos/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        Partido p = new Partido();
        p.setSede(req.getParameter("sede"));
        p.setIdJuego(entero(req.getParameter("idJuego"), 0));
        p.setIdJornada(entero(req.getParameter("idJornada"), 0));

        int idLocal        = entero(req.getParameter("idEquipoLocal"), 0);
        int idVisitante    = entero(req.getParameter("idEquipoVisitante"), 0);
        int golesLocal     = entero(req.getParameter("golesLocal"), 0);
        int golesVisitante = entero(req.getParameter("golesVisitante"), 0);

        String id = req.getParameter("idPartido");
        int idPartido;
        if (id == null || id.trim().isEmpty()) {
            idPartido = dao.insertar(p);
        } else {
            idPartido = Integer.parseInt(id);
            p.setIdPartido(idPartido);
            dao.actualizar(p);
        }

        if (idPartido > 0 && idLocal > 0 && idVisitante > 0) {
            enfrentarDAO.reemplazarParaPartido(idPartido, idLocal, idVisitante, golesLocal, golesVisitante);
        }

        resp.sendRedirect(req.getContextPath() + "/partidos");
    }

    private void cargarCombos(HttpServletRequest req) {
        req.setAttribute("juegos",   juegoDAO.listar());
        req.setAttribute("jornadas", jornadaDAO.listar());
        req.setAttribute("equipos",  equipoDAO.listar());
    }

    private int parseId(HttpServletRequest req) { return Integer.parseInt(req.getParameter("id")); }

    private int entero(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
}
