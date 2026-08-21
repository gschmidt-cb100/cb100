package ar.uba.fi.cb100.material.i03_poo;

/** Una implementación del contrato: avisa imprimiendo en la consola. */
public class NotificadorPorConsola implements Notificador {

    @Override
    public void notificar(String mensaje) {
        System.out.println("[consola] " + mensaje);
    }
}
