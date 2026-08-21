package ar.uba.fi.cb100.material.i07_hashing;

/**
 * Las <b>funciones de hash</b> que se ven en la unidad, para experimentar.
 * <p>
 * Las que se usan en la práctica: <b>módulo</b> (división), <b>multiplicativo</b>
 * y <b>polinomial</b> para cadenas (el de {@code String.hashCode()} de Java).
 * Las históricas/didácticas: <b>extracción</b>, <b>cuadrado medio</b> y
 * <b>plegado</b> — importan para entender la idea, aunque hoy no se usen.
 */
public final class FuncionesDeHash {

    private FuncionesDeHash() {}

    /** Método de la división (módulo): rápido y simple; conviene capacidad prima. */
    public static int porModulo(int clave, int capacidad) {
        return Math.floorMod(clave, capacidad);
    }

    /** Método multiplicativo (Knuth): usa la parte fraccionaria de clave·A. */
    public static int multiplicativo(int clave, int capacidad) {
        final double A = 0.6180339887;               // (√5 − 1) / 2, sugerido por Knuth
        double fraccion = (clave * A) % 1.0;
        return (int) (capacidad * Math.abs(fraccion));
    }

    /** Hash polinomial de una cadena (regla de Horner, base 31): el de Java. */
    public static int polinomial(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);                // h = s[0]·31^(n-1) + … + s[n-1]
        }
        return h;
    }

    /** EXTRACCIÓN: tomar algunos dígitos de la clave (p. ej., 3.º y 4.º). */
    public static int extraccion(int clave) {
        String d = String.valueOf(Math.abs(clave));
        if (d.length() < 4) {
            return Math.abs(clave);
        }
        return Integer.parseInt(d.substring(2, 4));  // dígitos 3.º y 4.º
    }

    /** CUADRADO MEDIO: elevar al cuadrado y tomar los dígitos DEL MEDIO. */
    public static int cuadradoMedio(int clave, int cantidadDeDigitos) {
        long cuadrado = (long) clave * clave;
        String d = String.valueOf(cuadrado);
        int desde = Math.max(0, (d.length() - cantidadDeDigitos) / 2);
        int hasta = Math.min(d.length(), desde + cantidadDeDigitos);
        return Integer.parseInt(d.substring(desde, hasta));
    }

    /** PLEGADO: partir la clave en grupos de dígitos y sumarlos. */
    public static int plegado(int clave, int digitosPorGrupo) {
        String d = String.valueOf(Math.abs(clave));
        int suma = 0;
        for (int i = 0; i < d.length(); i += digitosPorGrupo) {
            int fin = Math.min(d.length(), i + digitosPorGrupo);
            suma += Integer.parseInt(d.substring(i, fin));
        }
        return suma;
    }

    public static void main(String[] args) {
        System.out.println("modulo(123456, 97)      = " + porModulo(123456, 97));
        System.out.println("multiplicativo(123456, 97) = " + multiplicativo(123456, 97));
        System.out.println("polinomial(\"ana\")       = " + polinomial("ana"));
        System.out.println("\"ana\".hashCode()        = " + "ana".hashCode());  // igual!
        System.out.println("extraccion(123456)      = " + extraccion(123456));      // 34
        System.out.println("cuadradoMedio(123, 2)   = " + cuadradoMedio(123, 2));
        System.out.println("plegado(123456, 2)      = " + plegado(123456, 2));      // 12+34+56
    }
}
