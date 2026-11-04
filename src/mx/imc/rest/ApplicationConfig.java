package mx.imc.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Activa JAX-RS (Jersey) en GlassFish bajo el path /api.
 * Con esto los recursos quedan disponibles en /IMCApp/api/...
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
}
