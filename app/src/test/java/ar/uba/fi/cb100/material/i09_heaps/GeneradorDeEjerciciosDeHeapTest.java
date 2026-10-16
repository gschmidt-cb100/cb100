package ar.uba.fi.cb100.material.i09_heaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.uba.fi.cb100.material.i09_heaps.GeneradorDeEjerciciosDeHeap.Busqueda;
import ar.uba.fi.cb100.material.i09_heaps.GeneradorDeEjerciciosDeHeap.Ejercicio;
import ar.uba.fi.cb100.material.i09_heaps.GeneradorDeEjerciciosDeHeap.Tipo;

class GeneradorDeEjerciciosDeHeapTest {

    @Test
    @DisplayName("La misma semilla reproduce exactamente el mismo ejercicio")
    void reproducible() {
        assertEquals(GeneradorDeEjerciciosDeHeap.generar(104523L),
                     GeneradorDeEjerciciosDeHeap.generar(104523L));
    }

    @Test
    @DisplayName("Semillas distintas dan ejercicios distintos")
    void distintasSemillas() {
        assertFalse(GeneradorDeEjerciciosDeHeap.generar(1L).encolar()
                .equals(GeneradorDeEjerciciosDeHeap.generar(2L).encolar()));
    }

    @Test
    @DisplayName("Son 40 números distintos entre 1 y 99, 6 búsquedas mixtas y 8 extracciones")
    void formaDelEjercicio() {
        Ejercicio e = GeneradorDeEjerciciosDeHeap.generar(77L);
        assertEquals(40, e.encolar().size());
        assertEquals(40, new HashSet<>(e.encolar()).size());
        for (int v : e.encolar()) {
            assertTrue(v >= 1 && v <= 99, "fuera de rango: " + v);
        }
        Set<Integer> presentes = new HashSet<>(e.encolar());
        long estan = e.buscar().stream().filter(presentes::contains).count();
        assertEquals(6, e.buscar().size());
        assertEquals(3, estan);
        assertEquals(8, e.extracciones());
    }

    @Test
    @DisplayName("Se sortean los dos tipos de heap")
    void ambosTipos() {
        Set<Tipo> vistos = new HashSet<>();
        for (long s = 0; s < 30; s++) {
            vistos.add(GeneradorDeEjerciciosDeHeap.generar(s).tipo());
        }
        assertEquals(2, vistos.size());
    }

    @Test
    @DisplayName("La solución cumple el invariante del tipo sorteado en cada punto de control")
    void solucionCumpleInvariante() {
        for (long s = 0; s < 20; s++) {
            Ejercicio e = GeneradorDeEjerciciosDeHeap.generar(s);
            for (int n = 10; n <= 40; n += 10) {
                assertHeap(e.tipo(), GeneradorDeEjerciciosDeHeap.heapTrasEncolar(e, n), n);
            }
            List<Integer> extraidos = new ArrayList<>();
            List<Integer[]> estados = GeneradorDeEjerciciosDeHeap.heapsTrasExtraer(e, extraidos);
            assertEquals(8, estados.size());
            for (int i = 0; i < estados.size(); i++) {
                assertHeap(e.tipo(), estados.get(i), 40 - i - 1);
            }
            // lo extraído sale ordenado: ascendente en el de mínimo, descendente en el de máximo
            for (int i = 1; i < extraidos.size(); i++) {
                if (e.tipo() == Tipo.MINIMO) {
                    assertTrue(extraidos.get(i - 1) < extraidos.get(i));
                } else {
                    assertTrue(extraidos.get(i - 1) > extraidos.get(i));
                }
            }
        }
    }

    @Test
    @DisplayName("La búsqueda con poda acierta si está o no, y visita entre 1 y 40 nodos")
    void busquedaConPoda() {
        for (long s = 0; s < 20; s++) {
            Ejercicio e = GeneradorDeEjerciciosDeHeap.generar(s);
            Set<Integer> presentes = new HashSet<>(e.encolar());
            for (int v : e.buscar()) {
                Busqueda b = GeneradorDeEjerciciosDeHeap.buscar(e, v);
                assertEquals(presentes.contains(v), b.esta(), "valor " + v);
                assertTrue(b.nodosVisitados() >= 1 && b.nodosVisitados() <= 40);
            }
        }
    }

    private static void assertHeap(Tipo tipo, Integer[] a, int tamanioEsperado) {
        assertEquals(tamanioEsperado, a.length);
        for (int i = 1; i < a.length; i++) {
            int padre = (i - 1) / 2;
            if (tipo == Tipo.MINIMO) {
                assertTrue(a[padre] <= a[i], "min-heap roto en " + i);
            } else {
                assertTrue(a[padre] >= a[i], "max-heap roto en " + i);
            }
        }
    }
}
