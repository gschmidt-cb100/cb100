package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e07;

/**
 * Texto inmutable con semantica copy-on-write (COW): cada operacion que
 * "modifica" el texto en realidad devuelve una NUEVA instancia. La instancia
 * original nunca cambia, asi que compartirla entre varias referencias es seguro.
 */
public class TextoCOW {
    private final String valor;

    public TextoCOW(String valor) {
        this.valor = valor;
    }

    public String get() {
        return valor;
    }

    /** Devuelve una nueva instancia con el texto extra concatenado. */
    public TextoCOW agregar(String extra) {
        return new TextoCOW(valor + extra);
    }

    public static void main(String[] args) {
        TextoCOW original = new TextoCOW("Hola");
        TextoCOW extendido = original.agregar(" mundo");
        System.out.println("Original:  " + original.get());
        System.out.println("Extendido: " + extendido.get());
    }
}
