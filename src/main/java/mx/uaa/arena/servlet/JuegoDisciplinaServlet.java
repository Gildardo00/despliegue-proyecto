package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.model.JuegoDisciplina;
import java.io.IOException;

@WebServlet("/juegos")
public class JuegoDisciplinaServlet extends HttpServlet {

    private final JuegoDisciplinaDAO dao = new JuegoDisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                req.getRequestDispatcher("juegos/form.jsp").forward(req, resp);
                break;

            case "editar":
                req.setAttribute("juego", dao.obtenerPorId(parseId(req)));
                req.getRequestDispatcher("juegos/form.jsp").forward(req, resp);
                break;

            case "eliminar":
                boolean ok = dao.eliminar(parseId(req));
                resp.sendRedirect(req.getContextPath() + "/juegos" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("juegos/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombreDisciplina");
        String regla = req.getParameter("reglaPuntuacion");

        if (vacio(nombre) || vacio(regla)) {
            req.setAttribute("error", "El nombre y la regla de puntuacion son obligatorios.");
            req.getRequestDispatcher("juegos/form.jsp").forward(req, resp);
            return;
        }

        JuegoDisciplina jd = new JuegoDisciplina();
        jd.setNombreDisciplina(nombre.trim());
        jd.setReglaPuntuacion(regla.trim());
        jd.setTipoCompetencia(textoODefecto(req.getParameter("tipoCompetencia"), "Eliminacion directa"));
        jd.setModalidad(textoODefecto(req.getParameter("modalidad"), "Equipos"));
        jd.setActiva(req.getParameter("activa") != null);

        String id = req.getParameter("idJuego");
        if (id == null || id.trim().isEmpty()) {
            dao.insertar(jd);
        } else {
            jd.setIdJuego(Integer.parseInt(id));
            dao.actualizar(jd);
        }
        resp.sendRedirect(req.getContextPath() + "/juegos");
    }

    private int parseId(HttpServletRequest req) { return Integer.parseInt(req.getParameter("id")); }

    private boolean vacio(String s) { return s == null || s.trim().isEmpty(); }

    private String textoODefecto(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s.trim();
    }
}
