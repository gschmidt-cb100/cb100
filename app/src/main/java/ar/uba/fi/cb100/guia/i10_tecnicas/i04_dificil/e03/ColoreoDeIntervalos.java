package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * TECNICA: GREEDY (algoritmo goloso) + min-heap como estructura auxiliar.
 *
 * Problema: asignar aulas a clases dadas como intervalos semiabiertos
 * [inicio, fin) usando la MINIMA cantidad de aulas, sin que dos clases
 * solapadas compartan aula. (Dos intervalos [a,b) y [c,d) solapan si
 * a < d y c < b; que uno termine justo cuando otro empieza NO es solape.)
 *
 * Estrategia golosa: procesamos los intervalos ordenados por inicio.
 * Para cada uno, si alguna aula ya usada quedo libre (su ultima clase
 * termino en o antes de este inicio), la REUTILIZAMOS; si no, abrimos
 * un aula nueva. Para saber en O(log n) si hay aula libre usamos un
 * min-heap de pares (finLibre, aula): la raiz es el aula que se libera
 * mas temprano; si ni esa sirve, ninguna sirve.
 *
 * Por que la decision golosa es optima: en el momento en que abrimos la
 * aula numero k es porque hay k intervalos que contienen el instante de
 * inicio actual (los k-1 ocupados mas el nuevo). Esos k intervalos
 * solapan de a pares en ese instante, asi que CUALQUIER asignacion
 * necesita al menos k aulas. Nuestra solucion usa exactamente el maximo
 * de solapes simultaneos, que es una cota inferior: es optima.
 *
 * Complejidad: O(n log n) por el orden y las n operaciones de heap.
 */
public class ColoreoDeIntervalos {

    /** Una clase con nombre, en el intervalo semiabierto [inicio, fin). */
    public record Intervalo(String nombre, int inicio, int fin) {
        public Intervalo {
            if (fin <= inicio) {
                throw new IllegalArgumentException(
                        "Intervalo invalido: [" + inicio + ", " + fin + ")");
            }
        }
    }

    /**
     * Asigna a cada intervalo (por nombre) un numero de aula 1..k, con k
     * minimo. Intervalos que solapan reciben aulas distintas.
     */
    public Map<String, Integer> asignarAulas(List<Intervalo> intervalos) {
        List<Intervalo> ordenados = new ArrayList<>(intervalos);
        ordenados.sort(Comparator.comparingInt(Intervalo::inicio));

        // Min-heap de (finLibre, aula): la raiz es el aula que se desocupa antes.
        PriorityQueue<int[]> aulas = new PriorityQueue<>(Comparator.comparingInt(par -> par[0]));
        Map<String, Integer> asignacion = new LinkedHashMap<>();
        int aulasAbiertas = 0;

        for (Intervalo intervalo : ordenados) {
            int aula;
            if (!aulas.isEmpty() && aulas.peek()[0] <= intervalo.inicio()) {
                // El aula que se libera mas temprano ya esta libre: la reutilizamos.
                aula = aulas.poll()[1];
            } else {
                // Ninguna aula esta libre (si la mas temprana no sirve, ninguna): abrimos otra.
                aulasAbiertas++;
                aula = aulasAbiertas;
            }
            asignacion.put(intervalo.nombre(), aula);
            aulas.add(new int[] {intervalo.fin(), aula});
        }
        return asignacion;
    }

    /** Cantidad de aulas que usa una asignacion (el maximo numero de aula). */
    public int cantidadDeAulas(Map<String, Integer> asignacion) {
        return asignacion.values().stream().mapToInt(Integer::intValue).max().orElse(0);
    }

    /** Demostracion: tres clases que solapan de a dos entran en 2 aulas. */
    public static void main(String[] args) {
        ColoreoDeIntervalos coloreador = new ColoreoDeIntervalos();
        List<Intervalo> clases = List.of(
                new Intervalo("Algebra", 8, 12),
                new Intervalo("Analisis", 10, 14),
                new Intervalo("Fisica", 12, 16));
        Map<String, Integer> asignacion = coloreador.asignarAulas(clases);
        asignacion.forEach((nombre, aula) -> System.out.println(nombre + " -> aula " + aula));
        System.out.println("Aulas usadas: " + coloreador.cantidadDeAulas(asignacion));
    }
}
