package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e10;

/**
 * Pila sobre arreglo Object[] que evita fugas de memoria (memory leak).
 *
 * El problema clasico: al desapilar alcanzaria con bajar el contador de tamanio,
 * pero entonces el arreglo seguiria guardando la referencia al objeto "sacado",
 * impidiendo que el recolector de basura lo libere. La solucion es poner en null
 * la posicion liberada para no retenerlo (soltar la referencia obsoleta).
 */
public class PilaSinFuga {
    // Package-private para poder inspeccionar el arreglo desde el test.
    Object[] elementos;
    private int tamanio;

    public PilaSinFuga(int capacidad) {
        elementos = new Object[capacidad];
    }

    public void apilar(Object o) {
        elementos[tamanio] = o;
        tamanio++;
    }

    public Object desapilar() {
        if (tamanio == 0) {
            throw new IllegalStateException("pila vacia");
        }
        tamanio--;
        Object o = elementos[tamanio];
        elementos[tamanio] = null; // clave: soltamos la referencia para no retener el objeto
        return o;
    }

    public int tamanio() {
        return tamanio;
    }

    /** Permite al test observar el contenido crudo del arreglo interno. */
    Object espiar(int i) {
        return elementos[i];
    }

    public static void main(String[] args) {
        PilaSinFuga pila = new PilaSinFuga(4);
        pila.apilar("a");
        pila.apilar("b");
        System.out.println("Desapilado: " + pila.desapilar());
        System.out.println("Tamanio: " + pila.tamanio());
        System.out.println("Posicion liberada (debe ser null): " + pila.espiar(1));
    }
}
