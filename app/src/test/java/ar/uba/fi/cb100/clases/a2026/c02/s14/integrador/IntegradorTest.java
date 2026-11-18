package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Geocodificacion;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ArchivoDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.CatalogoDeIndices;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.EnlaceDeMapas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.FiltroPorFuerzaBruta;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.GeneradorDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ResultadoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.IndiceDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.TipoDeIndice;

@DisplayName("Ejercicio integrador")
class IntegradorTest {

    private static final Geocodificacion OBELISCO = new Geocodificacion(-34.6037, -58.3816);

    private static Persona persona(long dni, String nombre, String apellido, String provincia, String pais,
                                   Long padre, Long madre) {
        return new Persona(dni, nombre, apellido, 30, "Ciudad", provincia, pais, "", "", "", "", OBELISCO, padre, madre);
    }

    /** Una familia chica: Abel y Berta son padres de Carla y Dario; Carla y Ezequiel, de Flor. */
    private static List<Persona> familia() {
        return List.of(
                persona(10, "Abel", "Gómez", "Córdoba", "Argentina", null, null),
                persona(20, "Berta", "Schmidt", "Córdoba", "Argentina", null, null),
                persona(30, "Carla", "Gómez", "Mendoza", "Argentina", 10L, 20L),
                persona(40, "Dario", "Gómez", "Montevideo", "Uruguay", 10L, 20L),
                persona(50, "Ezequiel", "Schneider", "Mendoza", "Argentina", null, null),
                persona(60, "Flor", "Schneider", "Salta", "Argentina", 50L, 30L));
    }

    @Nested
    @DisplayName("índices")
    class Indices {

        @Test
        @DisplayName("las tres estructuras devuelven lo mismo, en el mismo orden")
        void lasTresCoinciden() {
            List<Persona> personas = GeneradorDePersonas.generar(2000, 7L);
            for (CampoDeBusqueda campo : CampoDeBusqueda.values()) {
                for (String prefijo : new String[]{"", "a", "sch", "gonz", "1", "zzz"}) {
                    List<Persona> esperado = TipoDeIndice.VECTOR.crear(campo, personas).buscarPorPrefijo(prefijo);
                    for (TipoDeIndice tipo : TipoDeIndice.values()) {
                        assertEquals(esperado, tipo.crear(campo, personas).buscarPorPrefijo(prefijo),
                                tipo + " sobre " + campo + " con \"" + prefijo + "\"");
                    }
                }
            }
        }

        @Test
        @DisplayName("buscar por prefijo no distingue mayúsculas y encuentra sólo lo que empieza así")
        void porPrefijo() {
            for (TipoDeIndice tipo : TipoDeIndice.values()) {
                IndiceDePersonas indice = tipo.crear(CampoDeBusqueda.APELLIDO, familia());
                assertEquals(List.of(20L, 50L, 60L),
                        indice.buscarPorPrefijo("SCH").stream().map(Persona::dni).toList(), tipo.toString());
                assertEquals(1, indice.buscarPorPrefijo("schm").size());
                assertTrue(indice.buscarPorPrefijo("midt").isEmpty(), "no es \"contiene\"");
                assertEquals(6, indice.buscarPorPrefijo("").size());
                assertEquals(6, indice.getCantidad());
            }
        }

        @Test
        @DisplayName("el índice por DNI encuentra a la persona exacta")
        void porDni() {
            IndiceDePersonas indice = TipoDeIndice.ARBOL.crear(CampoDeBusqueda.DNI, familia());
            assertEquals("Carla", indice.buscarPorPrefijo("30").get(0).nombre());
        }

        @Test
        @DisplayName("el catálogo arma los nueve índices y mide cada búsqueda")
        void catalogo() {
            CatalogoDeIndices catalogo = new CatalogoDeIndices(familia());
            assertEquals(9, catalogo.getMediciones().size());
            List<ResultadoDeBusqueda> losTres = catalogo.buscarConTodos(CampoDeBusqueda.NOMBRE, "c");
            assertEquals(3, losTres.size());
            losTres.forEach(resultado -> assertEquals(1, resultado.getCantidad()));
            assertTrue(losTres.get(0).nanosegundos() >= 0);
        }
    }

    @Nested
    @DisplayName("fuerza bruta")
    class FuerzaBruta {

        @Test
        @DisplayName("filtra por provincia y por país, con el texto en cualquier parte")
        void filtrar() {
            assertEquals(2, FiltroPorFuerzaBruta.filtrar(familia(), FiltroPorFuerzaBruta.Campo.PROVINCIA, "mendoza").getCantidad());
            assertEquals(1, FiltroPorFuerzaBruta.filtrar(familia(), FiltroPorFuerzaBruta.Campo.PAIS, "URU").getCantidad());
            assertEquals(5, FiltroPorFuerzaBruta.filtrar(familia(), FiltroPorFuerzaBruta.Campo.PAIS, "gent").getCantidad());
            assertEquals(0, FiltroPorFuerzaBruta.filtrar(familia(), FiltroPorFuerzaBruta.Campo.PAIS, "chile").getCantidad());
        }

        @Test
        @DisplayName("cuenta las personas por país")
        void contar() {
            assertEquals("{Argentina=5, Uruguay=1}",
                    FiltroPorFuerzaBruta.contarPor(familia(), FiltroPorFuerzaBruta.Campo.PAIS).toString());
        }
    }

    @Nested
    @DisplayName("grafo familiar")
    class Grafo {

        private final GrafoFamiliar grafo = new GrafoFamiliar(familia());

        @Test
        @DisplayName("hay una arista de cada padre o madre a cada hijo")
        void aristas() {
            assertEquals(6, grafo.getCantidadDeVertices());
            assertEquals(6, grafo.getCantidadDeAristas());
            assertEquals(List.of(30L, 40L), grafo.getHijosDe(10));
            assertEquals(List.of(50L, 30L), grafo.getPadresDe(60));
        }

        @Test
        @DisplayName("en profundidad agota una rama antes de pasar a la otra")
        void enProfundidad() {
            assertEquals(List.of(10L, 30L, 60L, 40L), grafo.recorrerEnProfundidad(10));
        }

        @Test
        @DisplayName("en anchura visita primero todos los hijos y después los nietos")
        void enAnchura() {
            assertEquals(List.of(10L, 30L, 40L, 60L), grafo.recorrerEnAnchura(10));
        }

        @Test
        @DisplayName("el orden topológico pone a todo padre antes que sus hijos")
        void topologico() {
            List<Long> orden = grafo.ordenarTopologicamente();
            assertEquals(List.of(10L, 20L, 30L, 40L, 50L, 60L), orden);
            verificarPrecedencia(grafo, orden);
        }

        @Test
        @DisplayName("el orden topológico de 5.000 personas generadas respeta la precedencia")
        void topologicoGrande() {
            GrafoFamiliar grande = new GrafoFamiliar(GeneradorDePersonas.generar(5000, 3L));
            List<Long> orden = grande.ordenarTopologicamente();
            assertEquals(5000, orden.size());
            verificarPrecedencia(grande, orden);
        }

        @Test
        @DisplayName("un ciclo en los datos se detecta")
        void ciclo() {
            GrafoFamiliar conCiclo = new GrafoFamiliar(List.of(
                    persona(1, "A", "X", "P", "Q", 2L, null), persona(2, "B", "X", "P", "Q", 1L, null)));
            assertThrows(IllegalStateException.class, conCiclo::ordenarTopologicamente);
        }

        @Test
        @DisplayName("la familia incluye ancestros y descendientes, con la persona primero")
        void familiaCercana() {
            Set<Long> deCarla = grafo.getFamiliaDe(30, 2, 20);
            assertEquals(30L, deCarla.iterator().next());
            assertEquals(Set.of(30L, 10L, 20L, 60L), deCarla);
            assertEquals(2, grafo.getFamiliaDe(30, 2, 2).size(), "respeta el tope");
        }

        @Test
        @DisplayName("un DNI que no existe se rechaza; un padre fuera del archivo se ignora")
        void validaciones() {
            assertThrows(IllegalArgumentException.class, () -> grafo.recorrerEnAnchura(99));
            GrafoFamiliar huerfano = new GrafoFamiliar(List.of(persona(1, "A", "X", "P", "Q", 777L, null)));
            assertEquals(0, huerfano.getCantidadDeAristas());
        }

        private static void verificarPrecedencia(GrafoFamiliar grafo, List<Long> orden) {
            Set<Long> yaVistos = new HashSet<>();
            for (long dni : orden) {
                for (long padre : grafo.getPadresDe(dni)) {
                    assertTrue(yaVistos.contains(padre), "el padre " + padre + " aparece después de " + dni);
                }
                yaVistos.add(dni);
            }
        }
    }

    @Nested
    @DisplayName("archivo, generador y mapas")
    class Archivo {

        @Test
        @DisplayName("lo que se escribe en el Excel se lee igual")
        void idaYVuelta(@TempDir Path carpeta) throws IOException {
            List<Persona> originales = GeneradorDePersonas.generar(300, 11L);
            Path archivo = carpeta.resolve("personas.xlsx");
            ArchivoDePersonas.escribir(archivo, originales);
            ArchivoDePersonas.ResultadoDeCarga carga = ArchivoDePersonas.leer(archivo);
            assertTrue(carga.errores().isEmpty());
            assertEquals(originales, carga.personas());
        }

        @Test
        @DisplayName("el generador es reproducible, no repite DNI y deja padres más viejos que los hijos")
        void generador() {
            List<Persona> personas = GeneradorDePersonas.generar(3000, 5L);
            assertEquals(personas, GeneradorDePersonas.generar(3000, 5L));
            assertFalse(personas.equals(GeneradorDePersonas.generar(3000, 6L)));
            assertEquals(3000, personas.stream().map(Persona::dni).distinct().count());
            GrafoFamiliar grafo = new GrafoFamiliar(personas);
            for (Persona hijo : personas) {
                for (long padre : grafo.getPadresDe(hijo.dni())) {
                    assertTrue(grafo.getPersona(padre).edad() > hijo.edad());
                }
            }
            assertNull(personas.get(0).dniDelPadre(), "la primera generación no tiene padres");
        }

        @Test
        @DisplayName("el enlace de Google Maps: un punto es una búsqueda, varios un recorrido")
        void enlace() {
            assertEquals("https://www.google.com/maps/search/?api=1&query=-34.603700,-58.381600",
                    EnlaceDeMapas.crear(familia().subList(0, 1)));
            assertEquals("https://www.google.com/maps/dir/-34.603700,-58.381600/-34.603700,-58.381600",
                    EnlaceDeMapas.crear(familia().subList(0, 2)));
            assertThrows(IllegalArgumentException.class, () -> EnlaceDeMapas.crear(List.of()));
        }

        @Test
        @DisplayName("una persona inválida no se puede construir")
        void personaInvalida() {
            assertThrows(IllegalArgumentException.class, () -> persona(0, "A", "B", "P", "Q", null, null));
            assertThrows(IllegalArgumentException.class, () -> persona(1, " ", "B", "P", "Q", null, null));
            assertThrows(IllegalArgumentException.class, () -> persona(1, "A", "B", "P", "Q", 1L, null));
            assertThrows(IllegalArgumentException.class, () -> Geocodificacion.desdeTexto("norte, sur"));
            assertThrows(IllegalArgumentException.class, () -> new Geocodificacion(91, 0));
        }
    }
}
