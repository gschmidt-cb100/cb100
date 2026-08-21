package ar.uba.fi.cb100.material.i03_poo;

/**
 * Un contrato de OTRO dominio (nada de geometría): "algo que sabe avisar".
 * Quien necesite avisar depende de ESTA interfaz, sin saber — ni importarle —
 * cómo se materializa el aviso.
 */
public interface Notificador {

    /** Hace llegar el mensaje a destino, como sea que "destino" signifique. */
    void notificar(String mensaje);

    /** Método default: implementación común que las clases pueden heredar o pisar. */
    default void notificarUrgente(String mensaje) {
        notificar("¡URGENTE! " + mensaje);
    }
}
