package ar.uba.fi.cb100.clases.a2026.c02.s06.vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests del TDA Vector de la semana 6.
 *
 * <p>Las variables se declaran con la <b>interfaz</b> {@code Vector<T>} (la de
 * este paquete) y se construyen con la <b>implementación</b>
 * {@code s06.Vector}. Así el test prueba el contrato, no el arreglo de adentro:
 * si mañana la implementación cambia, el test sigue valiendo.
 *
 * <p>Como la interfaz y la implementación se llaman igual, la implementación se
 * nombra con su paquete completo en {@link #nuevo(int)} y en ningún otro lado.
 */
@DisplayName("Vector")
class TestDeVector {

    /** Único lugar donde se nombra la implementación. */
    private static <T> Vector<T> nuevo(int capacidadInicial) {
        return new ar.uba.fi.cb100.clases.a2026.c02.s06.Vector<>(capacidadInicial);
    }

    private static Vector<String> conABC() {
        Vector<String> v = nuevo(4);
        v.agregar("a");
        v.agregar("b");
        v.agregar("c");
        return v;
    }

    @Nested
    @DisplayName("construcción")
    class Construccion {

        @Test
        @DisplayName("nace vacío, con la capacidad pedida")
        void naceVacio() {
            Vector<String> v = nuevo(4);
            assertEquals(0, v.tamanio());
            assertEquals(4, v.capacidad());
        }

        @Test
        @DisplayName("el constructor por defecto arranca con capacidad 10")
        void capacidadPorDefecto() {
            Vector<String> v = new ar.uba.fi.cb100.clases.a2026.c02.s06.Vector<>();
            assertEquals(0, v.tamanio());
            assertEquals(10, v.capacidad());
        }

        @Test
        @DisplayName("la capacidad inicial tiene que ser mayor que cero")
        void capacidadInvalida() {
            assertThrows(IllegalArgumentException.class, () -> nuevo(0));
            assertThrows(IllegalArgumentException.class, () -> nuevo(-1));
        }
    }

    @Nested
    @DisplayName("agregar y obtener")
    class AgregarYObtener {

        @Test
        @DisplayName("agrega al final y se obtiene por posición")
        void agregaAlFinal() {
            Vector<String> v = conABC();
            assertEquals(3, v.tamanio());
            assertEquals("a", v.obtener(0));
            assertEquals("b", v.obtener(1));
            assertEquals("c", v.obtener(2));
        }

        @Test
        @DisplayName("al llenarse duplica la capacidad y no pierde nada")
        void duplicaLaCapacidad() {
            Vector<Integer> v = nuevo(2);
            v.agregar(10);
            v.agregar(20);
            assertEquals(2, v.capacidad(), "todavía no creció");

            v.agregar(30);                       // el tercero no entra: redimensiona
            assertEquals(4, v.capacidad());
            assertEquals(3, v.tamanio());

            v.agregar(40);
            v.agregar(50);                       // otra vez lleno: 4 -> 8
            assertEquals(8, v.capacidad());
            assertEquals(5, v.tamanio());
            for (int i = 0; i < 5; i++) {
                assertEquals((i + 1) * 10, v.obtener(i), "posición " + i);
            }
        }

        @Test
        @DisplayName("no acepta null")
        void noAceptaNull() {
            Vector<String> v = nuevo(4);
            assertThrows(IllegalArgumentException.class, () -> v.agregar(null));
            assertEquals(0, v.tamanio());
        }

        @Test
        @DisplayName("obtener valida el rango: 0 .. tamanio-1")
        void obtenerFueraDeRango() {
            Vector<String> v = conABC();
            assertThrows(IllegalArgumentException.class, () -> v.obtener(-1));
            assertThrows(IllegalArgumentException.class, () -> v.obtener(3));
        }

        @Test
        @DisplayName("obtener sobre un vector vacío falla aunque haya capacidad")
        void obtenerEnVacio() {
            Vector<String> v = nuevo(4);
            assertThrows(IllegalArgumentException.class, () -> v.obtener(0));
        }
    }

    @Nested
    @DisplayName("insertar")
    class Insertar {

        @Test
        @DisplayName("en el medio corre los que siguen a la derecha")
        void enElMedio() {
            Vector<String> v = conABC();
            v.insertar(1, "X");
            assertEquals(4, v.tamanio());
            assertEquals("a", v.obtener(0));
            assertEquals("X", v.obtener(1));
            assertEquals("b", v.obtener(2));
            assertEquals("c", v.obtener(3));
        }

        @Test
        @DisplayName("en la posición 0 queda primero")
        void alPrincipio() {
            Vector<String> v = conABC();
            v.insertar(0, "X");
            assertEquals("X", v.obtener(0));
            assertEquals("a", v.obtener(1));
            assertEquals(4, v.tamanio());
        }

        @Test
        @DisplayName("en la posición tamanio equivale a agregar al final")
        void alFinal() {
            Vector<String> v = conABC();
            v.insertar(3, "X");
            assertEquals("X", v.obtener(3));
            assertEquals(4, v.tamanio());
        }

        @Test
        @DisplayName("si está lleno, redimensiona antes de insertar")
        void insertarEnLleno() {
            Vector<String> v = nuevo(3);
            v.agregar("a");
            v.agregar("b");
            v.agregar("c");
            v.insertar(1, "X");
            assertEquals(6, v.capacidad());
            assertEquals(4, v.tamanio());
            assertEquals("X", v.obtener(1));
            assertEquals("c", v.obtener(3));
        }

        @Test
        @DisplayName("valida el rango 0 .. tamanio y rechaza null")
        void validaciones() {
            Vector<String> v = conABC();
            assertThrows(IllegalArgumentException.class, () -> v.insertar(-1, "X"));
            assertThrows(IllegalArgumentException.class, () -> v.insertar(4, "X"));
            assertThrows(IllegalArgumentException.class, () -> v.insertar(1, null));
            assertEquals(3, v.tamanio(), "no cambió nada");
        }
    }

    @Nested
    @DisplayName("eliminar")
    class Eliminar {

        @Test
        @DisplayName("del medio corre los que siguen a la izquierda")
        void delMedio() {
            Vector<String> v = conABC();
            v.eliminar(1);
            assertEquals(2, v.tamanio());
            assertEquals("a", v.obtener(0));
            assertEquals("c", v.obtener(1));
        }

        @Test
        @DisplayName("del principio y del final")
        void extremos() {
            Vector<String> v = conABC();
            v.eliminar(0);
            assertEquals("b", v.obtener(0));
            v.eliminar(v.tamanio() - 1);
            assertEquals(1, v.tamanio());
            assertEquals("b", v.obtener(0));
        }

        @Test
        @DisplayName("la posición liberada ya no se puede obtener")
        void noDejaBasura() {
            Vector<String> v = conABC();
            v.eliminar(2);
            assertThrows(IllegalArgumentException.class, () -> v.obtener(2));
        }

        @Test
        @DisplayName("eliminar todo lo deja vacío y se puede volver a llenar")
        void vaciarYReutilizar() {
            Vector<String> v = conABC();
            v.eliminar(0);
            v.eliminar(0);
            v.eliminar(0);
            assertEquals(0, v.tamanio());
            v.agregar("z");
            assertEquals("z", v.obtener(0));
            assertEquals(1, v.tamanio());
        }

        @Test
        @DisplayName("valida el rango; en un vector vacío cualquier índice falla")
        void fueraDeRango() {
            Vector<String> v = conABC();
            assertThrows(IllegalArgumentException.class, () -> v.eliminar(-1));
            assertThrows(IllegalArgumentException.class, () -> v.eliminar(3));
            Vector<String> vacio = nuevo(4);
            assertThrows(IllegalArgumentException.class, () -> vacio.eliminar(0));
        }
    }

    @Nested
    @DisplayName("contiene e indiceDe")
    class Buscar {

        @Test
        @DisplayName("encuentra el primero que coincide por equals, no por ==")
        void encuentraPorEquals() {
            Vector<String> v = nuevo(4);
            v.agregar(new String("hola"));
            v.agregar("chau");
            v.agregar("hola");
            assertTrue(v.contiene(new String("hola")));
            assertEquals(0, v.indiceDe("hola"), "el primero, no el último");
            assertEquals(1, v.indiceDe("chau"));
        }

        @Test
        @DisplayName("lo que no está da false y -1")
        void noEsta() {
            Vector<String> v = conABC();
            assertFalse(v.contiene("z"));
            assertEquals(-1, v.indiceDe("z"));
            Vector<String> vacio = nuevo(4);
            assertFalse(vacio.contiene("a"));
        }

        @Test
        @DisplayName("buscar null no está permitido")
        void buscarNull() {
            Vector<String> v = conABC();
            assertThrows(IllegalArgumentException.class, () -> v.contiene(null));
            assertThrows(IllegalArgumentException.class, () -> v.indiceDe(null));
        }
    }

    @Nested
    @DisplayName("ordenar")
    class Ordenar {

        @Test
        @DisplayName("deja los elementos ascendentes y no toca el tamaño")
        void ordenaAscendente() {
            Vector<Integer> v = nuevo(4);
            v.agregar(42);
            v.agregar(7);
            v.agregar(19);
            v.agregar(3);
            v.agregar(25);                       // fuerza el redimensionado
            v.ordenar();
            assertEquals(5, v.tamanio());
            int[] esperado = {3, 7, 19, 25, 42};
            for (int i = 0; i < esperado.length; i++) {
                assertEquals(esperado[i], v.obtener(i), "posición " + i);
            }
        }

        @Test
        @DisplayName("con Strings ordena alfabéticamente")
        void ordenaStrings() {
            Vector<String> v = nuevo(4);
            v.agregar("pera");
            v.agregar("anana");
            v.agregar("manzana");
            v.ordenar();
            assertEquals("anana", v.obtener(0));
            assertEquals("manzana", v.obtener(1));
            assertEquals("pera", v.obtener(2));
        }

        @Test
        @DisplayName("ordenar un vector vacío o de un elemento no falla")
        void casosTriviales() {
            Vector<Integer> vacio = nuevo(4);
            vacio.ordenar();
            assertEquals(0, vacio.tamanio());
            Vector<Integer> uno = nuevo(4);
            uno.agregar(1);
            uno.ordenar();
            assertEquals(1, uno.obtener(0));
        }

        @Test
        @DisplayName("si los elementos no son Comparable, falla al ordenar")
        void noComparable() {
            Vector<Object> v = nuevo(4);
            v.agregar(new Object());
            v.agregar(new Object());
            assertThrows(ClassCastException.class, v::ordenar);
        }
    }

    @Nested
    @DisplayName("agregarTodos")
    class AgregarTodos {

        @Test
        @DisplayName("copia los elementos del otro al final, en orden")
        void copiaAlFinal() {
            Vector<String> v = conABC();
            Vector<String> otro = nuevo(2);
            otro.agregar("d");
            otro.agregar("e");
            v.agregarTodos(otro);
            assertEquals(5, v.tamanio());
            assertEquals("d", v.obtener(3));
            assertEquals("e", v.obtener(4));
            assertEquals(2, otro.tamanio(), "el otro no cambia");
        }

        @Test
        @DisplayName("crece lo que haga falta")
        void creceSiHaceFalta() {
            Vector<Integer> v = nuevo(2);
            Vector<Integer> otro = nuevo(8);
            for (int i = 0; i < 6; i++) {
                otro.agregar(i);
            }
            v.agregarTodos(otro);
            assertEquals(6, v.tamanio());
            assertTrue(v.capacidad() >= 6);
            assertEquals(5, v.obtener(5));
        }

        @Test
        @DisplayName("agregar un vector vacío no cambia nada")
        void otroVacio() {
            Vector<String> v = conABC();
            v.agregarTodos(nuevo(4));
            assertEquals(3, v.tamanio());
        }
    }

    @Test
    @DisplayName("escenario completo: la secuencia de operaciones deja el estado esperado")
    void escenarioCompleto() {
        Vector<Integer> v = nuevo(2);
        v.agregar(5);                //  [5]
        v.agregar(1);                //  [5, 1]
        v.insertar(1, 9);            //  [5, 9, 1]        capacidad 2 -> 4
        v.agregar(3);                //  [5, 9, 1, 3]
        v.eliminar(0);               //  [9, 1, 3]
        v.ordenar();                 //  [1, 3, 9]
        assertEquals(3, v.tamanio());
        assertEquals(4, v.capacidad());
        assertEquals(1, v.obtener(0));
        assertEquals(3, v.obtener(1));
        assertEquals(9, v.obtener(2));
        assertTrue(v.contiene(9));
        assertEquals(1, v.indiceDe(3));
    }
}
