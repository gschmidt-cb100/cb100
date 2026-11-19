package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.tda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ejemplo de parcial 26/11 (fácil) - TDA Camion")
class CamionTest {

    private static final double EPSILON = 1e-9;

    private static final Paquete HELADERA = new Paquete("P-001", "Rosario", 60, false);
    private static final Paquete VAJILLA = new Paquete("P-002", "Córdoba", 15, true);
    private static final Paquete LIBROS = new Paquete("P-003", "Rosario", 20, false);
    private static final Paquete TELEVISOR = new Paquete("P-004", "Mendoza", 12, true);

    /** 4 lugares y 100 kg: heladera (60), vajilla (15) y libros (20) = 95 kg; queda libre el lugar 4. */
    private static Camion camionDeEjemplo() {
        Camion camion = new Camion(4, 100);
        camion.cargar(HELADERA);
        camion.cargar(VAJILLA);
        camion.cargar(LIBROS);
        return camion;
    }

    @Test
    @DisplayName("un paquete valida sus datos")
    void paqueteValida() {
        assertThrows(IllegalArgumentException.class, () -> new Paquete(null, "Rosario", 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Paquete(" ", "Rosario", 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Paquete("P-1", null, 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Paquete("P-1", "", 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Paquete("P-1", "Rosario", 0, false));
        assertThrows(IllegalArgumentException.class, () -> new Paquete("P-1", "Rosario", -5, false));
    }

    @Test
    @DisplayName("dos paquetes son iguales por código, sin importar destino, peso ni fragilidad")
    void igualdadDePaquetes() {
        assertEquals(HELADERA, new Paquete("P-001", "Salta", 3, true));
        assertEquals(HELADERA.hashCode(), new Paquete("P-001", "Salta", 3, true).hashCode());
        assertNotEquals(HELADERA, new Paquete("P-999", "Rosario", 60, false));
    }

    @Test
    @DisplayName("el destino de un paquete se compara sin distinguir mayúsculas ni espacios")
    void destinoDelPaquete() {
        assertTrue(HELADERA.vaA("rosario"));
        assertTrue(HELADERA.vaA("  ROSARIO "));
        assertFalse(HELADERA.vaA("Córdoba"));
        assertThrows(IllegalArgumentException.class, () -> HELADERA.vaA(null));
    }

    @Test
    @DisplayName("un camión valida sus datos")
    void camionValida() {
        assertThrows(IllegalArgumentException.class, () -> new Camion(0, 100));
        assertThrows(IllegalArgumentException.class, () -> new Camion(-1, 100));
        assertThrows(IllegalArgumentException.class, () -> new Camion(4, 0));
        assertThrows(IllegalArgumentException.class, () -> new Camion(4, -10));
    }

    @Test
    @DisplayName("un camión nace vacío: todos los lugares libres, sin peso y sin paquete más pesado")
    void camionVacio() {
        Camion camion = new Camion(3, 50);
        assertTrue(camion.estaVacio());
        assertFalse(camion.estaLleno());
        assertEquals(3, camion.getCantidadDeLugares());
        assertEquals(3, camion.getCantidadDeLugaresLibres());
        assertEquals(0, camion.getPesoTotal(), EPSILON);
        assertEquals(0, camion.getPorcentajeDeOcupacion(), EPSILON);
        assertEquals(0, camion.getCantidadDePaquetesFragiles());
        assertNull(camion.getPaqueteMasPesado());
        assertEquals(0, camion.buscarPaquetes("Rosario").length);
    }

    @Test
    @DisplayName("cargar ubica el paquete en el primer lugar libre y devuelve su número")
    void cargar() {
        Camion camion = new Camion(4, 100);
        assertEquals(1, camion.cargar(HELADERA));
        assertEquals(2, camion.cargar(VAJILLA));
        assertSame(HELADERA, camion.getPaquete(1));
        assertSame(VAJILLA, camion.getPaquete(2));
        assertNull(camion.getPaquete(3));
        assertEquals(2, camion.getCantidadDeLugaresLibres());
    }

    @Test
    @DisplayName("cargar reutiliza el lugar que dejó libre una descarga")
    void cargarEnElHueco() {
        Camion camion = camionDeEjemplo();
        camion.descargar("P-002");
        assertEquals(2, camion.cargar(TELEVISOR));
        assertSame(TELEVISOR, camion.getPaquete(2));
    }

    @Test
    @DisplayName("cargar rechaza null y un paquete que ya está cargado")
    void cargarInvalido() {
        Camion camion = camionDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> camion.cargar(null));
        assertThrows(IllegalArgumentException.class, () -> camion.cargar(HELADERA));
        assertThrows(IllegalArgumentException.class, () -> camion.cargar(new Paquete("P-001", "Salta", 1, false)));
        assertEquals(1, camion.getCantidadDeLugaresLibres());
    }

    @Test
    @DisplayName("no se puede cargar si se supera el peso máximo, aunque haya lugar")
    void cargarSuperaElPeso() {
        Camion camion = camionDeEjemplo();
        assertThrows(IllegalStateException.class, () -> camion.cargar(TELEVISOR));   // 95 + 12 > 100
        assertEquals(95, camion.getPesoTotal(), EPSILON);
        assertFalse(camion.contiene(TELEVISOR));
    }

    @Test
    @DisplayName("se puede cargar hasta llegar justo al peso máximo")
    void cargarJustoAlPesoMaximo() {
        Camion camion = camionDeEjemplo();
        camion.cargar(new Paquete("P-005", "Salta", 5, false));                       // 95 + 5 = 100
        assertEquals(100, camion.getPorcentajeDeOcupacion(), EPSILON);
    }

    @Test
    @DisplayName("no se puede cargar si el camión está lleno, aunque sobre peso")
    void cargarEnCamionLleno() {
        Camion camion = new Camion(1, 1000);
        camion.cargar(VAJILLA);
        assertTrue(camion.estaLleno());
        assertThrows(IllegalStateException.class, () -> camion.cargar(TELEVISOR));
    }

    @Test
    @DisplayName("descargar libera el lugar y devuelve el paquete")
    void descargar() {
        Camion camion = camionDeEjemplo();
        assertSame(VAJILLA, camion.descargar("P-002"));
        assertNull(camion.getPaquete(2));
        assertFalse(camion.contiene(VAJILLA));
        assertEquals(2, camion.getCantidadDeLugaresLibres());
        assertEquals(80, camion.getPesoTotal(), EPSILON);
    }

    @Test
    @DisplayName("descargar rechaza null y un código que no está cargado")
    void descargarInvalido() {
        Camion camion = camionDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> camion.descargar(null));
        assertThrows(IllegalArgumentException.class, () -> camion.descargar("P-999"));
        assertEquals(1, camion.getCantidadDeLugaresLibres());
    }

    @Test
    @DisplayName("el peso total y la ocupación se calculan sobre lo cargado")
    void pesoYOcupacion() {
        Camion camion = camionDeEjemplo();
        assertEquals(95, camion.getPesoTotal(), EPSILON);
        assertEquals(95, camion.getPorcentajeDeOcupacion(), EPSILON);

        Camion grande = new Camion(2, 400);
        grande.cargar(HELADERA);
        assertEquals(15, grande.getPorcentajeDeOcupacion(), EPSILON);                 // 60 de 400
    }

    @Test
    @DisplayName("el más pesado y la cantidad de frágiles")
    void masPesadoYFragiles() {
        Camion camion = camionDeEjemplo();
        assertSame(HELADERA, camion.getPaqueteMasPesado());
        assertEquals(1, camion.getCantidadDePaquetesFragiles());

        camion.descargar("P-001");
        assertSame(LIBROS, camion.getPaqueteMasPesado());
    }

    @Test
    @DisplayName("si hay empate en el más pesado gana el del lugar de menor número")
    void masPesadoConEmpate() {
        Camion camion = new Camion(3, 100);
        Paquete primero = new Paquete("A", "Salta", 20, false);
        Paquete segundo = new Paquete("B", "Salta", 20, false);
        camion.cargar(primero);
        camion.cargar(segundo);
        assertSame(primero, camion.getPaqueteMasPesado());
    }

    @Test
    @DisplayName("buscar por destino devuelve los paquetes en el orden de los lugares")
    void buscarPorDestino() {
        Camion camion = camionDeEjemplo();
        assertArrayEquals(new Paquete[] {HELADERA, LIBROS}, camion.buscarPaquetes("rosario"));
        assertArrayEquals(new Paquete[] {VAJILLA}, camion.buscarPaquetes("Córdoba"));
        assertEquals(0, camion.buscarPaquetes("Ushuaia").length);
        assertThrows(IllegalArgumentException.class, () -> camion.buscarPaquetes(null));
    }

    @Test
    @DisplayName("dos camiones tienen destino en común si algún paquete de cada uno va al mismo lugar")
    void destinoEnComun() {
        Camion camion = camionDeEjemplo();
        Camion otro = new Camion(2, 500);
        assertFalse(camion.tieneDestinoEnComun(otro));                                // el otro está vacío

        otro.cargar(new Paquete("P-100", "Salta", 200, false));
        assertFalse(camion.tieneDestinoEnComun(otro));
        assertFalse(otro.tieneDestinoEnComun(camion));

        otro.cargar(new Paquete("P-101", "córdoba", 30, false));
        assertTrue(camion.tieneDestinoEnComun(otro));
        assertTrue(otro.tieneDestinoEnComun(camion));

        assertThrows(IllegalArgumentException.class, () -> camion.tieneDestinoEnComun(null));
    }

    @Test
    @DisplayName("getPaquete valida el número de lugar")
    void getPaqueteInvalido() {
        Camion camion = camionDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> camion.getPaquete(0));
        assertThrows(IllegalArgumentException.class, () -> camion.getPaquete(5));
    }

    @Test
    @DisplayName("el vector que devuelve una búsqueda es una copia: modificarlo no afecta al camión")
    void laBusquedaDevuelveUnaCopia() {
        Camion camion = camionDeEjemplo();
        Paquete[] encontrados = camion.buscarPaquetes("Rosario");
        encontrados[0] = null;
        assertSame(HELADERA, camion.getPaquete(1));
        assertEquals(2, camion.buscarPaquetes("Rosario").length);
    }

    @Test
    @DisplayName("el toString muestra todos los lugares")
    void texto() {
        String texto = camionDeEjemplo().toString();
        assertTrue(texto.contains("P-001"));
        assertTrue(texto.contains("Lugar 4: libre"));
    }
}
