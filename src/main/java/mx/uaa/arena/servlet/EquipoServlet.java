package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.EquipoDAO;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.model.Equipo;
import mx.uaa.arena.util.FiltroPalabras;
import java.io.IOException;

@WebServlet("/equipos")
public class EquipoServlet extends HttpServlet {

    private final EquipoDAO dao = new EquipoDAO();
    private final JuegoDisciplinaDAO juegoDAO = new JuegoDisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                req.setAttribute("disciplinas", juegoDAO.listar());
                req.getRequestDispatcher("equipos/form.jsp").forward(req, resp);
                break;

            case "editar":
                req.setAttribute("equipo", dao.obtenerPorId(parseId(req)));
                req.setAttribute("disciplinas", juegoDAO.listar());
                req.getRequestDispatcher("equipos/form.jsp").forward(req, resp);
                break;

            case "eliminar":
                boolean ok = dao.eliminar(parseId(req));
                resp.sendRedirect(req.getContextPath() + "/equipos" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("equipos/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombreEquipo");
        String idTxt = req.getParameter("idEquipo");
        int idEquipo = (idTxt == null || idTxt.trim().isEmpty()) ? 0 : Integer.parseInt(idTxt);

        Equipo e = new Equipo();
        e.setIdEquipo(idEquipo);
        e.setNombreEquipo(nombre == null ? "" : nombre.trim());
        e.setIdJuego(entero(req.getParameter("idJuego"), 0));
        e.setCategoria(textoODefecto(req.getParameter("categoria"), "Libre"));
        e.setRama(textoODefecto(req.getParameter("rama"), "Mixto"));
        e.setSeed(entero(req.getParameter("seed"), 0));

        String error = validar(e);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("equipo", idEquipo > 0 ? e : null);
            req.setAttribute("disciplinas", juegoDAO.listar());
            req.getRequestDispatcher("equipos/form.jsp").forward(req, resp);
            return;
        }

        if (idEquipo == 0) {
            dao.insertar(e);
        } else {
            dao.actualizar(e);
        }
        resp.sendRedirect(req.getContextPath() + "/equipos");
    }

    private String validar(Equipo e) {
        if (e.getNombreEquipo().isEmpty()) {
            return "El nombre del equipo es obligatorio.";
        }
        String ofensiva = FiltroPalabras.palabraDetectada(e.getNombreEquipo());
        if (ofensiva != null) {
            return "El nombre del equipo contiene lenguaje no permitido (\"" + ofensiva + "\").";
        }
        if (dao.existeNombre(e.getNombreEquipo(), e.getIdEquipo())) {
            return "Ya existe un equipo con ese nombre.";
        }
        return null;
    }

    private int parseId(HttpServletRequest req) { return Integer.parseInt(req.getParameter("id")); }

    private int entero(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }

    private String textoODefecto(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s.trim();
    }
}
