package mx.imc.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Representa un registro histórico de cálculo de IMC.
 */
public class Medicion implements Serializable {

    private int idMedicion;
    private int idUsuario;
    private double peso;
    private double estatura;
    private double imc;
    private String categoria;
    private Timestamp fecha;

    public int getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(int idMedicion) {
        this.idMedicion = idMedicion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
