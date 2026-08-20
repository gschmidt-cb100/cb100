package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CopiaProfundaCuentasTest {

    @Test
    @DisplayName("Depositar en una copia no afecta al original")
    void depositarEnCopiaNoAfectaOriginal() {
        Cuenta[] originales = { new Cuenta(100), new Cuenta(200) };
        Cuenta[] copias = CopiaProfundaCuentas.copiaProfunda(originales);

        copias[0].depositar(50);

        assertEquals(100, originales[0].getSaldo()); // original intacto
        assertEquals(150, copias[0].getSaldo());     // solo cambio la copia
    }

    @Test
    @DisplayName("La copia contiene objetos distintos (no las mismas cuentas)")
    void copiaConObjetosDistintos() {
        Cuenta[] originales = { new Cuenta(10) };
        Cuenta[] copias = CopiaProfundaCuentas.copiaProfunda(originales);

        assertNotSame(originales[0], copias[0]);
        assertEquals(originales[0].getSaldo(), copias[0].getSaldo());
    }

    @Test
    @DisplayName("El arreglo devuelto es una instancia distinta")
    void arregloDistinto() {
        Cuenta[] originales = { new Cuenta(1) };
        Cuenta[] copias = CopiaProfundaCuentas.copiaProfunda(originales);
        assertNotSame(originales, copias);
    }

    @Test
    @DisplayName("Caso borde: arreglo vacio")
    void arregloVacio() {
        Cuenta[] copias = CopiaProfundaCuentas.copiaProfunda(new Cuenta[]{});
        assertEquals(0, copias.length);
    }
}
