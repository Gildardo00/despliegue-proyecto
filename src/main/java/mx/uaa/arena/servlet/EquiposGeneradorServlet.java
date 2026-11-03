package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.EquipoDAO;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.dao.JugadorDAO;
import mx.uaa.arena.model.Equipo;
import mx.uaa.arena.model.Jugador;
import mx.uaa.arena.util.FiltroPalabras;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@WebServlet("/generar-equipos")
public class EquiposGeneradorServlet extends HttpServlet {

    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final JuegoDisciplinaDAO juegoDAO = new JuegoDisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idJuego = entero(req.getParameter("idJuego"), 0);
        req.setAttribute("disciplinas", juegoDAO.listar());
        if (idJuego > 0) {
            req.setAttribute("idJuego", idJuego);
            req.setAttribute("disciplina", juegoDAO.obtenerPorId(idJuego));
            req.setAttribute("pool", jugadorDAO.listarPoolPorDisciplina(idJuego));
        }
        req.getRequestDispatcher("generar_equipos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int idJuego = entero(req.getParameter("idJuego"), 0);
        int numEquipos = entero(req.getParameter("numEquipos"), 0);
        String base = req.getParameter("nombreBase");
        String categoria = textoODefecto(req.getParameter("categoria"), "Libre");
        String rama = textoODefecto(req.getParameter("rama"), "Mixto");

        String mensaje = procesar(idJuego, numEquipos, base, categoria, rama);
        resp.sendRedirect(req.getContextPath() + "/generar-equipos?idJuego=" + idJuego + "&msg=" + enc(mensaje));
    }

    private String procesar(int idJuego, int numEquipos, String base, String categoria, String rama) {
        if (idJuego <= 0) return "error:Selecciona una disciplina.";
        if (base == null || base.trim().isEmpty()) return "error:Indica un nombre base para los equipos.";
        String ofensiva = FiltroPalabras.palabraDetectada(base);
        if (ofensiva != null) return "error:El nombre base contiene lenguaje no permitido (\"" + ofensiva + "\").";
        if (numEquipos < 2) return "error:El numero de equipos debe ser al menos 2.";

        List<Jugador> pool = jugadorDAO.listarPoolPorDisciplina(idJuego);
        if (pool.size() < numEquipos) {
            return "error:No hay suficientes participantes sin equipo (" + pool.size()
                    + ") para formar " + numEquipos + " equipos.";
        }

        Collections.shuffle(pool);

        int[] idsEquipos = new int[numEquipos];
        for (int i = 0; i < numEquipos; i++) {
            Equipo e = new Equipo();
            e.setNombreEquipo(base.trim() + " " + (i + 1));
            e.setIdJuego(idJuego);
            e.setCategoria(categoria);
            e.setRama(rama);
            e.setSeed(i + 1);
            idsEquipos[i] = equipoDAO.insertar(e);
        }

        int asignados = 0;
        for (int k = 0; k < pool.size(); k++) {
            int idEquipo = idsEquipos[k % numEquipos];
            if (idEquipo > 0 && jugadorDAO.asignarEquipo(pool.get(k).getIdJugador(), idEquipo)) {
                asignados++;
            }
        }
        return "ok:Se crearon " + numEquipos + " equipos y se asignaron " + asignados + " participantes de forma aleatoria.";
    }

    private int entero(String s, int d) { try { return Integer.parseInt(s); } catch (Exception e) { return d; } }

    private String textoODefecto(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s.trim();
    }

    private String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }
}
