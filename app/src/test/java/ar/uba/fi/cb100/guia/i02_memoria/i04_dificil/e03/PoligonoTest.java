package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PoligonoTest {

    @Test
    @DisplayName("Mutar el arreglo original no afecta la copia profunda")
    void mutarOriginalNoAfectaCopia() {
        Punto[] ps = { new Punto(0, 0), new Punto(1, 1) };
        Poligono original = new Poligono(ps);
        Poligono copia = Poligono.copiaProfunda(original);

        ps[0] = new Punto(99, 99); // muto el arreglo que comparte el original

        assertEquals(new Punto(99, 99), original.puntos()[0]);
        assertEquals(new Punto(0, 0), copia.puntos()[0]);
    }

    @Test
    @DisplayName("La copia tiene los mismos puntos que el original")
    void copiaConservaContenido() {
        Punto[] ps = { new Punto(2, 3), new Punto(4, 5) };
        Poligono original = new Poligono(ps);
        Poligono copia = Poligono.copiaProfunda(original);

        assertArrayEquals(original.puntos(), copia.puntos());
    }

    @Test
    @DisplayName("La copia usa un arreglo distinto (no es la misma referencia)")
    void copiaUsaArregloDistinto() {
        Punto[] ps = { new Punto(1, 1) };
        Poligono original = new Poligono(ps);
        Poligono copia = Poligono.copiaProfunda(original);

        assertNotSame(original.puntos(), copia.puntos());
    }

    @Test
    @DisplayName("Caso borde: poligono con arreglo vacio")
    void poligonoVacio() {
        Poligono original = new Poligono(new Punto[]{});
        Poligono copia = Poligono.copiaProfunda(original);
        assertEquals(0, copia.puntos().length);
        assertNotSame(original.puntos(), copia.puntos());
    }
}
