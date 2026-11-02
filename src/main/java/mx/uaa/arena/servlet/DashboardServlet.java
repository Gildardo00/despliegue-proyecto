package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("totalJuegos",    new JuegoDisciplinaDAO().contar());
        req.setAttribute("totalEquipos",   new EquipoDAO().contar());
        req.setAttribute("totalJugadores", new JugadorDAO().contar());
        req.setAttribute("totalRondas",    new RondaDAO().contar());
        req.setAttribute("totalJornadas",  new JornadaDAO().contar());
        req.setAttribute("totalPartidos",  new PartidoDAO().contar());

        req.setAttribute("rondas",   new RondaDAO().listar());

        req.setAttribute("partidos", new PartidoDAO().listar());

        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
