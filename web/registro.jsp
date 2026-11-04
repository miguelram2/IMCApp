<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear cuenta - IMC App</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<div class="tarjeta">
    <h2>Crear cuenta</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p class="mensaje-error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="registro" method="post">
        <label>Nombre completo</label>
        <input type="text" name="nombreCompleto" required>

        <label>Nombre de usuario</label>
        <input type="text" name="nombreUsuario" required>

        <label>Contraseña</label>
        <input type="password" name="contrasena" required>

        <label>Edad (mínimo 15 años)</label>
        <input type="number" name="edad" min="15" required>

        <label>Sexo</label>
        <select name="sexo">
            <option value="Femenino">Femenino</option>
            <option value="Masculino">Masculino</option>
        </select>

        <label>Estatura en metros (entre 1.0 y 2.5)</label>
        <input type="number" step="0.01" name="estatura" min="1.0" max="2.5" required>

        <button type="submit">Crear cuenta</button>
    </form>

    <p class="enlace"><a href="login">¿Ya tienes cuenta? Inicia sesión</a></p>
</div>
</body>
</html>
