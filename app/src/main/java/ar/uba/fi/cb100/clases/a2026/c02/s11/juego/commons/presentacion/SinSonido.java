package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

/**
 * Implementación de {@link Sonido} que no hace nada ("objeto nulo").
 * Sirve cuando la máquina no tiene audio o no se encontraron los archivos,
 * y para probar el juego en silencio.
 */
public class SinSonido implements Sonido {

    @Override
    public void reproducirJugada() {
    }

    @Override
    public void reproducirError() {
    }

    @Override
    public void reproducirVictoria() {
    }

    @Override
    public void reproducirEmpate() {
    }

    @Override
    public void iniciarMusica() {
    }

    @Override
    public void close() {
    }
}
