package ar.uba.fi.cb100.material.i01_intro;

/**
 * ¿Y si NADIE captura? La misma cadena de llamadas que
 * {@link ViajeDeUnaExcepcion}, pero sin try/catch: la excepción atraviesa
 * TODOS los marcos, llega al fondo de la pila, y la JVM corta el programa
 * imprimiendo el <b>stack trace</b> — la "foto" de la pila en el momento del
 * lanzamiento, con la cadena de llamadas al revés (lo más profundo arriba).
 * <p>
 * Corrélo y estudiá la salida: aprender a LEER un stack trace es aprender a
 * encontrar tus errores solo.
 */
public class ExcepcionSinCapturar {

    public static void main(String[] args) {
        System.out.println("(1) main: por llamar (sin ninguna red)");
        procesarPedido(-3);
        System.out.println("(X) main: NO se ejecuta: la JVM ya cortó el programa");
    }

    static void procesarPedido(int cantidad) {
        System.out.println("(2) procesarPedido: por validar");
        validarCantidad(cantidad);
        System.out.println("(X) procesarPedido: NO se ejecuta");
    }

    static void validarCantidad(int cantidad) {
        System.out.println("(3) validarCantidad: verificando " + cantidad);
        if (cantidad <= 0) {
            throw new IllegalArgumentException("la cantidad debe ser positiva y es " + cantidad);
        }
    }
}
