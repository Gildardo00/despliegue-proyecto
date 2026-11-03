package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.EnfrentamientoDAO;
import mx.uaa.arena.dao.EquipoDAO;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.model.Enfrentamiento;
import mx.uaa.arena.model.Equipo;
import mx.uaa.arena.model.JuegoDisciplina;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/torneos")
public class TorneoServlet extends HttpServlet {

    private static final int MINIMO_PARTICIPANTES = 4;

    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final JuegoDisciplinaDAO juegoDAO = new JuegoDisciplinaDAO();
    private final EnfrentamientoDAO enfrentamientoDAO = new EnfrentamientoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idJuego = entero(req.getParameter("idJuego"), 0);
        String categoria = req.getParameter("categoria");
        String rama = req.getParameter("rama");

        req.setAttribute("disciplinas", juegoDAO.listar());

        if (idJuego > 0 && noVacio(categoria) && noVacio(rama)) {
            JuegoDisciplina jd = juegoDAO.obtenerPorId(idJuego);
            req.setAttribute("disciplina", jd);
            req.setAttribute("idJuego", idJuego);
            req.setAttribute("categoria", categoria);
            req.setAttribute("rama", rama);
            req.setAttribute("equipos", equipoDAO.listarPorGrupo(idJuego, categoria, rama));
            req.setAttribute("enfrentamientos", enfrentamientoDAO.listarPorGrupo(idJuego, categoria, rama));
        }
        req.getRequestDispatcher("torneos/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int idJuego = entero(req.getParameter("idJuego"), 0);
        String categoria = req.getParameter("categoria");
        String rama = req.getParameter("rama");

        JuegoDisciplina jd = juegoDAO.obtenerPorId(idJuego);
        if (jd == null || !noVacio(categoria) || !noVacio(rama)) {
            resp.sendRedirect(req.getContextPath() + "/torneos");
            return;
        }

        List<Equipo> equipos = equipoDAO.listarPorGrupo(idJuego, categoria, rama);
        String mensaje;
        if (equipos.size() < MINIMO_PARTICIPANTES) {
            mensaje = "error:Se necesitan al menos " + MINIMO_PARTICIPANTES
                    + " equipos en el grupo para generar el torneo (hay " + equipos.size() + ").";
        } else {
            enfrentamientoDAO.eliminarPorGrupo(idJuego, categoria, rama);
            String formato = jd.getTipoCompetencia();
            if ("Round Robin".equalsIgnoreCase(formato)) {
                generarRoundRobin(idJuego, categoria, rama, equipos);
            } else {
                formato = "Eliminacion directa";
                generarEliminacion(idJuego, categoria, rama, equipos);
            }
            mensaje = "ok:Torneo (" + formato + ") generado con " + equipos.size() + " equipos.";
        }
        resp.sendRedirect(req.getContextPath() + "/torneos?idJuego=" + idJuego
                + "&categoria=" + enc(categoria) + "&rama=" + enc(rama) + "&msg=" + enc(mensaje));
    }

    private void generarEliminacion(int idJuego, String categoria, String rama, List<Equipo> equipos) {
        int n = equipos.size();
        int orden = 1;
        int inicio = 0;
        if (n % 2 != 0) {
            Equipo top = equipos.get(0);
            enfrentamientoDAO.insertar(crear(idJuego, categoria, rama, "Eliminacion directa",
                    "Ronda 1", orden++, top, null, true));
            inicio = 1;
        }
        int i = inicio;
        int j = n - 1;
        while (i < j) {
            Equipo a = equipos.get(i);
            Equipo b = equipos.get(j);
            enfrentamientoDAO.insertar(crear(idJuego, categoria, rama, "Eliminacion directa",
                    "Ronda 1", orden++, a, b, false));
            i++;
            j--;
        }
    }

    private void generarRoundRobin(int idJuego, String categoria, String rama, List<Equipo> equipos) {
        List<int[]> pares = new ArrayList<>();
        for (int a = 0; a < equipos.size(); a++) {
            for (int b = a + 1; b < equipos.size(); b++) {
                pares.add(new int[]{a, b});
            }
        }
        Collections.shuffle(pares);
        int orden = 1;
        for (int[] par : pares) {
            Equipo a = equipos.get(par[0]);
            Equipo b = equipos.get(par[1]);
            enfrentamientoDAO.insertar(crear(idJuego, categoria, rama, "Round Robin",
                    "Jornada " + orden, orden, a, b, false));
            orden++;
        }
    }

    private Enfrentamiento crear(int idJuego, String categoria, String rama, String formato,
                                 String fase, int orden, Equipo a, Equipo b, boolean bye) {
        Enfrentamiento en = new Enfrentamiento();
        en.setIdJuego(idJuego);
        en.setCategoria(categoria);
        en.setRama(rama);
        en.setFormato(formato);
        en.setFase(fase);
        en.setOrden(orden);
        en.setIdEquipoA(a != null ? a.getIdEquipo() : null);
        en.setIdEquipoB(b != null ? b.getIdEquipo() : null);
        en.setSeedA(a != null ? a.getSeed() : 0);
        en.setSeedB(b != null ? b.getSeed() : 0);
        en.setEsBye(bye);
        return en;
    }

    private boolean noVacio(String s) { return s != null && !s.trim().isEmpty(); }

    private int entero(String s, int d) { try { return Integer.parseInt(s); } catch (Exception e) { return d; } }

    private String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }
}
