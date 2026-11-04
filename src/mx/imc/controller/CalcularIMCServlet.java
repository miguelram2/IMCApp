package mx.imc.controller;

import mx.imc.dao.MedicionDAO;
import mx.imc.model.Medicion;
import mx.imc.model.Usuario;
import mx.imc.util.CalculadoraIMC;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calcularIMC")
public class CalcularIMCServlet extends HttpServlet {

    private final MedicionDAO medicionDAO = new MedicionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/calcularIMC.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // SesionFilter ya garantiza que exista una sesión activa con "usuario"
        HttpSession session = req.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String error = null;

        try {
            double peso = Double.parseDouble(req.getParameter("peso"));

            if (peso <= 0) {
                error = "El peso debe ser mayor a 0.";
            } else {
                double imc = CalculadoraIMC.calcular(peso, usuario.getEstatura());
                String categoria = CalculadoraIMC.clasificar(imc);

                Medicion m = new Medicion();
                m.setIdUsuario(usuario.getIdUsuario());
                m.setPeso(peso);
                m.setEstatura(usuario.getEstatura());
                m.setImc(imc);
                m.setCategoria(categoria);
                medicionDAO.guardar(m);

                req.setAttribute("imcResultado", String.format("%.1f", imc));
                req.setAttribute("categoriaResultado", categoria);
            }
        } catch (NumberFormatException e) {
            error = "Ingresa un peso válido.";
        } catch (SQLException e) {
            error = "Error al guardar la medición: " + e.getMessage();
        }

        req.setAttribute("error", error);
        req.getRequestDispatcher("/calcularIMC.jsp").forward(req, resp);
    }
}
