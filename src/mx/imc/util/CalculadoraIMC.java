package mx.imc.util;

/**
 * Lógica de negocio para calcular el Índice de Masa Corporal.
 */
public class CalculadoraIMC {

    private CalculadoraIMC() {
        // clase utilitaria, no se instancia
    }

    public static double calcular(double pesoKg, double estaturaM) {
        return pesoKg / (estaturaM * estaturaM);
    }

    public static String clasificar(double imc) {
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25.0) {
            return "Peso normal";
        } else if (imc < 30.0) {
            return "Sobrepeso";
        } else {
            return "Obesidad";
        }
    }
}
