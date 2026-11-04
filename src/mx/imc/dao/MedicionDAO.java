package mx.imc.dao;

import mx.imc.model.Medicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la tabla mediciones_imc.
 */
public class MedicionDAO {

    public void guardar(Medicion m) throws SQLException {
        String sql = "INSERT INTO mediciones_imc "
                + "(id_usuario, peso, estatura, imc, categoria, fecha_medicion) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, m.getIdUsuario());
            ps.setDouble(2, m.getPeso());
            ps.setDouble(3, m.getEstatura());
            ps.setDouble(4, m.getImc());
            ps.setString(5, m.getCategoria());
            ps.executeUpdate();
        }
    }

    public List<Medicion> obtenerHistorial(int idUsuario) throws SQLException {
        List<Medicion> lista = new ArrayList<>();
        String sql = "SELECT * FROM mediciones_imc WHERE id_usuario = ? "
                + "ORDER BY fecha_medicion DESC";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicion m = new Medicion();
                    m.setIdMedicion(rs.getInt("id_medicion"));
                    m.setIdUsuario(rs.getInt("id_usuario"));
                    m.setPeso(rs.getDouble("peso"));
                    m.setEstatura(rs.getDouble("estatura"));
                    m.setImc(rs.getDouble("imc"));
                    m.setCategoria(rs.getString("categoria"));
                    m.setFecha(rs.getTimestamp("fecha_medicion"));
                    lista.add(m);
                }
            }
        }
        return lista;
    }
}
