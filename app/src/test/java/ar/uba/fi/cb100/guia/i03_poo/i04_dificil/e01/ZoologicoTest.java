package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZoologicoTest {

    @Test
    @DisplayName("ingresar suma animales y cantidadDeAnimales los cuenta")
    void ingresarYContar() {
        Zoologico zoologico = new Zoologico(3);
        assertEquals(0, zoologico.cantidadDeAnimales());

        zoologico.ingresar(new Animal("Clemente", "Elefante", 4200));
        zoologico.ingresar(new Animal("Rita", "Jirafa", 900));
        assertEquals(2, zoologico.cantidadDeAnimales());
    }

    @Test
    @DisplayName("buscarPorNombre devuelve el animal, o null si no está")
    void buscarPorNombre() {
        Zoologico zoologico = new Zoologico(3);
        Animal rita = new Animal("Rita", "Jirafa", 900);
        zoologico.ingresar(rita);

        assertEquals(rita, zoologico.buscarPorNombre("Rita"));
        assertNull(zoologico.buscarPorNombre("Dumbo"));
    }

    @Test
    @DisplayName("elMasPesado devuelve null en un zoológico vacío y el máximo con animales")
    void elMasPesado() {
        Zoologico zoologico = new Zoologico(3);
        assertNull(zoologico.elMasPesado());

        zoologico.ingresar(new Animal("Simba", "Leon", 190));
        zoologico.ingresar(new Animal("Clemente", "Elefante", 4200));
        zoologico.ingresar(new Animal("Rita", "Jirafa", 900));
        assertEquals("Clemente", zoologico.elMasPesado().nombre());
    }

    @Test
    @DisplayName("pesoTotal se calcula recorriendo (0 si está vacío)")
    void pesoTotal() {
        Zoologico zoologico = new Zoologico(3);
        assertEquals(0.0, zoologico.pesoTotal());

        zoologico.ingresar(new Animal("Simba", "Leon", 190.5));
        zoologico.ingresar(new Animal("Rita", "Jirafa", 900.0));
        assertEquals(1090.5, zoologico.pesoTotal());
    }

    @Test
    @DisplayName("ingresar lanza si está lleno o si el nombre está duplicado")
    void ingresarInvalido() {
        Zoologico zoologico = new Zoologico(2);
        zoologico.ingresar(new Animal("Simba", "Leon", 190));
        assertThrows(IllegalStateException.class,
                () -> zoologico.ingresar(new Animal("Simba", "Tigre", 220)),
                "no puede haber dos animales con el mismo nombre");

        zoologico.ingresar(new Animal("Rita", "Jirafa", 900));
        assertThrows(IllegalStateException.class,
                () -> zoologico.ingresar(new Animal("Clemente", "Elefante", 4200)),
                "está lleno");
        assertThrows(IllegalArgumentException.class, () -> zoologico.ingresar(null));
    }

    @Test
    @DisplayName("los parámetros inválidos de Animal y Zoologico lanzan excepciones")
    void construccionInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Zoologico(0));
        assertThrows(IllegalArgumentException.class, () -> new Zoologico(-3));
        assertThrows(IllegalArgumentException.class, () -> new Animal(null, "Leon", 190));
        assertThrows(IllegalArgumentException.class, () -> new Animal("  ", "Leon", 190));
        assertThrows(IllegalArgumentException.class, () -> new Animal("Simba", "", 190));
        assertThrows(IllegalArgumentException.class, () -> new Animal("Simba", "Leon", 0));
        assertThrows(IllegalArgumentException.class, () -> new Animal("Simba", "Leon", -5));

        Zoologico zoologico = new Zoologico(1);
        assertThrows(IllegalArgumentException.class, () -> zoologico.buscarPorNombre(null));
        assertThrows(IllegalArgumentException.class, () -> zoologico.buscarPorNombre(" "));
    }

    @Test
    @DisplayName("toString muestra todos los animales")
    void toStringMuestraTodo() {
        Zoologico zoologico = new Zoologico(2);
        zoologico.ingresar(new Animal("Simba", "Leon", 190));
        zoologico.ingresar(new Animal("Rita", "Jirafa", 900));
        String texto = zoologico.toString();
        assertTrue(texto.contains("Simba"));
        assertTrue(texto.contains("Rita"));
        assertTrue(texto.contains("Jirafa"));
    }
}
