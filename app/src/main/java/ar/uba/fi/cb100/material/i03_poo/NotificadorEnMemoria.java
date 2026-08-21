package ar.uba.fi.cb100.material.i03_poo;

/**
 * Otra implementación del MISMO contrato: no imprime nada — acumula los
 * mensajes en memoria. ¿Para qué sirve algo así? Para los TESTS: se enchufa
 * esta implementación, se ejercita el código, y después se inspecciona qué
 * avisos se enviaron. Es el truco profesional de "programar contra la
 * interfaz": el código que avisa ni se entera del cambio.
 */
public class NotificadorEnMemoria implements Notificador {

    private final StringBuilder registro = new StringBuilder();
    private int cantidad = 0;

    @Override
    public void notificar(String mensaje) {
        registro.append(mensaje).append('\n');
        cantidad++;
    }

    public int cantidadDeAvisos() {
        return cantidad;
    }

    public String avisosRegistrados() {
        return registro.toString();
    }
}
