package mx.uaa.arena.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.uaa.arena.config.Conexion;
import mx.uaa.arena.dao.UsuarioDAO;
import mx.uaa.arena.model.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String usuario   = req.getParameter("usuario");
        String contrasena = req.getParameter("contrasena");

        try (Connection c = Conexion.getConexion();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM usuario")) {
        } catch (Exception ex) {
            req.setAttribute("error", "Problema con la base de datos: " + ex.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        Usuario u = usuarioDAO.validar(usuario, contrasena);
        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", u);

            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Usuario o contrasena incorrectos.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
