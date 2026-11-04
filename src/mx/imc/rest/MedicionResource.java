package mx.imc.rest;

import mx.imc.dao.MedicionDAO;
import mx.imc.model.Medicion;

import java.sql.SQLException;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Servicio web tipo REST que expone el historial de mediciones de IMC
 * de un usuario en formato JSON. Es consumido desde calcularIMC.jsp
 * mediante JavaScript (fetch).
 */
@Path("/historial")
public class MedicionResource {

    private final MedicionDAO medicionDAO = new MedicionDAO();

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHistorial(@PathParam("idUsuario") int idUsuario) {

        // Protección extra: solo el usuario autenticado puede pedir su historial
        HttpSession session = request.getSession(false);
        Object usuarioSesion = session == null ? null : session.getAttribute("usuario");
        if (usuarioSesion == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"No autenticado\"}")
                    .build();
        }

        try {
            List<Medicion> historial = medicionDAO.obtenerHistorial(idUsuario);
            JsonArrayBuilder arreglo = Json.createArrayBuilder();

            for (Medicion m : historial) {
                arreglo.add(Json.createObjectBuilder()
                        .add("fecha", m.getFecha().toString())
                        .add("peso", m.getPeso())
                        .add("imc", Math.round(m.getImc() * 10.0) / 10.0)
                        .add("categoria", m.getCategoria()));
            }
            return Response.ok(arreglo.build().toString()).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
