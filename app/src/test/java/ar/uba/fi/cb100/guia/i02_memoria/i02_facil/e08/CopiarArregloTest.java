package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CopiarArregloTest {

    @Test
    @DisplayName("Modificar la copia no afecta al arreglo original")
    void originalIntacto() {
        int[] original = {1, 2, 3};
        int[] copia = CopiarArreglo.copiarYmodificar(original);

        assertArrayEquals(new int[]{1, 2, 3}, original);
        assertArrayEquals(new int[]{99, 2, 3}, copia);
    }

    @Test
    @DisplayName("La copia es un objeto distinto del original")
    void copiaEsOtroObjeto() {
        int[] original = {5, 6};
        int[] copia = CopiarArreglo.copiarYmodificar(original);
        assertNotSame(original, copia);
    }
}
