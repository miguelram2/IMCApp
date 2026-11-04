package mx.imc.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidad simple para no guardar contraseñas en texto plano.
 * Para un proyecto en producción se recomienda usar BCrypt en lugar de SHA-256.
 */
public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error al generar el hash de la contraseña", e);
        }
    }
}
