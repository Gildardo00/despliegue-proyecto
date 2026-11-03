package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.EquipoDAO;
import mx.uaa.arena.dao.PartidoDAO;
import mx.uaa.arena.model.Equipo;
import mx.uaa.arena.model.Partido;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@WebServlet("/grafica")
public class GraficaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Equipo>  equipos  = new EquipoDAO().listar();
        List<Partido> partidos = new PartidoDAO().listar();

        LinkedHashMap<String, Integer> victorias = new LinkedHashMap<>();
        for (Equipo e : equipos) {
            victorias.put(e.getNombreEquipo(), 0);
        }

        for (Partido p : partidos) {
            if (p.getNombreLocal() == null || p.getNombreVisitante() == null) continue;

            String ganador = null;
            if (p.getGolesLocal() > p.getGolesVisitante()) {
                ganador = p.getNombreLocal();
            } else if (p.getGolesVisitante() > p.getGolesLocal()) {
                ganador = p.getNombreVisitante();
            }

            if (ganador != null && victorias.containsKey(ganador)) {
                victorias.put(ganador, victorias.get(ganador) + 1);
            }
        }

        int maximo = 1;
        for (int v : victorias.values()) {
            if (v > maximo) maximo = v;
        }

        req.setAttribute("victorias", victorias);
        req.setAttribute("maximo", maximo);
        req.getRequestDispatcher("grafica.jsp").forward(req, resp);
    }
}
