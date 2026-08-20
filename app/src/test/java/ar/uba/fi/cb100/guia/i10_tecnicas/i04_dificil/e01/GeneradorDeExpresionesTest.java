package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GeneradorDeExpresionesTest {

    private final GeneradorDeExpresiones generador = new GeneradorDeExpresiones();

    @Test
    @DisplayName("Para 100 existen exactamente 11 expresiones")
    void oncePara100() {
        assertEquals(11, generador.expresionesQueDan(100).size());
    }

    @Test
    @DisplayName("Para 100 aparecen las soluciones conocidas 1+2+3-4+5+6+78+9 y 123-45-67+89")
    void solucionesConocidas() {
        List<String> soluciones = generador.expresionesQueDan(100);
        assertTrue(soluciones.contains("1+2+3-4+5+6+78+9"));
        assertTrue(soluciones.contains("123-45-67+89"));
    }

    @Test
    @DisplayName("Toda expresion generada usa los digitos 1..9 en orden")
    void digitosEnOrden() {
        for (String expresion : generador.expresionesQueDan(100)) {
            String soloDigitos = expresion.replace("+", "").replace("-", "");
            assertEquals("123456789", soloDigitos, "Expresion invalida: " + expresion);
        }
    }

    @Test
    @DisplayName("Toda expresion generada realmente evalua al objetivo")
    void evaluanAlObjetivo() {
        for (String expresion : generador.expresionesQueDan(100)) {
            assertEquals(100, evaluar(expresion), "No da 100: " + expresion);
        }
    }

    @Test
    @DisplayName("El maximo posible es 123456789 (todo concatenado) y tiene una sola expresion")
    void objetivoMaximo() {
        assertEquals(List.of("123456789"), generador.expresionesQueDan(123456789));
    }

    @Test
    @DisplayName("Un objetivo inalcanzable devuelve la lista vacia")
    void objetivoInalcanzable() {
        assertTrue(generador.expresionesQueDan(123456790).isEmpty());
    }

    /** Evaluador independiente (suma de terminos con signo) para verificar. */
    private long evaluar(String expresion) {
        long total = 0;
        int i = 0;
        while (i < expresion.length()) {
            int signo = 1;
            char c = expresion.charAt(i);
            if (c == '+' || c == '-') {
                signo = c == '-' ? -1 : 1;
                i++;
            }
            long termino = 0;
            while (i < expresion.length() && Character.isDigit(expresion.charAt(i))) {
                termino = termino * 10 + (expresion.charAt(i) - '0');
                i++;
            }
            total += signo * termino;
        }
        return total;
    }
}
