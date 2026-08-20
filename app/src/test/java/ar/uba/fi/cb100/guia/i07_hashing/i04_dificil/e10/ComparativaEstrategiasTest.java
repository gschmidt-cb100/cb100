package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ComparativaEstrategiasTest {

    @Test
    @DisplayName("Ambas estrategias coinciden con claves que colisionan")
    void coincidenConColisiones() {
        // "juan", "eva" y "sol" caen en el mismo indice inicial:
        // el encadenamiento arma una cadena y el sondeo corre casilleros,
        // pero el comportamiento observable es identico.
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        assertTrue(comparativa.coinciden(new String[] { "juan", "eva", "sol" }));
    }

    @Test
    @DisplayName("Coinciden con un lote grande que fuerza rehash del sondeo")
    void coincidenConLoteGrande() {
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        String[] claves = new String[40];
        for (int i = 0; i < claves.length; i++) {
            claves[i] = "clave" + i;
        }
        assertTrue(comparativa.coinciden(claves));
    }

    @Test
    @DisplayName("Despues de borrar en ambas siguen coincidiendo")
    void coincidenDespuesDeBorrar() {
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        String[] claves = { "juan", "eva", "sol", "ana", "mia", "leo" };
        assertTrue(comparativa.coinciden(claves));
        // Borro claves en las DOS tablas, incluida una del medio del sondeo.
        comparativa.quitar("eva");
        comparativa.quitar("leo");
        assertTrue(comparativa.verificar(claves));
    }

    @Test
    @DisplayName("Borrar una clave inexistente no rompe la coincidencia")
    void borrarInexistente() {
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        String[] claves = { "ana", "mia" };
        assertTrue(comparativa.coinciden(claves));
        comparativa.quitar("noexiste");
        assertTrue(comparativa.verificar(claves));
    }

    @Test
    @DisplayName("Coinciden con claves repetidas (la ultima posicion gana)")
    void clavesRepetidas() {
        // "ana" aparece dos veces: en ambas tablas debe quedar UNA entrada
        // con el valor de la ultima insercion.
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        assertTrue(comparativa.coinciden(new String[] { "ana", "leo", "ana" }));
    }

    @Test
    @DisplayName("Borrar todas las claves deja ambas tablas vacias e iguales")
    void borrarTodo() {
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        String[] claves = { "juan", "eva", "sol" };
        assertTrue(comparativa.coinciden(claves));
        for (String clave : claves) {
            comparativa.quitar(clave);
        }
        assertTrue(comparativa.verificar(claves));
    }
}
