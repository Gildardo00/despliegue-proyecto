package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.ConfiguracionDAO;
import mx.uaa.arena.dao.PalabraProhibidaDAO;
import java.io.IOException;

@WebServlet("/configuracion")
public class ConfiguracionServlet extends HttpServlet {

    private final ConfiguracionDAO configDAO = new ConfiguracionDAO();
    private final PalabraProhibidaDAO palabraDAO = new PalabraProhibidaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("abiertas", configDAO.inscripcionesAbiertas());
        req.setAttribute("fechaCierre", configDAO.fechaCierre());
        req.setAttribute("palabras", palabraDAO.listar());
        req.getRequestDispatcher("configuracion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        if ("inscripciones".equals(accion)) {
            boolean abiertas = "true".equals(req.getParameter("abiertas"));
            configDAO.setInscripcionesAbiertas(abiertas);
            configDAO.setFechaCierre(req.getParameter("fechaCierre"));
        } else if ("palabra-add".equals(accion)) {
            String palabra = req.getParameter("palabra");
            if (palabra != null && !palabra.trim().isEmpty()) {
                palabraDAO.agregar(palabra);
            }
        } else if ("palabra-del".equals(accion)) {
            String palabra = req.getParameter("palabra");
            if (palabra != null) {
                palabraDAO.eliminar(palabra);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/configuracion");
    }
}
