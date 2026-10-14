package ar.uba.fi.cb100.material.i08_arboles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generador del <b>ejercicio de simulación de árboles</b> (Unidad 8, guía).
 * <p>
 * Al ejecutarlo, sortea un <b>tipo de árbol</b> (ABB, AVL o Árbol B de orden 3)
 * y genera una secuencia de <b>40 números distintos</b> para insertar, más
 * <b>búsquedas</b> y <b>eliminaciones</b>. El ejercicio se resuelve A MANO
 * (dibujando el árbol en los puntos de control) y se verifica con el
 * simulador online indicado.
 * <p>
 * La semilla es el <b>padrón/legajo</b>: cada estudiante recibe un ejercicio
 * distinto, y la cátedra lo <b>reproduce exactamente</b> para corregir
 * ejecutando con el mismo número.
 *
 * Uso: {@code java ... GeneradorDeEjercicios <padron>}  (sin argumento usa
 * una semilla al azar, que se imprime).
 */
public final class GeneradorDeEjercicios {

    /** Un ejercicio generado: inmutable y reproducible a partir de la semilla. */
    public record Ejercicio(long semilla, String tipoDeArbol, String simulador,
                            List<Integer> insertar, List<Integer> buscar,
                            List<Integer> eliminar) {
    }

    private static final String[][] TIPOS = {
        {"ABB (árbol binario de búsqueda)",
         "https://cmps-people.ok.ubc.ca/ylucet/DS/BST.html"},
        {"AVL",
         "https://cmps-people.ok.ubc.ca/ylucet/DS/AVLtree.html"},
        {"Árbol B de orden 3 (máximo 2 claves por nodo; en el simulador, Max. Degree = 3)",
         "https://cmps-people.ok.ubc.ca/ylucet/DS/BTree.html"},
    };

    private static final int CANTIDAD_A_INSERTAR = 40;
    private static final int CANTIDAD_A_BUSCAR = 6;     // mitad presentes, mitad no
    private static final int CANTIDAD_A_ELIMINAR = 8;

    public static Ejercicio generar(long semilla) {
        Random azar = new Random(semilla);

        // tipo de árbol
        String[] tipo = TIPOS[azar.nextInt(TIPOS.length)];

        // 40 números distintos entre 1 y 99, en orden de inserción al azar
        List<Integer> todos = new ArrayList<>();
        for (int i = 1; i <= 99; i++) {
            todos.add(i);
        }
        Collections.shuffle(todos, azar);
        List<Integer> insertar = new ArrayList<>(todos.subList(0, CANTIDAD_A_INSERTAR));
        List<Integer> ausentes = new ArrayList<>(todos.subList(CANTIDAD_A_INSERTAR, todos.size()));

        // búsquedas: mitad que están, mitad que no (mezcladas)
        List<Integer> buscar = new ArrayList<>();
        for (int i = 0; i < CANTIDAD_A_BUSCAR / 2; i++) {
            buscar.add(insertar.get(azar.nextInt(insertar.size())));
            buscar.add(ausentes.get(azar.nextInt(ausentes.size())));
        }
        Collections.shuffle(buscar, azar);

        // eliminaciones: valores presentes, sin repetir
        List<Integer> candidatos = new ArrayList<>(insertar);
        Collections.shuffle(candidatos, azar);
        List<Integer> eliminar = new ArrayList<>(candidatos.subList(0, CANTIDAD_A_ELIMINAR));

        return new Ejercicio(semilla, tipo[0], tipo[1], insertar, buscar, eliminar);
    }

    public static void main(String[] args) {
        long semilla = args.length > 0
                ? Long.parseLong(args[0])
                : Math.abs(new Random().nextLong() % 1_000_000L);

        Ejercicio e = generar(semilla);

        System.out.println("=====================================================");
        System.out.println("  CB100 - Ejercicio de simulación de árboles");
        System.out.println("  Semilla (padrón): " + e.semilla()
                + (args.length == 0 ? "   <-- anotala para poder reproducirlo" : ""));
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("TIPO DE ÁRBOL: " + e.tipoDeArbol());
        System.out.println();
        System.out.println("1) INSERTAR, en este orden (dibujá el árbol tras los");
        System.out.println("   primeros 10, tras 20, tras 30 y tras los 40):");
        imprimirDeADiez(e.insertar());
        System.out.println();
        System.out.println("2) BUSCAR (para cada valor: ¿está? ¿cuántos nodos");
        System.out.println("   visitás hasta responder?):");
        System.out.println("   " + e.buscar());
        System.out.println();
        System.out.println("3) ELIMINAR, en este orden (dibujá el árbol después");
        System.out.println("   de CADA eliminación):");
        System.out.println("   " + e.eliminar());
        System.out.println();
        System.out.println("VERIFICACIÓN: reproducí la secuencia en el simulador");
        System.out.println("   " + e.simulador());
        System.out.println("   y comparalo con tus dibujos paso a paso.");
        System.out.println();
        System.out.println("Para corregir: ejecutar con la misma semilla reproduce");
        System.out.println("el ejercicio completo.");
    }

    private static void imprimirDeADiez(List<Integer> valores) {
        for (int i = 0; i < valores.size(); i += 10) {
            List<Integer> fila = valores.subList(i, Math.min(i + 10, valores.size()));
            System.out.printf("   %2d..%2d:  %s%n", i + 1, i + fila.size(), fila);
        }
    }

    private GeneradorDeEjercicios() {
    }
}
