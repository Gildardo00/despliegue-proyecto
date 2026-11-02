package mx.uaa.arena.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ctx  = req.getContextPath();

        String path = req.getRequestURI().substring(ctx.length());

        boolean publica =
                path.equals("/")                ||
                path.equals("/index.jsp")       ||
                path.equals("/login")           ||
                path.equals("/login.jsp")       ||
                path.equals("/registro")        ||
                path.equals("/registro.jsp")    ||
                path.equals("/inscripcion")     ||
                path.equals("/inscripcion.jsp") ||
                path.startsWith("/css/")        ||
                path.startsWith("/img/");

        HttpSession session = req.getSession(false);

        boolean logueado = (session != null && session.getAttribute("usuario") != null);

        if (publica || logueado) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(ctx + "/login");
        }
    }
}
