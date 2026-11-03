package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.JugadorDAO;
import mx.uaa.arena.dao.ParticiparDAO;
import mx.uaa.arena.dao.PartidoDAO;
import mx.uaa.arena.model.Participar;
import java.io.IOException;

@WebServlet("/participaciones")
public class ParticipacionServlet extends HttpServlet {
    private final ParticiparDAO dao        = new ParticiparDAO();
    private final JugadorDAO    jugadorDAO = new JugadorDAO();
    private final PartidoDAO    partidoDAO = new PartidoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":

                cargarCombos(req);
                req.getRequestDispatcher("participaciones/form.jsp").forward(req, resp);
                break;

            case "editar":

                req.setAttribute("participacion",
                        dao.obtener(entero(req.getParameter("idJugador"), 0),
                                    entero(req.getParameter("idPartido"),  0)));
                cargarCombos(req);
                req.getRequestDispatcher("participaciones/form.jsp").forward(req, resp);
                break;

            case "eliminar":

                boolean ok = dao.eliminar(entero(req.getParameter("idJugador"), 0),
                                          entero(req.getParameter("idPartido"),  0));
                resp.sendRedirect(req.getContextPath() + "/participaciones" + (ok ? "" : "?error=rel"));
                break;

            default:
                req.setAttribute("lista", dao.listar());
                req.getRequestDispatcher("participaciones/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        Participar pa = new Participar();
        pa.setIdJugador(entero(req.getParameter("idJugador"), 0));
        pa.setIdPartido(entero(req.getParameter("idPartido"),  0));
        pa.setNumeroP(entero(req.getParameter("numeroP"), 0));
        pa.setGolesIndividuales(entero(req.getParameter("golesIndividuales"), 0));

        pa.setPartidaGanada(req.getParameter("partidaGanada") != null);

        if ("1".equals(req.getParameter("editar"))) {
            dao.actualizar(pa);
        } else {
            dao.insertar(pa);
        }
        resp.sendRedirect(req.getContextPath() + "/participaciones");
    }

    private void cargarCombos(HttpServletRequest req) {
        req.setAttribute("jugadores", jugadorDAO.listar());
        req.setAttribute("partidos",  partidoDAO.listar());
    }

    private int entero(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
}
