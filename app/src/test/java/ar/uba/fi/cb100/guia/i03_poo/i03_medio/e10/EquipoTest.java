package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EquipoTest {

    @Test
    @DisplayName("El constructor hace copia defensiva: mutar el arreglo original no afecta al equipo")
    void copiaDefensivaEnConstructor() {
        int[] original = {10, 20, 30};
        Equipo equipo = new Equipo("A", original);

        original[0] = 999; // se muta el arreglo externo

        assertArrayEquals(new int[]{10, 20, 30}, equipo.getPuntajes());
    }

    @Test
    @DisplayName("El constructor copia duplica los puntajes (copia profunda)")
    void constructorCopiaDuplicaPuntajes() {
        Equipo a = new Equipo("A", new int[]{1, 2, 3});
        Equipo copia = new Equipo(a);

        assertArrayEquals(a.getPuntajes(), copia.getPuntajes());
        assertEquals(a.getNombre(), copia.getNombre());
    }

    @Test
    @DisplayName("Mutar el arreglo devuelto por getPuntajes no altera el estado interno")
    void getPuntajesNoExponeInterno() {
        Equipo equipo = new Equipo("A", new int[]{5, 6, 7});

        int[] copiados = equipo.getPuntajes();
        copiados[0] = -1;

        assertArrayEquals(new int[]{5, 6, 7}, equipo.getPuntajes());
    }

    @Test
    @DisplayName("La copia es independiente del original tras copiar")
    void copiaEsIndependiente() {
        int[] datos = {1, 2, 3};
        Equipo original = new Equipo("A", datos);
        Equipo copia = new Equipo(original);

        // Alterar el arreglo fuente no cambia ni el original ni la copia.
        datos[1] = 100;

        assertArrayEquals(new int[]{1, 2, 3}, original.getPuntajes());
        assertArrayEquals(new int[]{1, 2, 3}, copia.getPuntajes());
    }
}
