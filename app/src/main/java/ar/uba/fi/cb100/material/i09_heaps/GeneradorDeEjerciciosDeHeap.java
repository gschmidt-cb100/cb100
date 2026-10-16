package ar.uba.fi.cb100.material.i09_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generador del <b>ejercicio de simulación de heaps</b> (Unidad 9, guía).
 * <p>
 * Al ejecutarlo, sortea si el heap es <b>de mínimo o de máximo</b> y genera una
 * secuencia de <b>40 números distintos</b> para encolar, más <b>búsquedas</b> y
 * <b>extracciones</b> de la raíz. El ejercicio se resuelve A MANO, dibujando el
 * heap como árbol <i>y</i> como arreglo en los puntos de control.
 * <p>
 * La semilla es el <b>padrón/legajo</b>: cada estudiante recibe un ejercicio
 * distinto, y la cátedra lo <b>reproduce exactamente</b> para corregir. Con el
 * argumento {@code --solucion} el programa además imprime el estado del heap
 * en cada punto de control, calculado con {@link MinHeap} y {@link MaxHeap}
 * del material: ése es el "simulador" de este ejercicio.
 *
 * <pre>
 *   java ... GeneradorDeEjerciciosDeHeap 104523              (el ejercicio)
 *   java ... GeneradorDeEjerciciosDeHeap 104523 --solucion   (ejercicio + solución)
 * </pre>
 */
public final class GeneradorDeEjerciciosDeHeap {

    /** Tipo de heap sorteado. */
    public enum Tipo {
        MINIMO("heap de MÍNIMO (cada padre ≤ sus hijos; en la raíz, el menor)"),
        MAXIMO("heap de MÁXIMO (cada padre ≥ sus hijos; en la raíz, el mayor)");

        private final String descripcion;

        Tipo(String descripcion) { this.descripcion = descripcion; }

        public String getDescripcion() { return descripcion; }
    }

    /** Un ejercicio generado: inmutable y reproducible a partir de la semilla. */
    public record Ejercicio(long semilla, Tipo tipo, List<Integer> encolar,
                            List<Integer> buscar, int extracciones) {
    }

    /** Resultado de una búsqueda a mano: si está y cuántos nodos se visitan con poda. */
    public record Busqueda(int valor, boolean esta, int nodosVisitados) {
    }

    private static final int CANTIDAD_A_ENCOLAR = 40;
    private static final int CANTIDAD_A_BUSCAR = 6;     // mitad presentes, mitad no
    private static final int CANTIDAD_A_EXTRAER = 8;
    private static final int PUNTO_DE_CONTROL = 10;

    public static Ejercicio generar(long semilla) {
        Random azar = new Random(semilla);

        // 40 números distintos entre 1 y 99, en orden de llegada al azar
        List<Integer> todos = new ArrayList<>();
        for (int i = 1; i <= 99; i++) {
            todos.add(i);
        }
        Collections.shuffle(todos, azar);
        List<Integer> encolar = new ArrayList<>(todos.subList(0, CANTIDAD_A_ENCOLAR));
        List<Integer> ausentes = new ArrayList<>(todos.subList(CANTIDAD_A_ENCOLAR, todos.size()));

        // el tipo se sortea DESPUÉS de mezclar: el primer número de un Random con
        // semillas chicas y consecutivas está mal repartido (sale casi siempre igual)
        Tipo tipo = azar.nextBoolean() ? Tipo.MINIMO : Tipo.MAXIMO;

        // búsquedas: mitad que están, mitad que no (mezcladas)
        List<Integer> buscar = new ArrayList<>();
        for (int i = 0; i < CANTIDAD_A_BUSCAR / 2; i++) {
            buscar.add(encolar.get(azar.nextInt(encolar.size())));
            buscar.add(ausentes.get(azar.nextInt(ausentes.size())));
        }
        Collections.shuffle(buscar, azar);

        return new Ejercicio(semilla, tipo, encolar, buscar, CANTIDAD_A_EXTRAER);
    }

    // ------------------------------------------------------------ solución

    /**
     * Estado del heap (como arreglo) después de encolar los primeros {@code n}
     * valores del ejercicio, en el orden dado.
     */
    public static Integer[] heapTrasEncolar(Ejercicio e, int n) {
        return encolarPrimeros(e, n).arreglo();
    }

    /**
     * Estados del heap después de cada una de las extracciones de la raíz,
     * partiendo del heap con los 40 valores. El elemento {@code i} de la lista
     * es el arreglo tras la extracción {@code i+1}; {@code extraidos} recibe los
     * valores que salieron, en orden.
     */
    public static List<Integer[]> heapsTrasExtraer(Ejercicio e, List<Integer> extraidos) {
        Monticulo m = encolarPrimeros(e, e.encolar().size());
        List<Integer[]> estados = new ArrayList<>();
        for (int i = 0; i < e.extracciones(); i++) {
            extraidos.add(m.desencolar());
            estados.add(m.arreglo());
        }
        return estados;
    }

    /**
     * Búsqueda a mano en el heap completo (los 40 valores), con la única poda
     * que el invariante permite: en un heap de mínimo, un subárbol cuya raíz ya
     * es mayor que lo buscado no puede contenerlo (y al revés en el de máximo).
     * Cuenta los nodos visitados.
     */
    public static Busqueda buscar(Ejercicio e, int valor) {
        Integer[] heap = heapTrasEncolar(e, e.encolar().size());
        int[] visitados = {0};
        boolean esta = buscarDesde(heap, 0, valor, e.tipo(), visitados);
        return new Busqueda(valor, esta, visitados[0]);
    }

    private static boolean buscarDesde(Integer[] heap, int i, int valor, Tipo tipo, int[] visitados) {
        if (i >= heap.length) {
            return false;
        }
        visitados[0]++;
        int actual = heap[i];
        if (actual == valor) {
            return true;
        }
        boolean podar = (tipo == Tipo.MINIMO) ? actual > valor : actual < valor;
        if (podar) {
            return false;   // todo este subárbol está "más allá" del valor buscado
        }
        return buscarDesde(heap, 2 * i + 1, valor, tipo, visitados)
                || buscarDesde(heap, 2 * i + 2, valor, tipo, visitados);
    }

    private static Monticulo encolarPrimeros(Ejercicio e, int n) {
        Monticulo m = new Monticulo(e.tipo());
        for (int v : e.encolar().subList(0, n)) {
            m.encolar(v);
        }
        return m;
    }

    /** Envoltorio mínimo para usar MinHeap o MaxHeap según el tipo sorteado. */
    private static final class Monticulo {
        private final MinHeap<Integer> minimo;
        private final MaxHeap<Integer> maximo;

        Monticulo(Tipo tipo) {
            minimo = (tipo == Tipo.MINIMO) ? new MinHeap<>(CANTIDAD_A_ENCOLAR) : null;
            maximo = (tipo == Tipo.MAXIMO) ? new MaxHeap<>(CANTIDAD_A_ENCOLAR) : null;
        }

        void encolar(int v) {
            if (minimo != null) { minimo.encolar(v); } else { maximo.encolar(v); }
        }

        int desencolar() {
            return (minimo != null) ? minimo.desencolarMinimo() : maximo.desencolarMaximo();
        }

        Integer[] arreglo() {
            Object[] datos = (minimo != null) ? minimo.aArreglo() : maximo.aArreglo();
            return Arrays.copyOf(datos, datos.length, Integer[].class);
        }
    }

    // ------------------------------------------------------------ main

    public static void main(String[] args) {
        boolean conSolucion = Arrays.asList(args).contains("--solucion");
        Long semillaDada = null;
        for (String a : args) {
            if (!a.startsWith("--")) {
                semillaDada = Long.parseLong(a);
            }
        }
        long semilla = (semillaDada != null)
                ? semillaDada
                : Math.abs(new Random().nextLong() % 1_000_000L);

        Ejercicio e = generar(semilla);

        System.out.println("=====================================================");
        System.out.println("  CB100 - Ejercicio de simulación de heaps");
        System.out.println("  Semilla (padrón): " + e.semilla()
                + (semillaDada == null ? "   <-- anotala para poder reproducirlo" : ""));
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("TIPO DE HEAP: " + e.tipo().getDescripcion());
        System.out.println();
        System.out.println("1) ENCOLAR, en este orden (dibujá el heap como ÁRBOL y como");
        System.out.println("   ARREGLO tras los primeros 10, tras 20, tras 30 y tras los 40;");
        System.out.println("   anotá cada intercambio al flotar):");
        imprimirDeADiez(e.encolar());
        System.out.println();
        System.out.println("2) BUSCAR en el heap de 40 (para cada valor: ¿está? ¿cuántos");
        System.out.println("   nodos visitás, podando los subárboles que no pueden tenerlo?):");
        System.out.println("   " + e.buscar());
        System.out.println();
        System.out.println("3) EXTRAER la raíz " + e.extracciones() + " veces seguidas (dibujá el heap");
        System.out.println("   después de CADA extracción; anotá qué hoja sube y cómo se hunde).");
        System.out.println();
        System.out.println("VERIFICACIÓN: ejecutá con tu padrón y --solucion para ver el");
        System.out.println("   estado del heap en cada punto de control.");

        if (conSolucion) {
            imprimirSolucion(e);
        }
    }

    private static void imprimirSolucion(Ejercicio e) {
        System.out.println();
        System.out.println("----------------------- SOLUCIÓN ---------------------");
        for (int n = PUNTO_DE_CONTROL; n <= e.encolar().size(); n += PUNTO_DE_CONTROL) {
            System.out.printf("tras encolar %2d: %s%n", n, Arrays.toString(heapTrasEncolar(e, n)));
        }
        System.out.println();
        for (int v : e.buscar()) {
            Busqueda b = buscar(e, v);
            System.out.printf("buscar %2d: %s, %d nodos visitados%n",
                    b.valor(), b.esta() ? "ESTÁ" : "no está", b.nodosVisitados());
        }
        System.out.println();
        List<Integer> extraidos = new ArrayList<>();
        List<Integer[]> estados = heapsTrasExtraer(e, extraidos);
        for (int i = 0; i < estados.size(); i++) {
            System.out.printf("extracción %d: sale %2d -> %s%n",
                    i + 1, extraidos.get(i), Arrays.toString(estados.get(i)));
        }
    }

    private static void imprimirDeADiez(List<Integer> valores) {
        for (int i = 0; i < valores.size(); i += PUNTO_DE_CONTROL) {
            List<Integer> fila = valores.subList(i, Math.min(i + PUNTO_DE_CONTROL, valores.size()));
            System.out.printf("   %2d..%2d:  %s%n", i + 1, i + fila.size(), fila);
        }
    }

    private GeneradorDeEjerciciosDeHeap() {
    }
}
