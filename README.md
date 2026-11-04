[README.md](https://github.com/user-attachments/files/34351427/README.md)
# IMCApp – Calculadora de Índice de Masa Corporal

Aplicación web en Java EE que permite a cada usuario registrarse, iniciar sesión, calcular su índice de masa corporal (IMC) y consultar su historial de mediciones. El historial se obtiene de un servicio web REST. Está construida bajo el patrón Model-View-Controller.

## Tecnologías

- Java EE 8 (Servlets, JSP, filtros)
- JAX-RS (Jersey) y JSON-P para el servicio REST
- MySQL como base de datos
- GlassFish 5.1 como servidor de aplicaciones
- Apache NetBeans (proyecto Ant)

---

## Instalación y configuración

### Requisitos
- JDK 8 (es el que acepta GlassFish 5.1)
- GlassFish Server 5.1
- MySQL Server y su driver JDBC (MySQL Connector/J) agregado a las librerías del proyecto
- Apache NetBeans
- Git

### 1. Clonar el repositorio
```cmd
git clone https://github.com/miguelram2/IMCApp.git
cd IMCApp
```

### 2. Crear la base de datos
```cmd
mysql -u root -p -e "CREATE DATABASE imc_bd CHARACTER SET utf8mb4;"
mysql -u root -p imc_bd < db\esquema.sql
```

### 3. Configurar la conexión
El archivo con las credenciales no se sube al repositorio. En la carpeta `src/mx/imc/dao/`:

1. Copia `ConexionBD.java.example` y nómbralo `ConexionBD.java`.
2. Abre el archivo y reemplaza `TU_CONTRASENA` por la contraseña de tu usuario de MySQL.

### 4. Abrir y ejecutar
1. En NetBeans: **File → Open Project** y selecciona la carpeta `IMCApp`.
2. Verifica que GlassFish use el JDK 8.
3. Clic derecho en el proyecto → **Clean and Build** y luego **Run**.

---

## Uso del programa

1. Abre `http://localhost:8080/IMCApp/`.
2. **Crear cuenta:** captura nombre completo, nombre de usuario, contraseña, edad, sexo y estatura. Se rechazan edades menores de 15 años y estaturas fuera del rango de 1.0 a 2.5 m.
3. **Iniciar sesión** con el usuario y la contraseña registrados. Sin sesión no es posible calcular el IMC.
4. **Calcular IMC:** captura tu peso en kilogramos. No se aceptan valores de cero ni negativos.
5. Se muestra tu IMC actual con su categoría y una tabla con el historial de mediciones.
6. **Cerrar sesión** desde el enlace de la tarjeta.

### Servicio REST
```
GET /IMCApp/api/historial/{idUsuario}
```
Devuelve el historial de mediciones en JSON. Requiere sesión iniciada y solo permite consultar el historial del propio usuario.

---

## Estructura del proyecto

| Paquete | Contenido |
|---|---|
| `mx.imc.model` | Entidades `Usuario` y `Medicion` |
| `mx.imc.dao` | Acceso a datos: `UsuarioDAO`, `MedicionDAO`, `ConexionBD` |
| `mx.imc.util` | `CalculadoraIMC` y `PasswordUtil` |
| `mx.imc.controller` | Servlets de registro, login, logout y cálculo, más `SesionFilter` |
| `mx.imc.rest` | Servicio REST: `ApplicationConfig` y `MedicionResource` |
| `web/` | Vistas JSP y hoja de estilos |

---

## Créditos

Desarrollado por **Miguel Ángel Ramírez Montiel** como evidencia final del curso Computación Avanzada en Java, Universidad Tecmilenio.

## Licencia

MIT License. Copyright (c) 2026 Miguel Ángel Ramírez Montiel.

Se concede permiso, sin cargo, para usar, copiar, modificar y distribuir este software, siempre que se incluya este aviso de copyright. El software se entrega «tal cual», sin garantía de ningún tipo.
