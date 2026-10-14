package ar.uba.fi.cb100.material.i06_estrategias;

/**
 * Los <b>tipos de recursividad</b>, uno por método (Unidad 6):
 * <ul>
 *   <li><b>Lineal</b>: UNA llamada recursiva por activación → {@code factorial}
 *       (está en {@link RecursividadBasica}).</li>
 *   <li><b>Múltiple</b>: DOS o más llamadas por activación → {@code caminosEnGrilla}.</li>
 *   <li><b>Indirecta</b> (o mutua): A llama a B y B llama a A → {@code esPar}/{@code esImpar}.</li>
 *   <li><b>Anidada</b>: el argumento de la llamada es OTRA llamada → {@code mcCarthy91}.</li>
 * </ul>
 */
public class TiposDeRecursividad {

    // ------------------------------------------------------------------
    // MÚLTIPLE: dos llamadas recursivas por activación.
    // ¿De cuántas formas se llega de la esquina (0,0) a la (filas-1, cols-1)
    // de una grilla moviéndose sólo hacia la derecha o hacia abajo?
    // Cada casilla se alcanza desde arriba O desde la izquierda:
    //     caminos(f, c) = caminos(f-1, c) + caminos(f, c-1)
    // ------------------------------------------------------------------
    public static long caminosEnGrilla(int filas, int columnas) {
        if (filas < 1 || columnas < 1) {
            throw new IllegalArgumentException("la grilla debe ser de al menos 1x1");
        }
        if (filas == 1 || columnas == 1) {
            return 1;   // caso base: en un pasillo hay un solo camino
        }
        return caminosEnGrilla(filas - 1, columnas)     // vengo desde arriba
                + caminosEnGrilla(filas, columnas - 1); // o desde la izquierda
    }

    // ------------------------------------------------------------------
    // INDIRECTA (mutua): esPar llama a esImpar, y esImpar llama a esPar.
    // La recursión "rebota" entre los dos métodos hasta llegar a 0.
    // (Didáctico: en la práctica se usa n % 2 == 0.)
    // ------------------------------------------------------------------
    public static boolean esPar(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        if (n == 0) {
            return true;    // caso base: el 0 es par
        }
        return esImpar(n - 1);   // n es par si n-1 es impar
    }

    public static boolean esImpar(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        if (n == 0) {
            return false;   // caso base: el 0 no es impar
        }
        return esPar(n - 1);     // n es impar si n-1 es par
    }

    // ------------------------------------------------------------------
    // ANIDADA: la llamada recursiva aparece COMO ARGUMENTO de otra llamada
    // recursiva. La "función 91" de McCarthy (1970): devuelve 91 para TODO
    // n <= 100, y n - 10 para n > 100.
    // ------------------------------------------------------------------
    public static int mcCarthy91(int n) {
        if (n > 100) {
            return n - 10;                       // caso base
        }
        return mcCarthy91(mcCarthy91(n + 11));   // la llamada DENTRO de la llamada
    }

    public static void main(String[] args) {
        System.out.println("caminos en grilla de 3x3: " + caminosEnGrilla(3, 3));   // 6
        System.out.println("¿7 es par?   " + esPar(7));                             // false
        System.out.println("¿10 es par?  " + esPar(10));                            // true
        System.out.println("mcCarthy91(1)   = " + mcCarthy91(1));                   // 91
        System.out.println("mcCarthy91(100) = " + mcCarthy91(100));                 // 91
        System.out.println("mcCarthy91(150) = " + mcCarthy91(150));                 // 140
    }
}
