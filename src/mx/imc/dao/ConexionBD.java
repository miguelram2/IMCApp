package mx.imc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para obtener conexiones JDBC hacia MySQL.
 *
 * IMPORTANTE: ajusta URL, USUARIO y PASSWORD a los datos de tu instalación
 * de MySQL antes de ejecutar la aplicación. Recuerda también agregar el
 * archivo .jar del conector de MySQL a las Librerías del proyecto en NetBeans.
 */
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/imc_bd?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Moga1810";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver JDBC de MySQL. "
                    + "Agrega mysql-connector-j al proyecto.", e);
        }
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
