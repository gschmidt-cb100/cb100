package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e09;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MultiMapaTest {

    @Test
    @DisplayName("Una clave acumula varios valores y tamanio cuenta pares")
    void agregarVariosValores() {
        MultiMapa<String, String> biblioteca = new MultiMapa<>();
        biblioteca.agregar("borges", "Ficciones");
        biblioteca.agregar("borges", "El Aleph");
        biblioteca.agregar("cortazar", "Rayuela");
        assertEquals(3, biblioteca.tamanio());
        assertEquals(List.of("Ficciones", "El Aleph"), biblioteca.valoresDe("borges"));
        assertEquals(List.of("Rayuela"), biblioteca.valoresDe("cortazar"));
    }

    @Test
    @DisplayName("valoresDe una clave ausente devuelve lista vacia")
    void claveAusente() {
        MultiMapa<String, String> biblioteca = new MultiMapa<>();
        biblioteca.agregar("borges", "Ficciones");
        assertTrue(biblioteca.valoresDe("quiroga").isEmpty());
    }

    @Test
    @DisplayName("Quitar saca una sola aparicion del par")
    void quitarUnaAparicion() {
        MultiMapa<String, Integer> notas = new MultiMapa<>();
        notas.agregar("ana", 7);
        notas.agregar("ana", 9);
        notas.agregar("ana", 7);
        assertTrue(notas.quitar("ana", 7));
        assertEquals(2, notas.tamanio());
        assertEquals(List.of(9, 7), notas.valoresDe("ana"));
    }

    @Test
    @DisplayName("Quitar un par inexistente devuelve false")
    void quitarInexistente() {
        MultiMapa<String, Integer> notas = new MultiMapa<>();
        notas.agregar("ana", 7);
        assertFalse(notas.quitar("ana", 10));
        assertFalse(notas.quitar("leo", 7));
        assertEquals(1, notas.tamanio());
    }

    @Test
    @DisplayName("Una clave que se queda sin valores desaparece de la tabla")
    void claveQueSeVacia() {
        MultiMapa<String, String> biblioteca = new MultiMapa<>();
        biblioteca.agregar("cortazar", "Rayuela");
        assertTrue(biblioteca.quitar("cortazar", "Rayuela"));
        assertEquals(0, biblioteca.tamanio());
        assertTrue(biblioteca.valoresDe("cortazar").isEmpty());
    }

    @Test
    @DisplayName("Modificar la lista devuelta no afecta al multi-mapa")
    void copiaDefensiva() {
        MultiMapa<String, String> biblioteca = new MultiMapa<>();
        biblioteca.agregar("borges", "Ficciones");
        List<String> lista = biblioteca.valoresDe("borges");
        lista.clear();
        assertEquals(List.of("Ficciones"), biblioteca.valoresDe("borges"));
        assertEquals(1, biblioteca.tamanio());
    }

    @Test
    @DisplayName("Con muchas claves rehashea y conserva todos los pares")
    void creceSinPerderPares() {
        MultiMapa<String, Integer> mapa = new MultiMapa<>();
        for (int i = 0; i < 30; i++) {
            mapa.agregar("clave" + i, i);
            mapa.agregar("clave" + i, i * 10);
        }
        assertEquals(60, mapa.tamanio());
        for (int i = 0; i < 30; i++) {
            assertEquals(List.of(i, i * 10), mapa.valoresDe("clave" + i));
        }
    }
}
