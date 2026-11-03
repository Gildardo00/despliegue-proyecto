package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.RondaDAO;
import mx.uaa.arena.model.Ronda;
import java.io.IOException;

@WebServlet("/rondas")
public class RondaServlet extends HttpServlet {
    private final RondaDAO dao = new RondaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":

                req.getRequestDispatcher("rondas/form.jsp").forward(req, resp);
                break;

            case "editar":

                req.setAttribute("ronda", dao.obtenerPorId(parseId(req)));
                req.getRequestDispatcher("rondas/form.jsp").forward(req, resp);
                break;

            case "eliminar":

                boolean ok = dao.eliminar(parseId(req));
                resp.sendRedirect(req.getContextPath() + "/rondas" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("rondas/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        Ronda r = new Ronda();
        r.setNombreRonda(req.getParameter("nombreRonda"));
        r.setEstatus(req.getParameter("estatus"));

        String id = req.getParameter("idRonda");
        if (id == null || id.trim().isEmpty()) {
            dao.insertar(r);
        } else {
            r.setIdRonda(Integer.parseInt(id));
            dao.actualizar(r);
        }
        resp.sendRedirect(req.getContextPath() + "/rondas");
    }

    private int parseId(HttpServletRequest req) { return Integer.parseInt(req.getParameter("id")); }
}
