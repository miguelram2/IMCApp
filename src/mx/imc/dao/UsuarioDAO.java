package mx.imc.dao;

import mx.imc.model.Usuario;
import mx.imc.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Acceso a datos para la tabla usuarios.
 */
public class UsuarioDAO {

    public boolean existeUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void registrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios "
                + "(nombre_completo, nombre_usuario, contrasena, edad, sexo, estatura_actual) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getNombreUsuario());
            ps.setString(3, PasswordUtil.hash(u.getContrasena()));
            ps.setInt(4, u.getEdad());
            ps.setString(5, u.getSexo());
            ps.setDouble(6, u.getEstatura());
            ps.executeUpdate();
        }
    }

    public Usuario autenticar(String nombreUsuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("contrasena");
                    if (hashGuardado.equals(PasswordUtil.hash(contrasena))) {
                        return mapear(rs);
                    }
                }
            }
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setNombreUsuario(rs.getString("nombre_usuario"));
        u.setEdad(rs.getInt("edad"));
        u.setSexo(rs.getString("sexo"));
        u.setEstatura(rs.getDouble("estatura_actual"));
        return u;
    }
}
