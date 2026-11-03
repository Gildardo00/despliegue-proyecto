package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.JugadorDAO;
import java.io.IOException;

@WebServlet("/clasificacion")
public class ClasificacionServlet extends HttpServlet {

    private final JugadorDAO jugadorDAO = new JugadorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("jugadores", jugadorDAO.listarClasificados());
        req.getRequestDispatcher("clasificacion.jsp").forward(req, resp);
    }
}
