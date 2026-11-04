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

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombreCompleto = req.getParameter("nombreCompleto");
        String nombreUsuario = req.getParameter("nombreUsuario");
        String contrasena = req.getParameter("contrasena");
        String sexo = req.getParameter("sexo");
        String error = null;

        try {
            int edad = Integer.parseInt(req.getParameter("edad"));
            double estatura = Double.parseDouble(req.getParameter("estatura"));

            // Reglas de negocio obligatorias
            if (edad < 15) {
                error = "Debes tener al menos 15 años para registrarte.";
            } else if (estatura < 1.0 || estatura > 2.5) {
                error = "La estatura debe estar entre 1.0 m y 2.5 m.";
            } else if (nombreCompleto == null || nombreCompleto.trim().isEmpty()
                    || nombreUsuario == null || nombreUsuario.trim().isEmpty()
                    || contrasena == null || contrasena.isEmpty()) {
                error = "Todos los campos son obligatorios.";
            } else if (usuarioDAO.existeUsuario(nombreUsuario)) {
                error = "Ese nombre de usuario ya está en uso.";
            }

            if (error == null) {
                Usuario u = new Usuario();
                u.setNombreCompleto(nombreCompleto);
                u.setNombreUsuario(nombreUsuario);
                u.setContrasena(contrasena);
                u.setEdad(edad);
                u.setSexo(sexo);
                u.setEstatura(estatura);
                usuarioDAO.registrar(u);

                req.setAttribute("mensajeExito", "Registro exitoso. Ahora inicia sesión.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
        } catch (NumberFormatException e) {
            error = "Edad y estatura deben ser valores numéricos.";
        } catch (SQLException e) {
            error = "Error al registrar el usuario: " + e.getMessage();
        }

        req.setAttribute("error", error);
        req.getRequestDispatcher("/registro.jsp").forward(req, resp);
    }
}
