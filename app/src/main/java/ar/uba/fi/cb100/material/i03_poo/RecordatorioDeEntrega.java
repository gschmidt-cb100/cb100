package ar.uba.fi.cb100.material.i03_poo;

/**
 * El USUARIO del contrato. Fijate el detalle clave: el atributo es del tipo
 * de la INTERFAZ ({@code Notificador}), nunca de una implementación concreta.
 * Esta clase funciona igual con consola, con memoria, o con la implementación
 * que se invente el año que viene — sin tocarle una línea.
 */
public class RecordatorioDeEntrega {

    private final Notificador notificador;    // depende del CONTRATO, no del cómo
    private final String materia;

    public RecordatorioDeEntrega(Notificador notificador, String materia) {
        this.notificador = notificador;
        this.materia = materia;
    }

    public void avisarEntrega(String tp, int diasQueFaltan) {
        if (diasQueFaltan < 0) {
            throw new IllegalArgumentException("los días no pueden ser negativos");
        }
        if (diasQueFaltan == 0) {
            notificador.notificarUrgente(materia + ": ¡" + tp + " vence HOY!");
        } else {
            notificador.notificar(materia + ": " + tp + " vence en " + diasQueFaltan + " días");
        }
    }

    public static void main(String[] args) {
        // MISMA clase, PRIMERA implementación: avisos por consola.
        RecordatorioDeEntrega porConsola =
                new RecordatorioDeEntrega(new NotificadorPorConsola(), "CB100");
        porConsola.avisarEntrega("TP individual", 3);
        porConsola.avisarEntrega("TP individual", 0);

        // MISMA clase, OTRA implementación: nadie tocó RecordatorioDeEntrega.
        NotificadorEnMemoria memoria = new NotificadorEnMemoria();
        RecordatorioDeEntrega paraTest =
                new RecordatorioDeEntrega(memoria, "CB100");
        paraTest.avisarEntrega("TP grupal", 7);
        paraTest.avisarEntrega("TP grupal", 0);
        System.out.println(memoria.cantidadDeAvisos());    // 2
        System.out.print(memoria.avisosRegistrados());
        // CB100: TP grupal vence en 7 días
        // ¡URGENTE! CB100: TP grupal vence HOY!
    }
}
