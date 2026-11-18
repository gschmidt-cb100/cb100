package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.tda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parcial 12/11 - TDA Recorrido")
class RecorridoTest {

    private static final double EPSILON = 1e-9;

    private static final Parada RETIRO = new Parada("Retiro", "Av. Ramos Mejía 1300", 0, 0);
    private static final Parada OBELISCO = new Parada("Obelisco", "Av. Corrientes 1000", 2.5, 10);
    private static final Parada CONGRESO = new Parada("Congreso", "Av. Rivadavia 1800", 4, 8);
    private static final Parada ONCE = new Parada("Once", "Av. Pueyrredón 100", 6.5, 12);

    /** Retiro (0 km) - Obelisco (2,5) - Once (6,5): 22 minutos. */
    private static Recorrido recorridoDeEjemplo() {
        Recorrido recorrido = new Recorrido("Línea 1");
        recorrido.agregarParada(RETIRO);
        recorrido.agregarParada(OBELISCO);
        recorrido.agregarParada(ONCE);
        return recorrido;
    }

    @Test
    @DisplayName("una parada valida sus datos")
    void paradaValida() {
        assertThrows(IllegalArgumentException.class, () -> new Parada(null, "x", 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Parada(" ", "x", 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Parada("x", null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Parada("x", "y", -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Parada("x", "y", 0, -1));
    }

    @Test
    @DisplayName("dos paradas son iguales por nombre y dirección, sin importar distancia ni tiempo")
    void igualdadDeParadas() {
        assertEquals(OBELISCO, new Parada("OBELISCO", "av. corrientes 1000", 9, 99));
        assertEquals(OBELISCO.hashCode(), new Parada("OBELISCO", "av. corrientes 1000", 9, 99).hashCode());
        assertNotEquals(OBELISCO, new Parada("Obelisco", "Otra dirección", 2.5, 10));
    }

    @Test
    @DisplayName("un recorrido nace vacío: sin distancia, sin duración y sin parada más lejana")
    void recorridoVacio() {
        Recorrido recorrido = new Recorrido("Vacío");
        assertTrue(recorrido.estaVacio());
        assertEquals(0, recorrido.getCantidadDeParadas());
        assertEquals(0, recorrido.obtenerDuracionTotal());
        assertEquals(0, recorrido.obtenerDistanciaTotal(), EPSILON);
        assertNull(recorrido.obtenerParadaMasLejana());
        assertEquals(0, recorrido.buscarParadas("Retiro").length);
    }

    @Test
    @DisplayName("agregar deja la parada al final")
    void agregar() {
        Recorrido recorrido = recorridoDeEjemplo();
        assertEquals(3, recorrido.getCantidadDeParadas());
        assertSame(RETIRO, recorrido.getParada(1));
        assertSame(ONCE, recorrido.getParada(3));
    }

    @Test
    @DisplayName("agregar rechaza null, una parada repetida y una más cercana que la última")
    void agregarInvalido() {
        Recorrido recorrido = recorridoDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> recorrido.agregarParada(null));
        assertThrows(IllegalArgumentException.class, () -> recorrido.agregarParada(OBELISCO));
        assertThrows(IllegalArgumentException.class, () -> recorrido.agregarParada(CONGRESO));
        assertEquals(3, recorrido.getCantidadDeParadas());
    }

    @Test
    @DisplayName("insertar corre las paradas siguientes")
    void insertar() {
        Recorrido recorrido = recorridoDeEjemplo();
        recorrido.insertarParada(3, CONGRESO);
        assertEquals(4, recorrido.getCantidadDeParadas());
        assertSame(OBELISCO, recorrido.getParada(2));
        assertSame(CONGRESO, recorrido.getParada(3));
        assertSame(ONCE, recorrido.getParada(4));
    }

    @Test
    @DisplayName("insertar rechaza una posición fuera de rango o que rompe el orden por distancia")
    void insertarInvalido() {
        Recorrido recorrido = recorridoDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> recorrido.insertarParada(0, CONGRESO));
        assertThrows(IllegalArgumentException.class, () -> recorrido.insertarParada(5, CONGRESO));
        assertThrows(IllegalArgumentException.class, () -> recorrido.insertarParada(2, CONGRESO));
        assertThrows(IllegalArgumentException.class, () -> recorrido.insertarParada(4, CONGRESO));
        assertEquals(3, recorrido.getCantidadDeParadas());
    }

    @Test
    @DisplayName("el vector crece: se pueden cargar más paradas que la capacidad inicial")
    void crece() {
        Recorrido recorrido = new Recorrido("Largo");
        for (int i = 1; i <= 50; i++) {
            recorrido.agregarParada(new Parada("Parada " + i, "Calle " + i, i, 2));
        }
        recorrido.insertarParada(1, new Parada("Cabecera", "Calle 0", 0, 0));
        assertEquals(51, recorrido.getCantidadDeParadas());
        assertEquals("Cabecera", recorrido.getParada(1).getNombre());
        assertEquals("Parada 50", recorrido.getParada(51).getNombre());
        assertEquals(100, recorrido.obtenerDuracionTotal());
    }

    @Test
    @DisplayName("eliminar devuelve la parada y corre las siguientes")
    void eliminar() {
        Recorrido recorrido = recorridoDeEjemplo();
        assertSame(OBELISCO, recorrido.eliminarParada(2));
        assertEquals(2, recorrido.getCantidadDeParadas());
        assertSame(ONCE, recorrido.getParada(2));
        assertFalse(recorrido.contiene(OBELISCO));
        assertThrows(IllegalArgumentException.class, () -> recorrido.eliminarParada(3));
        assertThrows(IllegalArgumentException.class, () -> recorrido.eliminarParada(0));
    }

    @Test
    @DisplayName("duración total, distancia total y parada más lejana")
    void totales() {
        Recorrido recorrido = recorridoDeEjemplo();
        assertEquals(22, recorrido.obtenerDuracionTotal());
        assertEquals(6.5, recorrido.obtenerDistanciaTotal(), EPSILON);
        assertSame(ONCE, recorrido.obtenerParadaMasLejana());
        recorrido.eliminarParada(3);
        assertEquals(10, recorrido.obtenerDuracionTotal());
        assertEquals(2.5, recorrido.obtenerDistanciaTotal(), EPSILON);
        assertSame(OBELISCO, recorrido.obtenerParadaMasLejana());
    }

    @Test
    @DisplayName("buscar por nombre no distingue mayúsculas y devuelve todas las coincidencias")
    void buscar() {
        Recorrido recorrido = recorridoDeEjemplo();
        Parada otroObelisco = new Parada("Obelisco", "Diagonal Norte 900", 3, 4);
        recorrido.insertarParada(3, otroObelisco);
        Parada[] encontradas = recorrido.buscarParadas(" obelisco ");
        assertArrayEquals(new Parada[] {OBELISCO, otroObelisco}, encontradas);
        assertEquals(0, recorrido.buscarParadas("Constitución").length);
        assertThrows(IllegalArgumentException.class, () -> recorrido.buscarParadas(null));
    }

    @Test
    @DisplayName("paradas en común entre dos recorridos")
    void enComun() {
        Recorrido uno = recorridoDeEjemplo();
        Recorrido otro = new Recorrido("Línea 2");
        otro.agregarParada(new Parada("Once", "Av. Pueyrredón 100", 0, 0));
        otro.agregarParada(new Parada("Abasto", "Av. Corrientes 3200", 1.5, 6));
        otro.agregarParada(new Parada("Obelisco", "Av. Corrientes 1000", 5, 15));

        assertTrue(uno.tieneParadaEnComun(otro));
        assertTrue(otro.tieneParadaEnComun(uno));
        assertArrayEquals(new Parada[] {OBELISCO, ONCE}, uno.obtenerParadasEnComun(otro));
        assertEquals(2, otro.obtenerParadasEnComun(uno).length);
        assertEquals("Once", otro.obtenerParadasEnComun(uno)[0].getNombre());
    }

    @Test
    @DisplayName("recorridos sin paradas en común, o vacíos")
    void sinParadasEnComun() {
        Recorrido uno = recorridoDeEjemplo();
        Recorrido otro = new Recorrido("Línea 3");
        assertFalse(uno.tieneParadaEnComun(otro));
        otro.agregarParada(CONGRESO);
        assertFalse(uno.tieneParadaEnComun(otro));
        assertEquals(0, uno.obtenerParadasEnComun(otro).length);
        assertThrows(IllegalArgumentException.class, () -> uno.tieneParadaEnComun(null));
        assertThrows(IllegalArgumentException.class, () -> uno.obtenerParadasEnComun(null));
    }
}
