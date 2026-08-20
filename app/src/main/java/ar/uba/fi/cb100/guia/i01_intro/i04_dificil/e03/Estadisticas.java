package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e03;

/**
 * Ejercicio 03 - Estadisticas.
 * Calcula minimo, maximo y promedio de un arreglo en una sola pasada.
 *
 * <p>Record inmutable que agrupa las estadisticas basicas de un arreglo.
 *
 * @param min      valor minimo
 * @param max      valor maximo
 * @param promedio promedio aritmetico
 */
public record Estadisticas(int min, int max, double promedio) {

    /**
     * Calcula min, max y promedio recorriendo el arreglo una sola vez.
     *
     * @param v arreglo no vacio
     * @return estadisticas calculadas
     */
    public static Estadisticas calcular(int[] v) {
        if (v == null || v.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser null ni vacio");
        }

        int min = v[0];
        int max = v[0];
        long suma = 0;

        // Unica pasada: actualizamos min, max y acumulamos la suma
        for (int valor : v) {
            if (valor < min) {
                min = valor;
            }
            if (valor > max) {
                max = valor;
            }
            suma += valor;
        }

        double promedio = (double) suma / v.length;
        return new Estadisticas(min, max, promedio);
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        int[] datos = {4, 8, 15, 16, 23, 42};
        Estadisticas e = calcular(datos);
        System.out.println("min=" + e.min() + " max=" + e.max() + " promedio=" + e.promedio());
    }
}
