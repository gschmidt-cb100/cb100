package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tren")
class TrenTest {

    private static final double EPSILON = 1e-9;

    /** Un tren de 3 vagones: 60 + 40 pasajeros y 30 t de carga, 55 m. */
    private static Tren trenDeEjemplo() {
        Tren tren = new Tren(4);
        tren.agregarVagon(new VagonDePasajeros(20, 60));
        tren.agregarVagon(new VagonDePasajeros(20, 40));
        tren.agregarVagon(new VagonDeCarga(15, 30));
        return tren;
    }

    @Nested
    @DisplayName("vagones")
    class Vagones {

        @Test
        @DisplayName("un vagón de pasajeros nace vacío y no lleva carga; uno de carga, al revés")
        void vagonesNacenVacios() {
            Vagon pasajeros = new VagonDePasajeros(20, 60);
            assertEquals(60, pasajeros.getCapacidadDePasajeros());
            assertEquals(0, pasajeros.getPasajeros());
            assertEquals(0, pasajeros.getCapacidadDeCarga());
            assertTrue(pasajeros.estaVacio());

            Vagon carga = new VagonDeCarga(15, 30);
            assertEquals(30, carga.getCapacidadDeCarga(), EPSILON);
            assertEquals(0, carga.getCapacidadDePasajeros());
            assertEquals(0, carga.subirPasajeros(10), "no sube nadie a un vagón de carga");
        }

        @Test
        @DisplayName("los constructores validan longitud y capacidades")
        void validaciones() {
            assertThrows(IllegalArgumentException.class, () -> new VagonDePasajeros(0, 60));
            assertThrows(IllegalArgumentException.class, () -> new VagonDePasajeros(20, 0));
            assertThrows(IllegalArgumentException.class, () -> new VagonDeCarga(-1, 30));
            assertThrows(IllegalArgumentException.class, () -> new VagonDeCarga(15, -5));
        }

        @Test
        @DisplayName("subir y cargar devuelven lo que entró, sin pasarse de la capacidad")
        void subirYCargarAcotados() {
            VagonDePasajeros vagon = new VagonDePasajeros(20, 10);
            assertEquals(10, vagon.subirPasajeros(25));
            assertEquals(10, vagon.getPasajeros());
            assertEquals(4, vagon.bajarPasajeros(4));
            assertEquals(6, vagon.bajarPasajeros(100), "baja lo que hay");

            VagonDeCarga carga = new VagonDeCarga(15, 30);
            assertEquals(30, carga.cargar(50), EPSILON);
            assertEquals(12.5, carga.descargar(12.5), EPSILON);
            assertEquals(17.5, carga.getCarga(), EPSILON);
        }
    }

    @Nested
    @DisplayName("armar el tren")
    class Armar {

        @Test
        @DisplayName("nace vacío con la capacidad máxima del constructor")
        void naceVacio() {
            Tren tren = new Tren(3);
            assertEquals(0, tren.getCantidadDeVagones());
            assertEquals(3, tren.getCantidadMaximaDeVagones());
            assertEquals(0, tren.getLongitudTotal(), EPSILON);
            assertThrows(IllegalArgumentException.class, () -> new Tren(0));
        }

        @Test
        @DisplayName("agregar engancha al final y las posiciones van de 1 a n")
        void agregarAlFinal() {
            Tren tren = trenDeEjemplo();
            assertEquals(3, tren.getCantidadDeVagones());
            assertEquals(60, tren.getVagon(1).getCapacidadDePasajeros());
            assertEquals(30, tren.getVagon(3).getCapacidadDeCarga(), EPSILON);
            assertThrows(IllegalArgumentException.class, () -> tren.getVagon(0));
            assertThrows(IllegalArgumentException.class, () -> tren.getVagon(4));
        }

        @Test
        @DisplayName("no se puede agregar null, ni el mismo vagón dos veces, ni más del máximo")
        void agregarInvalido() {
            Tren tren = new Tren(2);
            Vagon vagon = new VagonDeCarga(15, 30);
            tren.agregarVagon(vagon);
            assertThrows(IllegalArgumentException.class, () -> tren.agregarVagon(null));
            assertThrows(IllegalArgumentException.class, () -> tren.agregarVagon(vagon));
            tren.agregarVagon(new VagonDePasajeros(20, 60));
            assertThrows(IllegalStateException.class, () -> tren.agregarVagon(new VagonDePasajeros(20, 60)));
            assertEquals(2, tren.getCantidadDeVagones());
        }

        @Test
        @DisplayName("retirar devuelve el vagón y corre los siguientes un lugar")
        void retirar() {
            Tren tren = trenDeEjemplo();
            Vagon segundo = tren.getVagon(2);
            Vagon tercero = tren.getVagon(3);
            Vagon retirado = tren.retirarVagon(2);
            assertSame(segundo, retirado);
            assertEquals(2, tren.getCantidadDeVagones());
            assertSame(tercero, tren.getVagon(2), "el de carga se corrió a la posición 2");
            assertEquals(35, tren.getLongitudTotal(), EPSILON);
            assertThrows(IllegalArgumentException.class, () -> tren.retirarVagon(3));
        }

        @Test
        @DisplayName("no se puede retirar un vagón con pasajeros o carga")
        void retirarOcupado() {
            Tren tren = trenDeEjemplo();
            tren.subirPasajeros(10);
            tren.cargar(5);
            assertThrows(IllegalStateException.class, () -> tren.retirarVagon(1));
            assertThrows(IllegalStateException.class, () -> tren.retirarVagon(3));
            tren.retirarVagon(2);                      // el segundo sigue vacío
            assertEquals(2, tren.getCantidadDeVagones());
        }
    }

    @Nested
    @DisplayName("pasajeros y carga")
    class PasajerosYCarga {

        @Test
        @DisplayName("totales y restantes se calculan sobre los vagones")
        void totales() {
            Tren tren = trenDeEjemplo();
            assertEquals(55, tren.getLongitudTotal(), EPSILON);
            assertEquals(100, tren.getCapacidadDePasajeros());
            assertEquals(100, tren.getCapacidadRestanteDePasajeros());
            assertEquals(30, tren.getCapacidadDeCarga(), EPSILON);
            assertEquals(30, tren.getCapacidadRestanteDeCarga(), EPSILON);
        }

        @Test
        @DisplayName("subir reparte de adelante hacia atrás; bajar vacía de atrás hacia adelante")
        void subirYBajar() {
            Tren tren = trenDeEjemplo();
            tren.subirPasajeros(75);
            assertEquals(60, tren.getVagon(1).getPasajeros());
            assertEquals(15, tren.getVagon(2).getPasajeros());
            assertEquals(25, tren.getCapacidadRestanteDePasajeros());

            tren.bajarPasajeros(20);
            assertEquals(0, tren.getVagon(2).getPasajeros());
            assertEquals(55, tren.getVagon(1).getPasajeros());
            assertEquals(55, tren.getPasajeros());
        }

        @Test
        @DisplayName("cargar y descargar funcionan igual con toneladas")
        void cargarYDescargar() {
            Tren tren = trenDeEjemplo();
            tren.agregarVagon(new VagonDeCarga(15, 20));
            tren.cargar(42.5);
            assertEquals(30, tren.getVagon(3).getCarga(), EPSILON);
            assertEquals(12.5, tren.getVagon(4).getCarga(), EPSILON);
            assertEquals(7.5, tren.getCapacidadRestanteDeCarga(), EPSILON);
            tren.descargar(20);
            assertEquals(0, tren.getVagon(4).getCarga(), EPSILON);
            assertEquals(22.5, tren.getVagon(3).getCarga(), EPSILON);
        }

        @Test
        @DisplayName("todo o nada: si no entra, no sube nadie; si no hay, no baja nadie")
        void todoONada() {
            Tren tren = trenDeEjemplo();
            assertThrows(IllegalArgumentException.class, () -> tren.subirPasajeros(101));
            assertEquals(0, tren.getPasajeros(), "no subió nadie");
            assertThrows(IllegalArgumentException.class, () -> tren.bajarPasajeros(1));
            assertThrows(IllegalArgumentException.class, () -> tren.cargar(30.5));
            assertEquals(0, tren.getCarga(), EPSILON);
            assertThrows(IllegalArgumentException.class, () -> tren.descargar(1));
            assertThrows(IllegalArgumentException.class, () -> tren.subirPasajeros(0));
            assertThrows(IllegalArgumentException.class, () -> tren.cargar(-3));
        }

        @Test
        @DisplayName("un tren sin vagones de carga no acepta carga, y sin vagones de pasajeros no acepta pasajeros")
        void sinVagonesDelTipo() {
            Tren soloCarga = new Tren(2);
            soloCarga.agregarVagon(new VagonDeCarga(15, 30));
            assertThrows(IllegalArgumentException.class, () -> soloCarga.subirPasajeros(1));
            soloCarga.cargar(30);
            assertEquals(0, soloCarga.getCapacidadRestanteDeCarga(), EPSILON);
        }
    }
}
