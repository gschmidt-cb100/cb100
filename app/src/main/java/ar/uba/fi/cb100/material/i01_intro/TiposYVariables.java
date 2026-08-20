package ar.uba.fi.cb100.material.i01_intro;

/**
 * Tipos de datos y variables en Java.
 * <p>
 * Java es fuertemente tipado: cada variable tiene un tipo fijo. Hay dos
 * familias: <b>primitivos</b> (guardan el valor) y <b>referencias</b>
 * (apuntan a un objeto).
 */
public class TiposYVariables {

    public static void main(String[] args) {
        // --- Tipos primitivos ---
        int entero = 42;                 // enteros de 32 bits
        long grande = 9_000_000_000L;    // 64 bits; el guion bajo es cosmético
        double real = 3.14159;           // punto flotante de 64 bits
        boolean verdadero = true;        // true / false
        char letra = 'A';                // un carácter Unicode

        // --- Inferencia de tipo con var (el tipo sigue siendo fijo) ---
        var mensaje = "Hola";            // el compilador infiere String
        var pi = 3.14;                   // ... infiere double

        // --- Casting (conversión de tipos) ---
        double promedio = 7.8;
        int truncado = (int) promedio;   // 7  (se pierde la parte decimal)
        double aReal = entero;           // 42.0 (widening automático)

        System.out.println("entero = " + entero + ", grande = " + grande);
        System.out.println("real = " + real + ", verdadero = " + verdadero);
        System.out.println("letra = " + letra + ", mensaje = " + mensaje);
        System.out.println("pi = " + pi + ", truncado = " + truncado
                + ", aReal = " + aReal);
    }
}
