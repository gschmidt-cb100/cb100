package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.listas;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ejemplo de parcial 26/11 (fácil) - buscarSeriesMejorPuntuadas")
class ServicioDeStreamingTest {

    private static final String CIENCIA_FICCION = "Ciencia ficción";
    private static final String COMEDIA = "Comedia";
    private static final String DRAMA = "Drama";

    private static final Serie EL_ETERNAUTA = new Serie("El Eternauta", CIENCIA_FICCION);
    private static final Serie DARK_MATTER = new Serie("Dark Matter", CIENCIA_FICCION);
    private static final Serie EL_ENCARGADO = new Serie("El Encargado", COMEDIA);
    private static final Serie OKUPAS = new Serie("Okupas", DRAMA);

    private final ServicioDeStreaming servicio = new ServicioDeStreaming();

    private static Opinion publicada(Serie serie, double puntaje) {
        return new Opinion(serie, puntaje, EstadoOpinion.PUBLICADA);
    }

    private static Opinion moderada(Serie serie, double puntaje) {
        return new Opinion(serie, puntaje, EstadoOpinion.MODERADA);
    }

    /** El Eternauta 9,5 (2) - El Encargado 8 (3) - Dark Matter 6,5 (2) - Okupas 10 (2). */
    private static List<Opinion> opinionesDeEjemplo() {
        return List.of(
                publicada(EL_ETERNAUTA, 9), publicada(EL_ETERNAUTA, 10), moderada(EL_ETERNAUTA, 1),
                publicada(DARK_MATTER, 6), publicada(DARK_MATTER, 7),
                publicada(EL_ENCARGADO, 8), publicada(EL_ENCARGADO, 9), publicada(EL_ENCARGADO, 7),
                publicada(OKUPAS, 10), publicada(OKUPAS, 10));
    }

    @Test
    @DisplayName("el caso típico: filtra por género, cantidad y promedio, y ordena de mayor a menor")
    void casoTipico() {
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opinionesDeEjemplo(), List.of(CIENCIA_FICCION, COMEDIA), 2, 7);
        assertEquals(List.of(EL_ETERNAUTA, EL_ENCARGADO), resultado);
    }

    @Test
    @DisplayName("sin opiniones el resultado es una lista vacía")
    void sinOpiniones() {
        assertTrue(servicio.buscarSeriesMejorPuntuadas(List.of(), List.of(COMEDIA), 1, 0).isEmpty());
    }

    @Test
    @DisplayName("sin géneros pedidos no entra ninguna serie")
    void sinGeneros() {
        assertTrue(servicio.buscarSeriesMejorPuntuadas(opinionesDeEjemplo(), List.of(), 1, 0).isEmpty());
    }

    @Test
    @DisplayName("las opiniones moderadas no cuentan para el promedio")
    void lasModeradasNoCuentanParaElPromedio() {
        // Con la moderada (1) el promedio de El Eternauta sería 6,67 y no pasaría el mínimo de 9.
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opinionesDeEjemplo(), List.of(CIENCIA_FICCION), 1, 9);
        assertEquals(List.of(EL_ETERNAUTA), resultado);
    }

    @Test
    @DisplayName("las opiniones moderadas no cuentan para la cantidad mínima")
    void lasModeradasNoCuentanParaLaCantidad() {
        // El Eternauta tiene 3 opiniones pero sólo 2 publicadas.
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opinionesDeEjemplo(), List.of(CIENCIA_FICCION, COMEDIA), 3, 0);
        assertEquals(List.of(EL_ENCARGADO), resultado);
    }

    @Test
    @DisplayName("una serie que sólo tiene opiniones moderadas no aparece")
    void serieSoloConModeradas() {
        List<Opinion> opiniones = List.of(moderada(OKUPAS, 10), moderada(OKUPAS, 9));
        assertTrue(servicio.buscarSeriesMejorPuntuadas(opiniones, List.of(DRAMA), 1, 0).isEmpty());
    }

    @Test
    @DisplayName("el promedio tiene que ser estrictamente mayor al mínimo")
    void promedioIgualAlMinimoNoEntra() {
        List<Opinion> opiniones = List.of(publicada(EL_ENCARGADO, 8), publicada(EL_ENCARGADO, 8));
        assertTrue(servicio.buscarSeriesMejorPuntuadas(opiniones, List.of(COMEDIA), 1, 8).isEmpty());
        assertEquals(List.of(EL_ENCARGADO),
                servicio.buscarSeriesMejorPuntuadas(opiniones, List.of(COMEDIA), 1, 7.99));
    }

    @Test
    @DisplayName("la cantidad mínima es 'al menos': con exactamente esa cantidad entra")
    void cantidadIgualALaMinimaEntra() {
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opinionesDeEjemplo(), List.of(COMEDIA), 3, 0);
        assertEquals(List.of(EL_ENCARGADO), resultado);
    }

    @Test
    @DisplayName("cada serie aparece una sola vez aunque tenga muchas opiniones")
    void sinRepetidas() {
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opinionesDeEjemplo(), List.of(CIENCIA_FICCION, COMEDIA, DRAMA), 1, 0);
        assertEquals(List.of(OKUPAS, EL_ETERNAUTA, EL_ENCARGADO, DARK_MATTER), resultado);
    }

    @Test
    @DisplayName("las opiniones de una misma serie no necesitan venir juntas")
    void opinionesIntercaladas() {
        List<Opinion> opiniones = List.of(
                publicada(DARK_MATTER, 4), publicada(EL_ETERNAUTA, 10),
                publicada(DARK_MATTER, 6), publicada(EL_ETERNAUTA, 8));
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(opiniones, List.of(CIENCIA_FICCION), 2, 0);
        assertEquals(List.of(EL_ETERNAUTA, DARK_MATTER), resultado);
    }

    @Test
    @DisplayName("dos objetos Serie con el mismo título y género son la misma serie")
    void mismaSerieEnObjetosDistintos() {
        List<Opinion> opiniones = List.of(
                publicada(new Serie("Okupas", DRAMA), 6), publicada(new Serie("Okupas", DRAMA), 8));
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(opiniones, List.of(DRAMA), 2, 6.5);
        assertEquals(List.of(OKUPAS), resultado);
    }

    @Test
    @DisplayName("con el mismo promedio se respeta el orden de aparición")
    void empateDePromedio() {
        List<Opinion> opiniones = List.of(publicada(EL_ENCARGADO, 8), publicada(DARK_MATTER, 8));
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opiniones, List.of(CIENCIA_FICCION, COMEDIA), 1, 0);
        assertEquals(List.of(EL_ENCARGADO, DARK_MATTER), resultado);
    }

    @Test
    @DisplayName("no modifica las listas recibidas")
    void noModificaLasEntradas() {
        List<Opinion> opiniones = new ArrayList<>(opinionesDeEjemplo());
        List<String> generos = new ArrayList<>(List.of(COMEDIA, CIENCIA_FICCION));
        servicio.buscarSeriesMejorPuntuadas(opiniones, generos, 1, 0);
        assertEquals(opinionesDeEjemplo().size(), opiniones.size());
        assertSame(opinionesDeEjemplo().get(0).getSerie(), opiniones.get(0).getSerie());
        assertEquals(List.of(COMEDIA, CIENCIA_FICCION), generos);
    }

    @Test
    @DisplayName("valida los parámetros")
    void validaciones() {
        List<Opinion> opiniones = opinionesDeEjemplo();
        List<String> generos = List.of(COMEDIA);
        assertThrows(IllegalArgumentException.class, () -> servicio.buscarSeriesMejorPuntuadas(null, generos, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> servicio.buscarSeriesMejorPuntuadas(opiniones, null, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> servicio.buscarSeriesMejorPuntuadas(opiniones, generos, 0, 0));
    }

    @Test
    @DisplayName("las clases de datos validan sus atributos")
    void datosValidan() {
        assertThrows(IllegalArgumentException.class, () -> new Serie(null, COMEDIA));
        assertThrows(IllegalArgumentException.class, () -> new Serie(" ", COMEDIA));
        assertThrows(IllegalArgumentException.class, () -> new Serie("Okupas", null));
        assertThrows(IllegalArgumentException.class, () -> new Opinion(null, 5, EstadoOpinion.PUBLICADA));
        assertThrows(IllegalArgumentException.class, () -> new Opinion(OKUPAS, 5, null));
        assertThrows(IllegalArgumentException.class, () -> new Opinion(OKUPAS, -1, EstadoOpinion.PUBLICADA));
        assertThrows(IllegalArgumentException.class, () -> new EstadisticaDeSerie(moderada(OKUPAS, 5)));
        assertThrows(IllegalArgumentException.class,
                () -> new EstadisticaDeSerie(publicada(OKUPAS, 5)).agregar(publicada(EL_ENCARGADO, 5)));
    }
}
