package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e10;

/**
 * Cola de prioridad ESTABLE sobre un heap PROPIO: los elementos con igual
 * prioridad salen en el orden en que llegaron (FIFO).
 *
 * Por que el heap pelado NO lo garantiza: al desencolar, la ultima hoja
 * salta a la raiz y se hunde intercambiandose con nodos cualesquiera. Esos
 * saltos mezclan las posiciones relativas de los empatados, asi que dos
 * valores iguales pueden salir en cualquier orden (igual que PriorityQueue,
 * que tampoco es estable).
 *
 * El truco clasico: guardar pares (valor, secuencia) donde la secuencia es
 * un contador que crece con cada encolar, y comparar primero por valor y,
 * si empatan, por secuencia. Asi NUNCA hay empates reales dentro del heap
 * y el desempate reproduce el orden de llegada. Costo: un long extra por
 * elemento; encolar y desencolar siguen siendo O(log n).
 */
public class ColaEstable<T extends Comparable<T>> {

    /**
     * Par (valor, secuencia): compara por valor y desempata por orden de
     * llegada. Es el elemento que de verdad vive dentro del heap.
     */
    private static class Entrada<T extends Comparable<T>> implements Comparable<Entrada<T>> {
        final T valor;
        final long secuencia;

        Entrada(T valor, long secuencia) {
            this.valor = valor;
            this.secuencia = secuencia;
        }

        @Override
        public int compareTo(Entrada<T> otra) {
            int porValor = this.valor.compareTo(otra.valor);
            if (porValor != 0) {
                return porValor;
            }
            return Long.compare(this.secuencia, otra.secuencia); // Desempate FIFO.
        }
    }

    private static final int CAPACIDAD_INICIAL = 8;

    /** Arreglo del heap de Entradas, por niveles. */
    private Object[] datos;

    /** Cantidad de elementos guardados. */
    private int tamanio;

    /** Numero de llegada del proximo elemento. Nunca se repite ni decrece. */
    private long proximaSecuencia;

    public ColaEstable() {
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
        this.proximaSecuencia = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si la cola esta vacia. O(1). */
    public boolean estaVacio() {
        return tamanio == 0;
    }

    /**
     * Devuelve (sin sacar) el proximo a salir: menor valor y, entre iguales,
     * el que llego primero.
     *
     * @throws IllegalStateException si la cola esta vacia.
     */
    public T verPrimero() {
        if (estaVacio()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        return entrada(0).valor;
    }

    /** Encola un valor sellandolo con su numero de llegada. O(log n). */
    public void encolar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se admiten valores null");
        }
        asegurarCapacidad();
        datos[tamanio] = new Entrada<>(valor, proximaSecuencia++);
        flotar(tamanio);
        tamanio++;
    }

    /** Desencola el proximo: ultima hoja a la raiz + hundir. O(log n). */
    public T desencolar() {
        T primero = verPrimero();
        tamanio--;
        datos[0] = datos[tamanio];
        datos[tamanio] = null;
        if (tamanio > 0) {
            hundir(0);
        }
        return primero;
    }

    private void asegurarCapacidad() {
        if (tamanio == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            System.arraycopy(datos, 0, nuevo, 0, tamanio);
            datos = nuevo;
        }
    }

    /** Sube la entrada de i mientras sea menor que su padre (i - 1) / 2. */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (entrada(i).compareTo(entrada(padre)) >= 0) {
                break;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /** Baja la entrada de i comparando con el MENOR de sus dos hijos. */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < tamanio && entrada(izquierdo).compareTo(entrada(menor)) < 0) {
                menor = izquierdo;
            }
            if (derecho < tamanio && entrada(derecho).compareTo(entrada(menor)) < 0) {
                menor = derecho;
            }
            if (menor == i) {
                break;
            }
            intercambiar(i, menor);
            i = menor;
        }
    }

    private void intercambiar(int i, int j) {
        Object aux = datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
    }

    @SuppressWarnings("unchecked")
    private Entrada<T> entrada(int i) {
        return (Entrada<T>) datos[i];
    }

    /**
     * Demostracion de la estabilidad. Encolamos tres pacientes con la misma
     * urgencia: en un heap pelado (o en PriorityQueue) el orden de salida
     * entre ellos es impredecible, porque el hundir mueve la ultima hoja a
     * la raiz y mezcla a los empatados. Con el par (valor, secuencia) el
     * desempate por llegada garantiza el FIFO.
     */
    public static void main(String[] args) {
        ColaEstable<Integer> urgencias = new ColaEstable<>();
        // (urgencia 2) ana, luego (urgencia 1) beto, luego mas urgencia 2.
        urgencias.encolar(2);
        urgencias.encolar(1);
        urgencias.encolar(2);
        urgencias.encolar(2);
        StringBuilder salida = new StringBuilder("Salen: ");
        while (!urgencias.estaVacio()) {
            salida.append(urgencias.desencolar()).append(' ');
        }
        // Los tres 2 salen en el mismo orden en que entraron.
        System.out.println(salida); // 1 2 2 2
    }
}
