package ar.uba.fi.cb100.material.i07_hashing;

/**
 * Diccionario con <b>direccionamiento abierto</b> y <b>sondeo lineal</b>
 * (<i>linear probing</i>): todos los pares viven en el arreglo mismo. Si la celda
 * que indica el hash está ocupada, se prueba la siguiente (y la siguiente…)
 * hasta encontrar lugar.
 * <p>
 * El borrado usa <b>lápidas</b> ({@code LAPIDA}): no se puede dejar la celda en
 * {@code null} porque cortaría las cadenas de sondeo de otras claves.
 * Rehash cuando α supera 0.5 (el direccionamiento abierto degrada rápido con la
 * tabla llena).
 */
public class TablaHashAbierta<K, V> implements Diccionario<K, V> {

    private static final Object LAPIDA = new Object();   // marca "acá hubo algo"
    private static final double FACTOR_DE_CARGA_MAXIMO = 0.5;

    private Object[] claves;
    private Object[] valores;
    private int cantidad;          // pares vivos
    private int ocupadas;          // vivos + lápidas (para el factor de carga real)

    public TablaHashAbierta(int capacidadInicial) {
        claves = new Object[Math.max(2, capacidadInicial)];
        valores = new Object[claves.length];
    }

    public TablaHashAbierta() { this(8); }

    private int indiceInicial(K clave) {
        return Math.floorMod(clave.hashCode(), claves.length);
    }

    @Override
    public void poner(K clave, V valor) {
        if ((double) (ocupadas + 1) / claves.length > FACTOR_DE_CARGA_MAXIMO) {
            rehash();
        }
        int i = indiceInicial(clave);
        int primeraLapida = -1;
        while (claves[i] != null) {                       // sondeo lineal
            if (claves[i] == LAPIDA) {
                if (primeraLapida == -1) {
                    primeraLapida = i;   // celda reutilizable
                }
            } else if (claves[i].equals(clave)) {
                valores[i] = valor;                       // ya estaba: reemplazo
                return;
            }
            i = (i + 1) % claves.length;                  // pruebo la siguiente
        }
        int destino = (primeraLapida != -1) ? primeraLapida : i;
        if (destino == i) {
            ocupadas++;   // celda nueva (no lápida)
        }
        claves[destino] = clave;
        valores[destino] = valor;
        cantidad++;
    }

    /** Busca la celda de una clave sondeando; -1 si no está. */
    private int celdaDe(K clave) {
        int i = indiceInicial(clave);
        while (claves[i] != null) {                       // null = fin de la cadena
            if (claves[i] != LAPIDA && claves[i].equals(clave)) {
                return i;
            }
            i = (i + 1) % claves.length;                  // las lápidas NO cortan
        }
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V obtener(K clave) {
        int i = celdaDe(clave);
        return i == -1 ? null : (V) valores[i];
    }

    @Override
    public boolean contiene(K clave) { return celdaDe(clave) != -1; }

    @Override
    @SuppressWarnings("unchecked")
    public V quitar(K clave) {
        int i = celdaDe(clave);
        if (i == -1) {
            return null;
        }
        V valor = (V) valores[i];
        claves[i] = LAPIDA;            // ¡no null! una lápida mantiene viva la cadena
        valores[i] = null;
        cantidad--;
        return valor;
    }

    @Override
    public int tamanio() { return cantidad; }

    public int capacidad() { return claves.length; }

    /** Duplica el arreglo y reubica los pares vivos (las lápidas se descartan). */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Object[] viejasClaves = claves;
        Object[] viejosValores = valores;
        claves = new Object[viejasClaves.length * 2];
        valores = new Object[claves.length];
        cantidad = 0;
        ocupadas = 0;
        for (int i = 0; i < viejasClaves.length; i++) {
            if (viejasClaves[i] != null && viejasClaves[i] != LAPIDA) {
                poner((K) viejasClaves[i], (V) viejosValores[i]);
            }
        }
    }

    public static void main(String[] args) {
        TablaHashAbierta<String, String> capital = new TablaHashAbierta<>(8);
        capital.poner("argentina", "Buenos Aires");
        capital.poner("uruguay", "Montevideo");
        capital.poner("chile", "Santiago");
        System.out.println(capital.obtener("uruguay"));    // Montevideo
        System.out.println(capital.quitar("uruguay"));     // Montevideo (deja lápida)
        System.out.println(capital.contiene("uruguay"));   // false
        System.out.println(capital.obtener("chile"));      // Santiago (la lápida no corta)
        System.out.println(capital.tamanio());             // 2
    }
}
