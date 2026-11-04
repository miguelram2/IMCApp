package mx.imc.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Bloquea el acceso a /calcularIMC y a la API REST de historial
 * si el usuario no ha iniciado sesión.
 */
@WebFilter(urlPatterns = {"/calcularIMC", "/api/*"})
public class SesionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        boolean autenticado = session != null && session.getAttribute("usuario") != null;

        if (autenticado) {
            chain.doFilter(req, res);
        } else if (request.getRequestURI().contains("/api/")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Debe iniciar sesión");
        } else {
            request.getSession().setAttribute("error", "Debes iniciar sesión para calcular tu IMC.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
