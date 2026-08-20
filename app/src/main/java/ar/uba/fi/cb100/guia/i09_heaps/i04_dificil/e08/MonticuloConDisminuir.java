package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e08;

import java.util.HashMap;
import java.util.Map;

/**
 * Min-heap A MANO con actualizacion de prioridad (decrease-key), la
 * operacion estrella de Dijkstra y Prim.
 *
 * El problema: un heap pelado no sabe DONDE esta cada valor, asi que
 * disminuir la prioridad de uno obligaria a buscarlo en O(n). La solucion
 * es mantener un HashMap valor -> posicion actualizado en CADA intercambio:
 * asi ubicamos cualquier valor en O(1) y disminuirClave queda O(log n).
 *
 * El costo de ese indice es que las claves deben ser UNICAS (un HashMap no
 * puede guardar dos posiciones para el mismo valor): encolar un duplicado
 * lanza IllegalArgumentException.
 *
 * disminuirClave(viejo, nuevo) con nuevo < viejo: reemplaza el valor en su
 * posicion y lo hace flotar (solo puede subir, porque se achico).
 */
public class MonticuloConDisminuir<T extends Comparable<T>> {

    private static final int CAPACIDAD_INICIAL = 8;

    /** Arreglo que guarda el arbol por niveles. */
    private Object[] datos;

    /** Cantidad de elementos guardados. */
    private int tamanio;

    /** Indice valor -> posicion en el arreglo, mantenido en cada intercambio. */
    private final Map<T, Integer> posiciones;

    public MonticuloConDisminuir() {
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
        this.posiciones = new HashMap<>();
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si el monticulo esta vacio. O(1). */
    public boolean estaVacio() {
        return tamanio == 0;
    }

    /** Indica si el valor esta en el monticulo. O(1) gracias al indice. */
    public boolean contiene(T valor) {
        return posiciones.containsKey(valor);
    }

    /**
     * Devuelve el minimo SIN sacarlo.
     *
     * @throws IllegalStateException si el monticulo esta vacio.
     */
    public T verMinimo() {
        if (estaVacio()) {
            throw new IllegalStateException("El monticulo esta vacio");
        }
        return elemento(0);
    }

    /**
     * Agrega un valor NUEVO. O(log n).
     *
     * @throws IllegalArgumentException si es null o ya estaba (claves unicas).
     */
    public void encolar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se admiten valores null");
        }
        if (posiciones.containsKey(valor)) {
            throw new IllegalArgumentException("La clave ya esta en el monticulo: " + valor);
        }
        asegurarCapacidad();
        datos[tamanio] = valor;
        posiciones.put(valor, tamanio);
        flotar(tamanio);
        tamanio++;
    }

    /** Saca y devuelve el minimo, sacandolo tambien del indice. O(log n). */
    public T desencolarMinimo() {
        T minimo = verMinimo();
        posiciones.remove(minimo);
        tamanio--;
        if (tamanio > 0) {
            datos[0] = datos[tamanio];
            posiciones.put(elemento(0), 0);
            hundir(0);
        }
        datos[tamanio] = null;
        return minimo;
    }

    /**
     * Reemplaza una clave por otra MENOR y la re-flota. O(log n): el HashMap
     * ubica la posicion en O(1) y flotar recorre a lo sumo la altura.
     *
     * @throws IllegalArgumentException si viejo no esta, si nuevo no es
     *         estrictamente menor, o si nuevo ya existe como clave.
     */
    public void disminuirClave(T viejo, T nuevo) {
        Integer posicion = posiciones.get(viejo);
        if (posicion == null) {
            throw new IllegalArgumentException("La clave no esta en el monticulo: " + viejo);
        }
        if (nuevo == null || nuevo.compareTo(viejo) >= 0) {
            throw new IllegalArgumentException(
                    "La clave nueva debe ser estrictamente menor: " + nuevo + " vs " + viejo);
        }
        if (posiciones.containsKey(nuevo)) {
            throw new IllegalArgumentException("La clave nueva ya existe: " + nuevo);
        }
        posiciones.remove(viejo);
        datos[posicion] = nuevo;
        posiciones.put(nuevo, posicion);
        flotar(posicion); // Se achico: solo puede subir.
    }

    private void asegurarCapacidad() {
        if (tamanio == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            System.arraycopy(datos, 0, nuevo, 0, tamanio);
            datos = nuevo;
        }
    }

    /** Sube el elemento de i mientras sea menor que su padre. */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (elemento(i).compareTo(elemento(padre)) >= 0) {
                break;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /** Baja el elemento de i comparando con el MENOR de sus dos hijos. */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < tamanio && elemento(izquierdo).compareTo(elemento(menor)) < 0) {
                menor = izquierdo;
            }
            if (derecho < tamanio && elemento(derecho).compareTo(elemento(menor)) < 0) {
                menor = derecho;
            }
            if (menor == i) {
                break;
            }
            intercambiar(i, menor);
            i = menor;
        }
    }

    /** Intercambia dos posiciones Y actualiza el indice: el paso clave del ejercicio. */
    private void intercambiar(int i, int j) {
        Object aux = datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
        posiciones.put(elemento(i), i);
        posiciones.put(elemento(j), j);
    }

    @SuppressWarnings("unchecked")
    private T elemento(int i) {
        return (T) datos[i];
    }

    /** Demostracion: una hoja "descubre un camino mas corto" y pasa al frente. */
    public static void main(String[] args) {
        MonticuloConDisminuir<Integer> monticulo = new MonticuloConDisminuir<>();
        for (int valor : new int[] {10, 30, 20, 50, 40}) {
            monticulo.encolar(valor);
        }
        System.out.println("Minimo inicial = " + monticulo.verMinimo()); // 10
        monticulo.disminuirClave(50, 5); // Como en Dijkstra al relajar una arista.
        System.out.println("Tras disminuir 50 -> 5, minimo = " + monticulo.verMinimo()); // 5
    }
}
