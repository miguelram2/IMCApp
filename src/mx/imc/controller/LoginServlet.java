package mx.imc.controller;

import mx.imc.dao.UsuarioDAO;
import mx.imc.model.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombreUsuario = req.getParameter("nombreUsuario");
        String contrasena = req.getParameter("contrasena");

        try {
            Usuario u = usuarioDAO.autenticar(nombreUsuario, contrasena);
            if (u != null) {
                HttpSession session = req.getSession();
                session.setAttribute("usuario", u);
                resp.sendRedirect(req.getContextPath() + "/calcularIMC");
            } else {
                req.setAttribute("error", "Usuario o contraseña incorrectos.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
