package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e03;

/**
 * Tabla de hash con DIRECCIONAMIENTO ABIERTO y sondeo lineal.
 *
 * Todos los pares viven en el arreglo: si el indice que le toca a una clave
 * esta ocupado, se prueba el siguiente (i+1, i+2, ... modulo capacidad)
 * hasta encontrar lugar. Eso es el sondeo lineal.
 *
 * Borrado con LAPIDAS: no se puede poner null al borrar porque cortaria las
 * busquedas de claves que fueron sondeadas mas alla de esa posicion. En su
 * lugar se deja un marcador (LAPIDA) que la busqueda saltea pero que poner
 * puede reutilizar.
 *
 * Rehash: cuando (ocupadas + lapidas) / capacidad supera 0.5 se duplica la
 * capacidad y se reinsertan solo los pares vivos (las lapidas se descartan).
 *
 * Complejidad: O(1) esperado por operacion con alfa acotado; O(n) peor caso.
 */
public class TablaAbierta<K, V> {

    /** Marcador de casillero borrado. Se compara por identidad (==). */
    private static final Object LAPIDA = new Object();

    /** Umbral de carga (contando lapidas) que dispara el rehash. */
    private static final double ALFA_MAXIMO = 0.5;

    /** Claves: null = nunca usado, LAPIDA = borrado, otro = clave viva. */
    private Object[] claves;
    /** Valores paralelos a las claves. */
    private Object[] valores;
    /** Cantidad de pares vivos. */
    private int tamanio;
    /** Cantidad de lapidas presentes en el arreglo. */
    private int lapidas;

    /** Crea una tabla abierta con la capacidad inicial indicada. */
    public TablaAbierta(int capacidadInicial) {
        if (capacidadInicial <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser positiva: " + capacidadInicial);
        }
        this.claves = new Object[capacidadInicial];
        this.valores = new Object[capacidadInicial];
        this.tamanio = 0;
        this.lapidas = 0;
    }

    /** Cantidad de pares vivos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Capacidad actual del arreglo interno. O(1). */
    public int capacidad() {
        return claves.length;
    }

    private int indiceInicial(K clave) {
        return Math.floorMod(clave.hashCode(), claves.length);
    }

    /**
     * Asocia el valor a la clave (reemplaza si ya estaba). Si en el camino
     * encuentra una lapida, la recuerda para reutilizar ese casillero.
     */
    public void poner(K clave, V valor) {
        int i = indiceInicial(clave);
        int primeraLapida = -1;
        // El sondeo termina seguro porque siempre hay casilleros null (alfa <= 0.5).
        while (claves[i] != null) {
            if (claves[i] == LAPIDA) {
                if (primeraLapida == -1) {
                    primeraLapida = i; // Candidato para reutilizar.
                }
            } else if (claves[i].equals(clave)) {
                valores[i] = valor; // Clave existente: reemplazo el valor.
                return;
            }
            i = (i + 1) % claves.length;
        }
        // Clave nueva: prefiero la lapida encontrada antes que el null final.
        if (primeraLapida != -1) {
            i = primeraLapida;
            lapidas--;
        }
        claves[i] = clave;
        valores[i] = valor;
        tamanio++;
        if ((double) (tamanio + lapidas) / claves.length > ALFA_MAXIMO) {
            rehash(claves.length * 2);
        }
    }

    /** Devuelve el valor asociado a la clave, o null si no esta. */
    @SuppressWarnings("unchecked")
    public V obtener(K clave) {
        int i = indiceInicial(clave);
        while (claves[i] != null) {
            // Una lapida NO corta la busqueda: la clave pudo haber sido
            // sondeada mas alla de este casillero cuando se inserto.
            if (claves[i] != LAPIDA && claves[i].equals(clave)) {
                return (V) valores[i];
            }
            i = (i + 1) % claves.length;
        }
        return null;
    }

    /**
     * Quita la clave dejando una lapida en su lugar. Devuelve el valor que
     * tenia asociado, o null si la clave no estaba.
     */
    @SuppressWarnings("unchecked")
    public V quitar(K clave) {
        int i = indiceInicial(clave);
        while (claves[i] != null) {
            if (claves[i] != LAPIDA && claves[i].equals(clave)) {
                V valor = (V) valores[i];
                claves[i] = LAPIDA; // Lapida: no rompe los sondeos ajenos.
                valores[i] = null;
                tamanio--;
                lapidas++;
                return valor;
            }
            i = (i + 1) % claves.length;
        }
        return null;
    }

    /** Reinserta los pares vivos en un arreglo mas grande, sin lapidas. */
    @SuppressWarnings("unchecked")
    private void rehash(int nuevaCapacidad) {
        Object[] clavesViejas = claves;
        Object[] valoresViejos = valores;
        claves = new Object[nuevaCapacidad];
        valores = new Object[nuevaCapacidad];
        tamanio = 0;
        lapidas = 0;
        for (int j = 0; j < clavesViejas.length; j++) {
            if (clavesViejas[j] != null && clavesViejas[j] != LAPIDA) {
                poner((K) clavesViejas[j], (V) valoresViejos[j]);
            }
        }
    }

    /** Demostracion: colision triple, borrado con lapida y busqueda posterior. */
    public static void main(String[] args) {
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        // "juan", "eva" y "sol" caen las tres en el indice 0 con capacidad 8:
        // quedan en los casilleros 0, 1 y 2 por sondeo lineal.
        tabla.poner("juan", 25);
        tabla.poner("eva", 30);
        tabla.poner("sol", 28);
        System.out.println("quito eva: " + tabla.quitar("eva"));
        // Gracias a la lapida, "sol" (casillero 2) se sigue encontrando.
        System.out.println("sol -> " + tabla.obtener("sol"));
        tabla.poner("lu", 22); // "lu" da indice 1: reutiliza la lapida de "eva".
        System.out.println("tamanio = " + tabla.tamanio());
    }
}
