package ar.uba.fi.cb100.material.i13_diseno.tostadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Los casos de uso de la Unidad 13 convertidos en tests, uno a uno (CU1 a
 * CU8). Cada test es una historia completa: situación inicial, acción y
 * resultado esperado, incluidos los casos que el TDA tiene que RECHAZAR.
 */
@DisplayName("Tostadora")
class TostadoraTest {

    @Test
    @DisplayName("CU1: nace apagada, con todas las ranuras libres y el nivel inicial")
    void naceApagada() {
        Tostadora tostadora = new Tostadora(4);
        assertEquals(EstadoDeTostadora.APAGADA, tostadora.getEstado());
        assertEquals(4, tostadora.contarRanurasLibres());
        assertEquals(Tostadora.NIVEL_INICIAL, tostadora.getNivelDeCalor());
        assertNull(tostadora.ranuraQueMasTosto(), "nadie tostó todavía");
    }

    @Test
    @DisplayName("CU2: encender, tostar en una ranura y finalizar")
    void tostarUnPan() {
        Tostadora tostadora = new Tostadora(2);
        tostadora.encender();
        tostadora.iniciarTostado(1);
        assertEquals(1, tostadora.contarRanurasLibres());
        tostadora.finalizarTostado(1);
        assertEquals(2, tostadora.contarRanurasLibres());
        assertEquals(1, tostadora.ranuraQueMasTosto().getNumero());
        assertEquals(1, tostadora.ranuraQueMasTosto().getPanesTostados());
    }

    @Test
    @DisplayName("CU3: la ranura que más tostó; en empate gana la de menor número")
    void ranuraQueMasTosto() {
        Tostadora tostadora = new Tostadora(3);
        tostadora.encender();
        for (int vez = 0; vez < 2; vez++) {            // ranura 3: dos panes
            tostadora.iniciarTostado(3);
            tostadora.finalizarTostado(3);
        }
        tostadora.iniciarTostado(1);                  // ranura 1: uno
        tostadora.finalizarTostado(1);
        assertEquals(3, tostadora.ranuraQueMasTosto().getNumero());

        tostadora.iniciarTostado(1);                  // ranura 1 empata con dos
        tostadora.finalizarTostado(1);
        assertEquals(1, tostadora.ranuraQueMasTosto().getNumero(), "empate: la de menor número");
    }

    @Test
    @DisplayName("CU4: tostar con la tostadora apagada es un error de estado")
    void tostarApagada() {
        Tostadora tostadora = new Tostadora(2);
        assertThrows(IllegalStateException.class, () -> tostadora.iniciarTostado(1));
        assertEquals(2, tostadora.contarRanurasLibres(), "no cambió nada");
    }

    @Test
    @DisplayName("CU5: tostar en una ranura ocupada, o finalizar una libre, son errores de estado")
    void transicionesInvalidasDeRanura() {
        Tostadora tostadora = new Tostadora(2);
        tostadora.encender();
        tostadora.iniciarTostado(2);
        assertThrows(IllegalStateException.class, () -> tostadora.iniciarTostado(2));
        assertThrows(IllegalStateException.class, () -> tostadora.finalizarTostado(1));
        assertEquals(1, tostadora.contarRanurasLibres());
    }

    @Test
    @DisplayName("CU6: una ranura que no existe es un error de argumento")
    void ranuraInexistente() {
        Tostadora tostadora = new Tostadora(2);
        tostadora.encender();
        assertThrows(IllegalArgumentException.class, () -> tostadora.iniciarTostado(0));
        assertThrows(IllegalArgumentException.class, () -> tostadora.iniciarTostado(3));
        assertThrows(IllegalArgumentException.class, () -> tostadora.finalizarTostado(-1));
    }

    @Test
    @DisplayName("CU7: el nivel de calor va de 1 a 10, encendida o apagada")
    void nivelDeCalor() {
        Tostadora tostadora = new Tostadora(1);
        tostadora.fijarNivelDeCalor(10);
        assertEquals(10, tostadora.getNivelDeCalor());
        assertThrows(IllegalArgumentException.class, () -> tostadora.fijarNivelDeCalor(0));
        assertThrows(IllegalArgumentException.class, () -> tostadora.fijarNivelDeCalor(11));
        assertEquals(10, tostadora.getNivelDeCalor(), "el nivel inválido no se aplicó");
    }

    @Test
    @DisplayName("CU8: no se puede apagar con ranuras ocupadas, ni encender dos veces")
    void apagarYEncender() {
        Tostadora tostadora = new Tostadora(2);
        assertThrows(IllegalStateException.class, tostadora::apagar);
        tostadora.encender();
        assertThrows(IllegalStateException.class, tostadora::encender);
        tostadora.iniciarTostado(1);
        assertThrows(IllegalStateException.class, tostadora::apagar);
        tostadora.finalizarTostado(1);
        tostadora.apagar();
        assertEquals(EstadoDeTostadora.APAGADA, tostadora.getEstado());
    }

    @Test
    @DisplayName("El constructor rechaza una tostadora sin ranuras")
    void sinRanuras() {
        assertThrows(IllegalArgumentException.class, () -> new Tostadora(0));
    }
}
