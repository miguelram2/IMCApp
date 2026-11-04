<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión - IMC App</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<div class="tarjeta">
    <h2>Iniciar sesión</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p class="mensaje-error"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (request.getAttribute("mensajeExito") != null) { %>
        <p class="mensaje-exito"><%= request.getAttribute("mensajeExito") %></p>
    <% } %>
    <% if (session.getAttribute("error") != null) { %>
        <p class="mensaje-error"><%= session.getAttribute("error") %></p>
        <% session.removeAttribute("error"); %>
    <% } %>

    <form action="login" method="post">
        <label>Usuario</label>
        <input type="text" name="nombreUsuario" required autofocus>

        <label>Contraseña</label>
        <input type="password" name="contrasena" required>

        <button type="submit">Entrar</button>
    </form>

    <p class="enlace"><a href="registro">¿No tienes cuenta? Regístrate</a></p>
</div>
</body>
</html>
