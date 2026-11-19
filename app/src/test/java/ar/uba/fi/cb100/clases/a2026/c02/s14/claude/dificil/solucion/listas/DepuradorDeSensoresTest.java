package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.listas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ejemplo de parcial 26/11 (difícil) - depurar")
class DepuradorDeSensoresTest {

    private static final int DURACION = 10;

    private final DepuradorDeSensores depurador = new DepuradorDeSensores();

    /** Arma un registro con esos valores, todos con duración 10. */
    private static Registro registroCon(int... valores) {
        Registro registro = new Registro("Prueba");
        for (int valor : valores) {
            registro.agregarLectura(new Lectura(valor, DURACION));
        }
        return registro;
    }

    private static int[] valoresDe(Registro registro) {
        return registro.getLecturas().stream().mapToInt(Lectura::getValor).toArray();
    }

    private static int[] duracionesDe(Registro registro) {
        return registro.getLecturas().stream().mapToInt(Lectura::getDuracion).toArray();
    }

    @Test
    @DisplayName("un registro sin anomalías ni repetidas no cambia")
    void sinCambios() {
        Registro registro = registroCon(20, 21, 22, 23, 24);
        assertEquals(0, depurador.depurar(registro, 5, 3));
        assertArrayEquals(new int[] {20, 21, 22, 23, 24}, valoresDe(registro));
        assertArrayEquals(new int[] {10, 10, 10, 10, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("una lectura anómala se reemplaza por la mediana de la ventana")
    void corrigeUnaLectura() {
        Registro registro = registroCon(20, 21, 22, 90, 23);
        assertEquals(1, depurador.depurar(registro, 5, 3));
        // mediana(20, 21, 22) = 21
        assertArrayEquals(new int[] {20, 21, 22, 21, 23}, valoresDe(registro));
    }

    @Test
    @DisplayName("se usa la mediana y no el promedio: un valor extremo en la ventana no la arrastra")
    void medianaNoPromedio() {
        Registro registro = registroCon(10, 1000, 12, 13);
        assertEquals(0, depurador.depurar(registro, 5, 3));
        // mediana(10, 1000, 12) = 12, así que 13 es normal. Con el promedio (340,67) se habría corregido.
        assertArrayEquals(new int[] {10, 1000, 12, 13}, valoresDe(registro));
    }

    @Test
    @DisplayName("las primeras 'ventana' lecturas no se corrigen aunque sean anómalas")
    void primerasLecturasIntactas() {
        Registro registro = registroCon(500, 20, 900, 21);
        assertEquals(1, depurador.depurar(registro, 5, 3));
        // mediana(500, 20, 900) = 500: la cuarta (21) es la única que se evalúa, y se corrige.
        assertArrayEquals(new int[] {500, 20, 900, 500}, valoresDe(registro));
    }

    @Test
    @DisplayName("la lectura corregida entra a la ventana con su valor nuevo")
    void laCorregidaEntraALaVentana() {
        Registro registro = registroCon(10, 11, 12, 99, 98, 13);
        assertEquals(2, depurador.depurar(registro, 5, 3));
        // 99 contra mediana(10, 11, 12) = 11: se corrige a 11.
        // 98 contra mediana(11, 12, 11) = 11: se corrige a 11 y se comprime con la anterior.
        // 13 contra mediana(12, 11, 11) = 11: queda.
        // Si la ventana guardara el 99 original, la mediana para el 98 sería 12 y para el 13 sería 98.
        assertArrayEquals(new int[] {10, 11, 12, 11, 13}, valoresDe(registro));
        assertArrayEquals(new int[] {10, 10, 10, 20, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("la ventana se desliza: sólo cuentan las últimas lecturas")
    void laVentanaSeDesliza() {
        Registro registro = registroCon(10, 12, 14, 16, 18, 20, 22);
        assertEquals(0, depurador.depurar(registro, 5, 3));
        // Cada lectura está a 4 de la mediana de las tres anteriores. Contra las primeras, el 22 sería anómalo.
        assertArrayEquals(new int[] {10, 12, 14, 16, 18, 20, 22}, valoresDe(registro));
    }

    @Test
    @DisplayName("el borde exacto de la tolerancia no es anómalo")
    void bordeDeLaTolerancia() {
        Registro registro = registroCon(10, 10, 10, 15, 4);
        assertEquals(1, depurador.depurar(registro, 5, 3));
        // 15 está justo a 5 de la mediana 10: queda. 4 contra mediana(10, 10, 15) = 10 está a 6: se corrige.
        assertArrayEquals(new int[] {10, 15, 10}, valoresDe(registro));
        assertArrayEquals(new int[] {30, 10, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("con tolerancia cero toda lectura distinta de la mediana se corrige")
    void toleranciaCero() {
        Registro registro = registroCon(7, 8, 9, 10);
        assertEquals(1, depurador.depurar(registro, 0, 3));
        assertArrayEquals(new int[] {7, 8, 9, 8}, valoresDe(registro));
    }

    @Test
    @DisplayName("las repetidas consecutivas se comprimen sumando la duración a la anterior")
    void comprimeRepetidas() {
        Registro registro = registroCon(5, 5, 5, 6, 6, 5);
        assertEquals(0, depurador.depurar(registro, 100, 3));
        assertArrayEquals(new int[] {5, 6, 5}, valoresDe(registro));
        assertArrayEquals(new int[] {30, 20, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("la compresión también se aplica a las primeras 'ventana' lecturas")
    void comprimeEnLasPrimeras() {
        Registro registro = registroCon(7, 7, 8);
        assertEquals(0, depurador.depurar(registro, 0, 5));
        assertArrayEquals(new int[] {7, 8}, valoresDe(registro));
        assertArrayEquals(new int[] {20, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("una lectura eliminada igual cuenta para la ventana de las siguientes")
    void laEliminadaEntraALaVentana() {
        Registro registro = registroCon(1, 5, 5, 5, 9);
        assertEquals(0, depurador.depurar(registro, 5, 3));
        // El 5 de la cuarta posición ve la ventana (1, 5, 5) aunque uno de esos 5 se eliminó de la lista.
        // El 9 ve la ventana (5, 5, 5): mediana 5, queda. Si las eliminadas no contaran,
        // la ventana todavía no estaría llena y nada de esto se evaluaría.
        assertArrayEquals(new int[] {1, 5, 9}, valoresDe(registro));
        assertArrayEquals(new int[] {10, 30, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("una lectura corregida que queda igual a la anterior se comprime, y cuenta como corregida")
    void corregidaYComprimida() {
        Registro registro = registroCon(8, 8, 8, 70);
        assertEquals(1, depurador.depurar(registro, 5, 3));
        assertArrayEquals(new int[] {8}, valoresDe(registro));
        assertArrayEquals(new int[] {40}, duracionesDe(registro));
    }

    @Test
    @DisplayName("con ventana 1 cada lectura se compara con la anterior ya procesada")
    void ventanaDeUno() {
        Registro registro = registroCon(10, 12, 30, 13);
        assertEquals(1, depurador.depurar(registro, 5, 1));
        // 30 contra 12: se corrige a 12 y se comprime. 13 contra 12: queda.
        assertArrayEquals(new int[] {10, 12, 13}, valoresDe(registro));
        assertArrayEquals(new int[] {10, 20, 10}, duracionesDe(registro));
    }

    @Test
    @DisplayName("un registro vacío o más corto que la ventana no se corrige")
    void registroCorto() {
        Registro vacio = registroCon();
        assertEquals(0, depurador.depurar(vacio, 5, 3));
        assertTrue(vacio.getLecturas().isEmpty());

        Registro corto = registroCon(1, 900, 50);
        assertEquals(0, depurador.depurar(corto, 5, 3));
        assertArrayEquals(new int[] {1, 900, 50}, valoresDe(corto));
    }

    @Test
    @DisplayName("se modifica el registro original: la lista y las lecturas que quedan son las mismas")
    void modificaEnElLugar() {
        Registro registro = registroCon(10, 10, 12, 80);
        List<Lectura> lista = registro.getLecturas();
        Lectura primera = lista.get(0);
        Lectura ultima = lista.get(3);
        depurador.depurar(registro, 5, 3);
        assertSame(lista, registro.getLecturas());
        assertSame(primera, lista.get(0));
        assertSame(ultima, lista.get(2));
        assertEquals(20, primera.getDuracion());
        assertEquals(10, ultima.getValor());
    }

    @Test
    @DisplayName("valida los parámetros")
    void validaciones() {
        Registro registro = registroCon(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> depurador.depurar(null, 5, 3));
        assertThrows(IllegalArgumentException.class, () -> depurador.depurar(registro, -1, 3));
        assertThrows(IllegalArgumentException.class, () -> depurador.depurar(registro, 5, 0));
        assertThrows(IllegalArgumentException.class, () -> depurador.depurar(registro, 5, -3));
        assertThrows(IllegalArgumentException.class, () -> depurador.depurar(registro, 5, 4));   // ventana par
        assertArrayEquals(new int[] {1, 2, 3}, valoresDe(registro));
    }

    @Test
    @DisplayName("la ventana de mediana recuerda sólo los últimos valores y devuelve el del medio")
    void ventanaDeMediana() {
        VentanaDeMediana ventana = new VentanaDeMediana(3);
        assertFalse(ventana.estaLlena());
        assertThrows(IllegalStateException.class, ventana::getMediana);
        ventana.agregar(30);
        ventana.agregar(10);
        ventana.agregar(20);
        assertTrue(ventana.estaLlena());
        assertEquals(20, ventana.getMediana());       // (30, 10, 20)
        ventana.agregar(5);                           // sale el 30
        assertEquals(10, ventana.getMediana());       // (10, 20, 5)
        ventana.agregar(5);                           // sale el 10
        assertEquals(5, ventana.getMediana());        // (20, 5, 5)
        ventana.agregar(5);                           // sale el 20
        assertEquals(5, ventana.getMediana());        // (5, 5, 5)
        assertEquals(3, ventana.getCantidad());
        assertThrows(IllegalArgumentException.class, () -> new VentanaDeMediana(0));
        assertThrows(IllegalArgumentException.class, () -> new VentanaDeMediana(2));
    }

    @Test
    @DisplayName("da lo mismo que una versión directa, sin restricciones, sobre registros al azar")
    void coincideConLaVersionDirecta() {
        Random azar = new Random(26112026);
        for (int caso = 0; caso < 500; caso++) {
            int cantidad = azar.nextInt(40);
            int ventana = 1 + 2 * azar.nextInt(4);        // 1, 3, 5 ó 7
            int tolerancia = azar.nextInt(6);
            int[] valores = new int[cantidad];
            for (int i = 0; i < cantidad; i++) {
                valores[i] = azar.nextInt(12) - 3;
            }

            Registro registro = registroCon(valores);
            int corregidas = depurador.depurar(registro, tolerancia, ventana);

            List<int[]> esperado = new ArrayList<>();
            int corregidasEsperadas = depurarDeFormaDirecta(valores, tolerancia, ventana, esperado);

            assertEquals(corregidasEsperadas, corregidas);
            assertEquals(esperado.size(), registro.getLecturas().size());
            for (int i = 0; i < esperado.size(); i++) {
                assertEquals(esperado.get(i)[0], valoresDe(registro)[i]);
                assertEquals(esperado.get(i)[1], duracionesDe(registro)[i]);
            }
        }
    }

    /**
     * La misma regla del enunciado, escrita de la forma más simple posible y
     * SIN respetar las restricciones (guarda todos los valores procesados y
     * ordena la ventana cada vez). Sirve sólo como oráculo del test.
     * Deja en {@code resultado} pares {valor, duración} y devuelve la cantidad de corregidas.
     */
    private static int depurarDeFormaDirecta(int[] valores, int tolerancia, int ventana, List<int[]> resultado) {
        int[] procesados = new int[valores.length];
        int corregidas = 0;
        for (int i = 0; i < valores.length; i++) {
            int valor = valores[i];
            if (i >= ventana) {
                int[] ultimos = Arrays.copyOfRange(procesados, i - ventana, i);
                Arrays.sort(ultimos);
                int mediana = ultimos[ventana / 2];
                if (Math.abs(valor - mediana) > tolerancia) {
                    valor = mediana;
                    corregidas++;
                }
            }
            procesados[i] = valor;
            if (!resultado.isEmpty() && resultado.get(resultado.size() - 1)[0] == valor) {
                resultado.get(resultado.size() - 1)[1] += DURACION;
            } else {
                resultado.add(new int[] {valor, DURACION});
            }
        }
        return corregidas;
    }
}
