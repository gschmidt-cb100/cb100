package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e09;

/**
 * e09: ordenamiento por mezcla (Mergesort) sobre una lista simplemente
 * enlazada de enteros.
 *
 * <p>Mergesort es ideal para listas enlazadas: no necesita acceso por indice y
 * la mezcla se hace reenlazando nodos, sin copiar arreglos. La lista se parte en
 * dos mitades con la tecnica de los dos punteros (uno lento que avanza de a un
 * nodo y uno rapido que avanza de a dos): cuando el rapido llega al final, el
 * lento quedo en el medio.</p>
 *
 * <p>Complejidad temporal: {@code O(n log n)}.</p>
 */
public final class MergesortLista {

    private MergesortLista() {
    }

    /**
     * Construye una lista enlazada a partir de un arreglo (util para pruebas y demo).
     *
     * @param valores valores en orden
     * @return la cabeza de la lista, o {@code null} si el arreglo esta vacio
     */
    public static Nodo desdeArreglo(int[] valores) {
        Nodo cabeza = null;
        for (int i = valores.length - 1; i >= 0; i--) {
            Nodo nuevo = new Nodo(valores[i]);
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
        return cabeza;
    }

    /**
     * Vuelca la lista a un arreglo (util para pruebas y demo).
     *
     * @param cabeza cabeza de la lista
     * @return arreglo con los datos en el orden de la lista
     */
    public static int[] aArreglo(Nodo cabeza) {
        int largo = 0;
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            largo++;
        }
        int[] resultado = new int[largo];
        int i = 0;
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            resultado[i++] = n.dato;
        }
        return resultado;
    }

    /**
     * Ordena la lista de menor a mayor y devuelve la nueva cabeza.
     *
     * @param cabeza cabeza de la lista (puede ser {@code null})
     * @return cabeza de la lista ordenada
     */
    public static Nodo ordenar(Nodo cabeza) {
        // Caso base: lista vacia o de un solo nodo, ya esta ordenada.
        if (cabeza == null || cabeza.siguiente == null) {
            return cabeza;
        }
        // Dividir: partimos en dos mitades.
        Nodo medio = partir(cabeza);
        Nodo segundaMitad = medio.siguiente;
        medio.siguiente = null; // cortamos el enlace entre mitades

        // Conquistar: ordenamos cada mitad recursivamente.
        Nodo izquierda = ordenar(cabeza);
        Nodo derecha = ordenar(segundaMitad);

        // Combinar: mezclamos las dos mitades ordenadas.
        return mezclar(izquierda, derecha);
    }

    /**
     * Devuelve el nodo medio (final de la primera mitad) usando dos punteros:
     * el lento avanza de a uno, el rapido de a dos.
     */
    private static Nodo partir(Nodo cabeza) {
        Nodo lento = cabeza;
        Nodo rapido = cabeza.siguiente;
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }
        return lento;
    }

    /**
     * Mezcla dos listas ordenadas reenlazando nodos (sin crear nodos nuevos).
     */
    private static Nodo mezclar(Nodo a, Nodo b) {
        Nodo centinela = new Nodo(0); // nodo ficticio para simplificar el enlace
        Nodo cola = centinela;
        while (a != null && b != null) {
            if (a.dato <= b.dato) {
                cola.siguiente = a;
                a = a.siguiente;
            } else {
                cola.siguiente = b;
                b = b.siguiente;
            }
            cola = cola.siguiente;
        }
        cola.siguiente = (a != null) ? a : b; // enganchamos lo que reste
        return centinela.siguiente;
    }

    public static void main(String[] args) {
        Nodo lista = desdeArreglo(new int[]{5, 2, 9, 1, 5, 6, 3});
        Nodo ordenada = ordenar(lista);
        System.out.print("ordenada: ");
        for (Nodo n = ordenada; n != null; n = n.siguiente) {
            System.out.print(n.dato + " ");
        }
        System.out.println();
    }
}
