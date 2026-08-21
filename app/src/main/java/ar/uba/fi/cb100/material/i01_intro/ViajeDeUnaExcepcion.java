package ar.uba.fi.cb100.material.i01_intro;

/**
 * El VIAJE de una excepción por la pila de llamadas.
 * <p>
 * {@code main} llama a {@code procesarPedido}, que llama a
 * {@code validarCantidad}: tres marcos apilados. Cuando la validación lanza,
 * la excepción viaja de vuelta DESAPILANDO: salta el resto de
 * {@code validarCantidad}, salta el resto de {@code procesarPedido}, y recién
 * se detiene en el {@code catch} de {@code main}.
 * <p>
 * Los {@code println} numerados muestran EXACTAMENTE qué se ejecuta y qué no.
 */
public class ViajeDeUnaExcepcion {

    public static void main(String[] args) {
        System.out.println("(1) main: por llamar a procesarPedido(-3)");
        try {
            procesarPedido(-3);
            System.out.println("(X) main: NO se ejecuta (la llamada terminó en excepción)");
        } catch (IllegalArgumentException e) {
            System.out.println("(5) main: ¡capturada! mensaje = " + e.getMessage());
        }
        System.out.println("(6) main: el programa SIGUE con normalidad");
    }

    static void procesarPedido(int cantidad) {
        System.out.println("(2) procesarPedido: por llamar a validarCantidad");
        validarCantidad(cantidad);
        System.out.println("(X) procesarPedido: NO se ejecuta (la excepción pasó de largo)");
    }

    static void validarCantidad(int cantidad) {
        System.out.println("(3) validarCantidad: verificando " + cantidad);
        if (cantidad <= 0) {
            System.out.println("(4) validarCantidad: cantidad inválida, LANZO");
            throw new IllegalArgumentException("la cantidad debe ser positiva y es " + cantidad);
        }
        System.out.println("(X) validarCantidad: NO se ejecuta (está después del throw)");
    }
}
