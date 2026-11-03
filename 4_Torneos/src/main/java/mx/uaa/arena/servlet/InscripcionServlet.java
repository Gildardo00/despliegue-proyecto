package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.ConfiguracionDAO;
import mx.uaa.arena.dao.JuegoDisciplinaDAO;
import mx.uaa.arena.dao.JugadorDAO;
import mx.uaa.arena.model.Jugador;
import java.io.IOException;

@WebServlet("/inscripcion")
public class InscripcionServlet extends HttpServlet {

    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private final JuegoDisciplinaDAO juegoDAO = new JuegoDisciplinaDAO();
    private final ConfiguracionDAO configDAO = new ConfiguracionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("abiertas", configDAO.inscripcionesAbiertas());
        req.setAttribute("disciplinas", juegoDAO.listarActivas());
        req.getRequestDispatcher("inscripcion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if (!configDAO.inscripcionesAbiertas()) {
            req.setAttribute("error", "Las inscripciones estan cerradas en este momento.");
            mostrarFormulario(req, resp);
            return;
        }

        String nombre = req.getParameter("nombreCompleto");
        int idJuego = entero(req.getParameter("idJuego"), 0);
        String categoria = req.getParameter("categoria");
        String rama = req.getParameter("rama");
        Integer edad = enteroNullable(req.getParameter("edad"));

        StringBuilder faltantes = new StringBuilder();
        if (vacio(nombre))    faltantes.append("nombre completo, ");
        if (idJuego == 0)     faltantes.append("disciplina, ");
        if (vacio(categoria)) faltantes.append("categoria, ");
        if (vacio(rama))      faltantes.append("rama, ");

        if (faltantes.length() > 0) {
            String campos = faltantes.substring(0, faltantes.length() - 2);
            req.setAttribute("error", "Faltan datos obligatorios: " + campos + ".");
            mostrarFormulario(req, resp);
            return;
        }

        Jugador j = new Jugador();
        j.setNombreCompleto(nombre.trim());
        j.setIdJuego(idJuego);
        j.setIdEquipo(null);
        j.setCategoria(categoria.trim());
        j.setRama(rama.trim());
        j.setEdad(edad);

        if (jugadorDAO.insertar(j)) {
            req.setAttribute("exito", "Inscripcion enviada con exito. Bienvenido a la Arena!");
        } else {
            req.setAttribute("error", "No se pudo enviar la inscripcion. Intenta de nuevo.");
        }
        mostrarFormulario(req, resp);
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("abiertas", configDAO.inscripcionesAbiertas());
        req.setAttribute("disciplinas", juegoDAO.listarActivas());
        req.getRequestDispatcher("inscripcion.jsp").forward(req, resp);
    }

    private boolean vacio(String s) { return s == null || s.trim().isEmpty(); }

    private int entero(String s, int d) { try { return Integer.parseInt(s); } catch (Exception e) { return d; } }

    private Integer enteroNullable(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return null; }
    }
}
