package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.JornadaDAO;
import mx.uaa.arena.dao.RondaDAO;
import mx.uaa.arena.model.Jornada;
import java.io.IOException;

@WebServlet("/jornadas")
public class JornadaServlet extends HttpServlet {
    private final JornadaDAO dao      = new JornadaDAO();
    private final RondaDAO   rondaDAO = new RondaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":

                req.setAttribute("rondas", rondaDAO.listar());
                req.getRequestDispatcher("jornadas/form.jsp").forward(req, resp);
                break;

            case "editar":

                req.setAttribute("jornada", dao.obtenerPorId(parseId(req)));
                req.setAttribute("rondas", rondaDAO.listar());
                req.getRequestDispatcher("jornadas/form.jsp").forward(req, resp);
                break;

            case "eliminar":

                boolean ok = dao.eliminar(parseId(req));
                resp.sendRedirect(req.getContextPath() + "/jornadas" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("jornadas/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        Jornada j = new Jornada();
        j.setNumeroJornada(entero(req.getParameter("numeroJornada"), 1));
        j.setFechaInicio(req.getParameter("fechaInicio"));
        j.setFechaFin(req.getParameter("fechaFin"));
        j.setIdRonda(entero(req.getParameter("idRonda"), 0));

        String id = req.getParameter("idJornada");
        if (id == null || id.trim().isEmpty()) {
            dao.insertar(j);
        } else {
            j.setIdJornada(Integer.parseInt(id));
            dao.actualizar(j);
        }
        resp.sendRedirect(req.getContextPath() + "/jornadas");
    }

    private int parseId(HttpServletRequest req) { return Integer.parseInt(req.getParameter("id")); }

    private int entero(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
}
