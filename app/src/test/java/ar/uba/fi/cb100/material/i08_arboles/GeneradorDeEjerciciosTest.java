package ar.uba.fi.cb100.material.i08_arboles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneradorDeEjerciciosTest {

    @Test
    @DisplayName("La misma semilla reproduce exactamente el mismo ejercicio")
    void reproducible() {
        var a = GeneradorDeEjercicios.generar(104523L);
        var b = GeneradorDeEjercicios.generar(104523L);
        assertEquals(a, b);   // record: equals por valor, campo a campo
    }

    @Test
    @DisplayName("Semillas distintas dan ejercicios distintos")
    void distintasSemillas() {
        var a = GeneradorDeEjercicios.generar(1L);
        var b = GeneradorDeEjercicios.generar(2L);
        assertFalse(a.insertar().equals(b.insertar()));
    }

    @Test
    @DisplayName("Son 40 números, distintos, entre 1 y 99")
    void cuarentaDistintosEnRango() {
        var e = GeneradorDeEjercicios.generar(77L);
        assertEquals(40, e.insertar().size());
        Set<Integer> unicos = new HashSet<>(e.insertar());
        assertEquals(40, unicos.size());
        for (int v : e.insertar()) {
            assertTrue(v >= 1 && v <= 99, "fuera de rango: " + v);
        }
    }

    @Test
    @DisplayName("Las eliminaciones son valores insertados, sin repetir")
    void eliminacionesPresentes() {
        var e = GeneradorDeEjercicios.generar(2024L);
        Set<Integer> insertados = new HashSet<>(e.insertar());
        Set<Integer> aEliminar = new HashSet<>(e.eliminar());
        assertEquals(8, e.eliminar().size());
        assertEquals(8, aEliminar.size());
        assertTrue(insertados.containsAll(aEliminar));
    }

    @Test
    @DisplayName("Las búsquedas mezclan presentes y ausentes")
    void busquedasMixtas() {
        var e = GeneradorDeEjercicios.generar(31416L);
        Set<Integer> insertados = new HashSet<>(e.insertar());
        long presentes = e.buscar().stream().filter(insertados::contains).count();
        assertEquals(6, e.buscar().size());
        assertEquals(3, presentes);
    }

    @Test
    @DisplayName("El tipo sorteado trae su simulador correspondiente")
    void tipoConSimulador() {
        for (long s = 0; s < 30; s++) {
            var e = GeneradorDeEjercicios.generar(s);
            assertTrue(e.simulador().startsWith("https://cmps-people.ok.ubc.ca/ylucet/DS/"));
            assertTrue(e.tipoDeArbol().length() > 2);
        }
    }
}
