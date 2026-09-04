package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumDeFotosTest {

    @Test
    @DisplayName("agregar y quitarUltima se comportan como una secuencia LIFO")
    void agregarYQuitar() {
        AlbumDeFotos album = new AlbumDeFotos(3);
        album.agregar("playa.jpg");
        album.agregar("montania.jpg");
        assertEquals(2, album.cantidad());
        assertEquals("playa.jpg", album.fotoEn(0));
        assertEquals("montania.jpg", album.fotoEn(1));

        assertEquals("montania.jpg", album.quitarUltima());
        assertEquals(1, album.cantidad());
        assertEquals("playa.jpg", album.quitarUltima());
        assertEquals(0, album.cantidad());
    }

    @Test
    @DisplayName("quitarUltima pone null en la posición interna liberada (no hay fuga)")
    void quitarNoRetieneLaReferencia() {
        AlbumDeFotos album = new AlbumDeFotos(3);
        album.agregar("playa.jpg");
        album.agregar("montania.jpg");
        assertTrue(album.retieneReferenciaEn(0));
        assertTrue(album.retieneReferenciaEn(1));
        assertFalse(album.retieneReferenciaEn(2), "posición nunca usada");

        album.quitarUltima();
        assertFalse(album.retieneReferenciaEn(1),
                "la posición liberada debe quedar en null para que el GC pueda levantar la foto");
        assertTrue(album.retieneReferenciaEn(0), "la foto que sigue en el álbum sí se retiene");

        album.quitarUltima();
        assertFalse(album.retieneReferenciaEn(0));
    }

    @Test
    @DisplayName("volver a agregar después de quitar reutiliza la posición liberada")
    void reutilizaPosiciones() {
        AlbumDeFotos album = new AlbumDeFotos(2);
        album.agregar("vieja.jpg");
        album.quitarUltima();
        album.agregar("nueva.jpg");
        assertEquals(1, album.cantidad());
        assertEquals("nueva.jpg", album.fotoEn(0));
        assertTrue(album.retieneReferenciaEn(0));
        assertFalse(album.retieneReferenciaEn(1));
    }

    @Test
    @DisplayName("los casos inválidos lanzan excepciones claras")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> new AlbumDeFotos(0));
        assertThrows(IllegalArgumentException.class, () -> new AlbumDeFotos(-1));

        AlbumDeFotos album = new AlbumDeFotos(1);
        assertThrows(IllegalStateException.class, album::quitarUltima, "álbum vacío");
        assertThrows(IllegalArgumentException.class, () -> album.agregar(null));
        assertThrows(IllegalArgumentException.class, () -> album.fotoEn(0),
                "fotoEn valida contra cantidad(), no contra la capacidad");

        album.agregar("unica.jpg");
        assertThrows(IllegalStateException.class, () -> album.agregar("otra.jpg"), "álbum lleno");
        assertThrows(IllegalArgumentException.class, () -> album.fotoEn(1));
        assertThrows(IllegalArgumentException.class, () -> album.fotoEn(-1));
        assertThrows(IllegalArgumentException.class, () -> album.retieneReferenciaEn(5));
    }
}
