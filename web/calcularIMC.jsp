<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mx.imc.model.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calcular IMC</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<div class="tarjeta">
    <div class="encabezado">
        <h2>Hola, <%= usuario.getNombreCompleto() %></h2>
        <a href="logout" class="enlace">Cerrar sesión</a>
    </div>

    <% if (request.getAttribute("error") != null) { %>
        <p class="mensaje-error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="calcularIMC" method="post">
        <label>Peso (kg)</label>
        <input type="number" step="0.1" name="peso" min="0.01" required>
        <button type="submit">Calcular IMC</button>
    </form>

    <% if (request.getAttribute("imcResultado") != null) { %>
        <div class="resultado">
            <p>Tu IMC actual</p>
            <p class="imc-valor"><%= request.getAttribute("imcResultado") %></p>
            <p><%= request.getAttribute("categoriaResultado") %></p>
        </div>
    <% } %>

    <h3>Historial de mediciones</h3>
    <table id="tablaHistorial">
        <thead>
            <tr><th>Fecha</th><th>Peso</th><th>IMC</th><th>Categoría</th></tr>
        </thead>
        <tbody id="cuerpoHistorial">
            <tr><td colspan="4">Cargando...</td></tr>
        </tbody>
    </table>
</div>

<script>
    // El historial NO se llena desde el servlet: se consume desde el
    // servicio web REST (mx.imc.rest.MedicionResource) vía fetch().
    var idUsuario = <%= usuario.getIdUsuario() %>;

    fetch("api/historial/" + idUsuario)
        .then(function (respuesta) {
            if (!respuesta.ok) {
                throw new Error("No se pudo obtener el historial");
            }
            return respuesta.json();
        })
        .then(function (datos) {
            var cuerpo = document.getElementById("cuerpoHistorial");
            cuerpo.innerHTML = "";
            if (datos.length === 0) {
                cuerpo.innerHTML = "<tr><td colspan='4'>Aún no tienes mediciones</td></tr>";
                return;
            }
            datos.forEach(function (m) {
                var fila = document.createElement("tr");
                fila.innerHTML = "<td>" + m.fecha + "</td>"
                        + "<td>" + m.peso + " kg</td>"
                        + "<td>" + m.imc + "</td>"
                        + "<td>" + m.categoria + "</td>";
                cuerpo.appendChild(fila);
            });
        })
        .catch(function () {
            document.getElementById("cuerpoHistorial").innerHTML =
                "<tr><td colspan='4'>Error al cargar el historial</td></tr>";
        });
</script>
</body>
</html>
