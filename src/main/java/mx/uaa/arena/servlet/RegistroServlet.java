package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaa.arena.dao.UsuarioDAO;
import mx.uaa.arena.model.Usuario;
import mx.uaa.arena.util.FiltroPalabras;
import java.io.IOException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nombre     = req.getParameter("nombre");
        String correo     = req.getParameter("correo");
        String usuario    = req.getParameter("usuario");
        String contrasena = req.getParameter("contrasena");

        if (vacio(nombre) || vacio(correo) || vacio(usuario) || vacio(contrasena)) {
            req.setAttribute("error", "Todos los campos son obligatorios.");
            req.getRequestDispatcher("registro.jsp").forward(req, resp);
            return;
        }

        if (contrasena.length() < 4) {
            req.setAttribute("error", "La contrasena debe tener al menos 4 caracteres.");
            req.getRequestDispatcher("registro.jsp").forward(req, resp);
            return;
        }

        String ofensiva = FiltroPalabras.palabraDetectada(usuario);
        if (ofensiva == null) ofensiva = FiltroPalabras.palabraDetectada(nombre);
        if (ofensiva != null) {
            req.setAttribute("error", "El nombre o usuario contiene lenguaje no permitido (\"" + ofensiva + "\").");
            req.getRequestDispatcher("registro.jsp").forward(req, resp);
            return;
        }

        if (usuarioDAO.existe(usuario, correo)) {
            req.setAttribute("error", "El usuario o el correo ya estan registrados.");
            req.getRequestDispatcher("registro.jsp").forward(req, resp);
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setUsuario(usuario);
        u.setContrasena(contrasena);
        u.setRol("ADMIN");

        if (usuarioDAO.registrar(u)) {
            req.setAttribute("exito", "Cuenta creada con exito. Ya puedes iniciar sesion.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "No se pudo crear la cuenta. Intenta de nuevo.");
            req.getRequestDispatcher("registro.jsp").forward(req, resp);
        }
    }

    private boolean vacio(String s) { return s == null || s.trim().isEmpty(); }
}
