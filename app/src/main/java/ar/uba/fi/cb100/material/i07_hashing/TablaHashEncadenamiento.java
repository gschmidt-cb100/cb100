package ar.uba.fi.cb100.material.i07_hashing;

/**
 * Diccionario implementado con una <b>tabla de hash con encadenamiento</b>
 * (<i>separate chaining</i>): un arreglo de <b>baldes</b>, donde cada balde es una
 * lista enlazada de pares. Las claves que colisionan (mismo índice) conviven
 * encadenadas en el mismo balde.
 * <p>
 * Con una buena función de hash y factor de carga acotado (acá α ≤ 0.75, con
 * <b>rehash</b> al superarlo), todas las operaciones son <b>O(1) promedio</b>.
 * El peor caso (todas las claves en un mismo balde) es O(n).
 */
public class TablaHashEncadenamiento<K, V> implements Diccionario<K, V> {

    /** Un par clave→valor encadenado dentro de su balde. */
    private static class Par<K, V> {
        final K clave;
        V valor;
        Par<K, V> siguiente;
        Par(K clave, V valor, Par<K, V> siguiente) {
            this.clave = clave; this.valor = valor; this.siguiente = siguiente;
        }
    }

    private static final double FACTOR_DE_CARGA_MAXIMO = 0.75;

    private Par<K, V>[] baldes;
    private int cantidad;

    @SuppressWarnings("unchecked")
    public TablaHashEncadenamiento(int capacidadInicial) {
        baldes = (Par<K, V>[]) new Par[Math.max(1, capacidadInicial)];
    }

    public TablaHashEncadenamiento() { this(8); }

    /** Índice del balde para una clave: hash "aplastado" al rango [0, capacidad). */
    private int indiceDe(K clave, int capacidad) {
        int h = clave.hashCode();
        return Math.floorMod(h, capacidad);   // floorMod evita índices negativos
    }

    @Override
    public void poner(K clave, V valor) {
        int i = indiceDe(clave, baldes.length);
        for (Par<K, V> p = baldes[i]; p != null; p = p.siguiente) {
            if (p.clave.equals(clave)) {      // la clave ya estaba: reemplazo el valor
                p.valor = valor;
                return;
            }
        }
        baldes[i] = new Par<>(clave, valor, baldes[i]);   // agrego al frente del balde
        cantidad++;
        if (factorDeCarga() > FACTOR_DE_CARGA_MAXIMO) rehash();
    }

    @Override
    public V obtener(K clave) {
        int i = indiceDe(clave, baldes.length);
        for (Par<K, V> p = baldes[i]; p != null; p = p.siguiente) {
            if (p.clave.equals(clave)) return p.valor;    // recorre SOLO su balde
        }
        return null;
    }

    @Override
    public boolean contiene(K clave) { return obtener(clave) != null; }

    @Override
    public V quitar(K clave) {
        int i = indiceDe(clave, baldes.length);
        Par<K, V> anterior = null;
        for (Par<K, V> p = baldes[i]; p != null; anterior = p, p = p.siguiente) {
            if (p.clave.equals(clave)) {
                if (anterior == null) baldes[i] = p.siguiente;   // era el primero
                else anterior.siguiente = p.siguiente;           // se lo saltea
                cantidad--;
                return p.valor;
            }
        }
        return null;
    }

    @Override
    public int tamanio() { return cantidad; }

    /** α = elementos / baldes: el largo promedio de cada cadena. */
    public double factorDeCarga() { return (double) cantidad / baldes.length; }

    public int capacidad() { return baldes.length; }

    /** Duplica los baldes y reubica TODOS los pares (sus índices cambian). */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Par<K, V>[] viejos = baldes;
        baldes = (Par<K, V>[]) new Par[viejos.length * 2];
        cantidad = 0;
        for (Par<K, V> balde : viejos) {
            for (Par<K, V> p = balde; p != null; p = p.siguiente) {
                poner(p.clave, p.valor);      // cada par se re-ubica con el nuevo módulo
            }
        }
    }

    public static void main(String[] args) {
        TablaHashEncadenamiento<String, Integer> edades = new TablaHashEncadenamiento<>(4);
        edades.poner("ana", 25);
        edades.poner("juan", 31);
        edades.poner("mia", 19);
        System.out.println(edades.obtener("juan"));        // 31
        edades.poner("juan", 32);                          // reemplaza
        System.out.println(edades.obtener("juan"));        // 32
        System.out.println(edades.contiene("pedro"));      // false
        System.out.println(edades.quitar("ana"));          // 25
        System.out.println(edades.tamanio());              // 2
        System.out.println("α = " + edades.factorDeCarga());
    }
}
