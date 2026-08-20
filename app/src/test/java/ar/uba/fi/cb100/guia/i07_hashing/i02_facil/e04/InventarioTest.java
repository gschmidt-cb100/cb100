package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    @Test
    @DisplayName("agregar acumula el stock del mismo producto")
    void agregarAcumula() {
        Inventario inv = new Inventario();
        inv.agregarStock("yerba", 10);
        inv.agregarStock("yerba", 5);
        assertEquals(15, inv.stockDe("yerba"));
    }

    @Test
    @DisplayName("un producto que nunca se cargo tiene stock 0")
    void productoInexistenteEsCero() {
        assertEquals(0, new Inventario().stockDe("fideos"));
    }

    @Test
    @DisplayName("quitar descuenta del stock")
    void quitarDescuenta() {
        Inventario inv = new Inventario();
        inv.agregarStock("azucar", 8);
        inv.quitarStock("azucar", 3);
        assertEquals(5, inv.stockDe("azucar"));
    }

    @Test
    @DisplayName("quitar mas de lo que hay lanza IllegalArgumentException")
    void quitarDeMasFalla() {
        Inventario inv = new Inventario();
        inv.agregarStock("cafe", 2);
        assertThrows(IllegalArgumentException.class, () -> inv.quitarStock("cafe", 5));
        // El stock no debe haber cambiado.
        assertEquals(2, inv.stockDe("cafe"));
    }

    @Test
    @DisplayName("quitar de un producto inexistente lanza IllegalArgumentException")
    void quitarInexistenteFalla() {
        assertThrows(IllegalArgumentException.class,
                () -> new Inventario().quitarStock("fantasma", 1));
    }
}
