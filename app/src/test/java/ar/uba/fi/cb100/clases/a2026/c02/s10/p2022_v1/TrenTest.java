package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tren v1: cargas polimórficas")
class TrenTest {

    private static final double EPSILON = 1e-9;

    @Nested
    @DisplayName("vagones cargables")
    class Vagones {

        @Test
        @DisplayName("cada vagón admite sólo sus tipos, y acepta si hay lugar y peso")
        void admiteYAcepta() {
            VagonDePasajeros coche = new VagonDePasajeros(20, 1);
            VagonDeCarga plataforma = new VagonDeCarga(15, 30, 2, TipoDeCarga.CONTENEDOR);

            assertTrue(coche.aceptaCarga(new Pasajero("Ana")));
            assertFalse(coche.aceptaCarga(new Contenedor("C1", 1)));
            assertTrue(plataforma.aceptaCarga(new Contenedor("C1", 30)));
            assertFalse(plataforma.aceptaCarga(new Contenedor("C2", 30.5)), "se pasa de peso");
            assertFalse(plataforma.aceptaCarga(new Granel("trigo", 1)), "no admite GRANEL");

            coche.agregarCarga(new Pasajero("Ana"));
            assertTrue(coche.estaLleno());
            assertFalse(coche.aceptaCarga(new Pasajero("Bruno")));
        }

        @Test
        @DisplayName("agregar y quitar mantienen cantidad y peso; quitar corre las siguientes")
        void agregarYQuitar() {
            VagonDeCarga mixto = new VagonDeCarga(18, 50, 3, TipoDeCarga.CONTENEDOR, TipoDeCarga.GRANEL);
            Carga c1 = new Contenedor("C1", 10);
            Carga trigo = new Granel("trigo", 15);
            Carga c2 = new Contenedor("C2", 5);
            mixto.agregarCarga(c1);
            mixto.agregarCarga(trigo);
            mixto.agregarCarga(c2);
            assertEquals(3, mixto.getCantidadDeCargas());
            assertEquals(30, mixto.getPesoDeCarga(), EPSILON);
            assertTrue(mixto.estaLleno(), "por cantidad de bultos");

            mixto.quitarCarga(trigo);
            assertFalse(mixto.contiene(trigo));
            assertSame(c2, mixto.getCarga(2), "c2 se corrió a la posición 2");
            assertEquals(15, mixto.getPesoDeCarga(), EPSILON);
            assertEquals(35, mixto.getPesoDisponible(), EPSILON);
            assertThrows(IllegalArgumentException.class, () -> mixto.quitarCarga(trigo));
        }

        @Test
        @DisplayName("agregar a la fuerza lo que no se acepta es un error y no cambia nada")
        void agregarInvalido() {
            VagonDeCarga tolva = new VagonDeCarga(12, 10, 1, TipoDeCarga.GRANEL);
            assertThrows(IllegalArgumentException.class, () -> tolva.agregarCarga(new Pasajero("Ana")));
            assertThrows(IllegalArgumentException.class, () -> tolva.agregarCarga(new Granel("arena", 11)));
            tolva.agregarCarga(new Granel("arena", 10));
            assertThrows(IllegalStateException.class, () -> tolva.agregarCarga(new Granel("sal", 1)));
            assertEquals(1, tolva.getCantidadDeCargas());
        }

        @Test
        @DisplayName("los constructores validan")
        void constructores() {
            assertThrows(IllegalArgumentException.class, () -> new VagonDePasajeros(0, 10));
            assertThrows(IllegalArgumentException.class, () -> new VagonDePasajeros(20, 0));
            assertThrows(IllegalArgumentException.class, () -> new VagonDeCarga(15, 30, 2));
            assertThrows(IllegalArgumentException.class, () -> new VagonDeCarga(15, 30, 2, TipoDeCarga.PASAJERO));
            assertThrows(IllegalArgumentException.class, () -> new VagonDeCarga(15, -30, 2, TipoDeCarga.GRANEL));
            assertThrows(IllegalArgumentException.class, () -> new Contenedor("", 1));
            assertThrows(IllegalArgumentException.class, () -> new Granel("trigo", 0));
        }
    }

    @Nested
    @DisplayName("el tren")
    class ElTren {

        private Tren trenDeEjemplo() {
            Tren tren = new Tren(4);
            tren.agregarVagon(new VagonDePasajeros(20, 2));
            tren.agregarVagon(new VagonComedor(22));
            tren.agregarVagon(new VagonDeCarga(15, 30, 3, TipoDeCarga.CONTENEDOR));
            tren.agregarVagon(new VagonDeCarga(12, 40, 1, TipoDeCarga.GRANEL));
            return tren;
        }

        @Test
        @DisplayName("carga cualquier Carga con el mismo método y la pone en el vagón que la admite")
        void cargarPolimorfico() {
            Tren tren = trenDeEjemplo();
            assertEquals(1, tren.cargar(new Pasajero("Ana")));
            assertEquals(3, tren.cargar(new Contenedor("C1", 12.5)));
            assertEquals(4, tren.cargar(new Granel("trigo", 18)));
            assertEquals(3, tren.getCantidadDeCargas());
            assertEquals(12.5 + 18 + Pasajero.PESO_PROMEDIO, tren.getPesoDeCarga(), EPSILON);
            assertEquals(69, tren.getLongitudTotal(), EPSILON);
        }

        @Test
        @DisplayName("si ningún vagón acepta la carga, es un error de estado y no sube")
        void nadieAcepta() {
            Tren tren = trenDeEjemplo();
            tren.cargar(new Pasajero("Ana"));
            tren.cargar(new Pasajero("Bruno"));
            assertThrows(IllegalStateException.class, () -> tren.cargar(new Pasajero("Carla")));
            assertThrows(IllegalStateException.class, () -> tren.cargar(new Contenedor("C9", 31)));
            assertThrows(IllegalStateException.class, () -> tren.cargar(new Granel("sal", 41)), "la tolva admite 40 t");
            assertEquals(2, tren.getCantidadDeCargas(), "sólo subieron Ana y Bruno");
            assertThrows(IllegalArgumentException.class, () -> tren.cargar(null));
        }

        @Test
        @DisplayName("capacidades restantes por tipo: lugares para pasajeros, toneladas para carga")
        void capacidadesPorTipo() {
            Tren tren = trenDeEjemplo();
            assertEquals(2, tren.getLugaresLibresPara(TipoDeCarga.PASAJERO));
            assertEquals(30, tren.getPesoDisponiblePara(TipoDeCarga.CONTENEDOR), EPSILON);
            assertEquals(40, tren.getPesoDisponiblePara(TipoDeCarga.GRANEL), EPSILON);
            assertEquals(0, tren.getPesoDisponiblePara(TipoDeCarga.GANADO), EPSILON, "nadie admite ganado");
            tren.cargar(new Pasajero("Ana"));
            tren.cargar(new Contenedor("C1", 12.5));
            assertEquals(1, tren.getLugaresLibresPara(TipoDeCarga.PASAJERO));
            assertEquals(17.5, tren.getPesoDisponiblePara(TipoDeCarga.CONTENEDOR), EPSILON);
        }

        @Test
        @DisplayName("descargar busca el vagón que tiene la carga; lo que no está es un error")
        void descargar() {
            Tren tren = trenDeEjemplo();
            Carga c1 = new Contenedor("C1", 12.5);
            tren.cargar(c1);
            assertTrue(tren.contiene(c1));
            assertTrue(tren.contiene(new Contenedor("C1", 12.5)), "igualdad por contenido: es un record");
            assertEquals(3, tren.descargar(c1));
            assertFalse(tren.contiene(c1));
            assertThrows(IllegalArgumentException.class, () -> tren.descargar(c1));
            assertEquals(0, tren.getPesoDeCarga(), EPSILON);
        }

        @Test
        @DisplayName("retirar vagones: el comedor siempre, los cargables sólo vacíos; los demás se corren")
        void retirar() {
            Tren tren = trenDeEjemplo();
            Carga trigo = new Granel("trigo", 18);
            tren.cargar(trigo);
            assertThrows(IllegalStateException.class, () -> tren.retirarVagon(4));
            Vagon comedor = tren.retirarVagon(2);
            assertTrue(comedor instanceof VagonComedor);
            assertEquals(3, tren.getCantidadDeVagones());
            assertEquals(3, tren.descargar(trigo), "la tolva se corrió a la posición 3");
            assertThrows(IllegalArgumentException.class, () -> tren.retirarVagon(4));
            assertThrows(IllegalArgumentException.class, () -> tren.getVagon(0));
        }

        @Test
        @DisplayName("agregar: null, repetido y tren completo se rechazan")
        void agregarInvalido() {
            Tren tren = new Tren(1);
            Vagon coche = new VagonDePasajeros(20, 2);
            tren.agregarVagon(coche);
            assertThrows(IllegalArgumentException.class, () -> tren.agregarVagon(null));
            assertThrows(IllegalArgumentException.class, () -> tren.agregarVagon(coche));
            assertThrows(IllegalStateException.class, () -> tren.agregarVagon(new VagonComedor(10)));
            assertTrue(tren.estaCompleto());
            assertThrows(IllegalArgumentException.class, () -> new Tren(0));
        }
    }
}
