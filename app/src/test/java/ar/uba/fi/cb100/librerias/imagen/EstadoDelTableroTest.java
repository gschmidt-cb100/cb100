package ar.uba.fi.cb100.librerias.imagen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * El estado del tablero se prueba sin abrir ninguna ventana: es puro estado.
 */
class EstadoDelTableroTest {

    private static final String[] GRILLA = {
            "#####",
            "#..~#",
            "#####",
    };

    @Test
    @DisplayName("un tablero nuevo está en blanco y tiene sus dimensiones")
    void tableroNuevo() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);

        assertEquals(3, estado.getFilas());
        assertEquals(5, estado.getColumnas());
        assertEquals(' ', estado.getCelda(1, 1));
        assertEquals(' ', estado.getCelda(3, 5));
        assertEquals("", estado.getMensaje());
    }

    @Test
    @DisplayName("cargar una grilla deja cada símbolo en su celda, de 1 a n")
    void cargarGrilla() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);

        estado.cargar(GRILLA);

        assertEquals('#', estado.getCelda(1, 1));
        assertEquals('.', estado.getCelda(2, 2));
        assertEquals('~', estado.getCelda(2, 4));
        assertEquals('#', estado.getCelda(3, 5));
    }

    @Test
    @DisplayName("una grilla que no coincide con el tablero se rechaza")
    void grillaInvalida() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);

        assertThrows(IllegalArgumentException.class, () -> estado.cargar(new String[]{"#####", "#...#"}));
        assertThrows(IllegalArgumentException.class, () -> estado.cargar(new String[]{"#####", "#..#", "#####"}));
        assertThrows(IllegalArgumentException.class, () -> estado.cargar(new String[]{"#####", null, "#####"}));
        assertThrows(NullPointerException.class, () -> estado.cargar(null));
    }

    @Test
    @DisplayName("setCelda y getCelda validan el rango 1..n")
    void celdasFueraDeRango() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);

        estado.setCelda(2, 3, 'T');
        assertEquals('T', estado.getCelda(2, 3));

        assertThrows(IllegalArgumentException.class, () -> estado.getCelda(0, 1));
        assertThrows(IllegalArgumentException.class, () -> estado.getCelda(4, 1));
        assertThrows(IllegalArgumentException.class, () -> estado.setCelda(1, 6, '.'));
    }

    @Test
    @DisplayName("los símbolos habituales ya tienen apariencia; los raros, no")
    void apariencias() {
        EstadoDelTablero estado = new EstadoDelTablero(1, 1);

        assertNotNull(estado.aparienciaDe('#'));
        assertNotNull(estado.aparienciaDe('.'));
        assertNotNull(estado.aparienciaDe('~'));
        assertNull(estado.aparienciaDe('@'), "sin definir: se dibuja como desconocido");

        estado.definirApariencia('@', EstadoDelTablero.Apariencia.de(Color.PINK));
        assertEquals(Color.PINK, estado.aparienciaDe('@').color());
        assertNull(estado.aparienciaDe('@').imagen());
    }

    @Test
    @DisplayName("un personaje se agrega, se mueve y se quita")
    void personajes() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);
        estado.cargar(GRILLA);
        EstadoDelTablero.Apariencia azul = EstadoDelTablero.Apariencia.de(Color.BLUE);

        estado.agregarPersonaje("heroe", azul, 2, 2);
        assertTrue(estado.tienePersonaje("heroe"));
        assertEquals(2, estado.filaDe("heroe"));
        assertEquals(2, estado.columnaDe("heroe"));

        estado.moverPersonaje("heroe", 2, 4);
        assertEquals(4, estado.columnaDe("heroe"));
        assertEquals('.', estado.getCelda(2, 2), "moverse no cambia la celda de abajo");

        estado.quitarPersonaje("heroe");
        assertFalse(estado.tienePersonaje("heroe"));
    }

    @Test
    @DisplayName("un personaje repetido, desconocido o fuera del tablero se rechaza")
    void personajesInvalidos() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);
        EstadoDelTablero.Apariencia azul = EstadoDelTablero.Apariencia.de(Color.BLUE);
        estado.agregarPersonaje("heroe", azul, 2, 2);

        assertThrows(IllegalArgumentException.class, () -> estado.agregarPersonaje("heroe", azul, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> estado.agregarPersonaje("", azul, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> estado.agregarPersonaje("bicho", azul, 9, 9));
        assertThrows(IllegalArgumentException.class, () -> estado.moverPersonaje("heroe", 0, 0));

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> estado.moverPersonaje("fantasma", 1, 1));
        assertTrue(error.getMessage().contains("heroe"), "avisa cuáles hay: " + error.getMessage());
    }

    @Test
    @DisplayName("los personajes se recorren en el orden en que se agregaron")
    void ordenDeDibujo() {
        EstadoDelTablero estado = new EstadoDelTablero(3, 5);
        EstadoDelTablero.Apariencia a = EstadoDelTablero.Apariencia.de(Color.RED);
        estado.agregarPersonaje("zorro", a, 1, 1);
        estado.agregarPersonaje("abeja", a, 1, 2);

        StringBuilder nombres = new StringBuilder();
        for (EstadoDelTablero.Personaje p : estado.personajes()) {
            nombres.append(p.nombre).append(' ');
        }

        assertEquals("zorro abeja ", nombres.toString(), "el último agregado se dibuja encima");
    }

    @Test
    @DisplayName("el mensaje nulo se guarda como vacío")
    void mensaje() {
        EstadoDelTablero estado = new EstadoDelTablero(1, 1);

        estado.setMensaje("Energía 90");
        assertEquals("Energía 90", estado.getMensaje());
        estado.setMensaje(null);
        assertEquals("", estado.getMensaje());
    }

    @Test
    @DisplayName("un tablero sin filas o sin columnas no se puede crear")
    void dimensionesInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> new EstadoDelTablero(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new EstadoDelTablero(3, 0));
    }
}
