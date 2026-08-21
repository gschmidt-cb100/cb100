package ar.uba.fi.cb100.material.i01_intro;

/**
 * <b>Ejemplo integrador de excepciones</b>: un cajero automático con TODOS los
 * casos en un solo lugar.
 * <ul>
 *   <li>dos {@code try} anidados: el interno atiende lo esperable del negocio
 *       (saldo insuficiente); lo que él no captura sigue viajando al externo</li>
 *   <li>excepciones del sistema ({@code NumberFormatException},
 *       {@code IllegalArgumentException}) y una propia chequeada
 *       ({@link SaldoInsuficienteException})</li>
 *   <li>un {@code throw} lanzado desde adentro del bloque {@code try}</li>
 *   <li>varios {@code catch} ordenados de específico a general
 *       (¡{@code NumberFormatException} ES una {@code IllegalArgumentException}!)</li>
 *   <li>{@code finally}: la tarjeta se devuelve SIEMPRE, pase lo que pase</li>
 * </ul>
 * Corré el {@code main} y seguí la salida caso por caso contra el apunte.
 */
public class CajeroAutomatico {

    private int saldo;

    public CajeroAutomatico(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    /** Convierte el texto tecleado en un monto. Puede lanzar NumberFormatException (del sistema). */
    private static int interpretarMonto(String texto) {
        System.out.println("  interpretarMonto: leyendo \"" + texto + "\"");
        int monto = Integer.parseInt(texto);          // acá puede EXPLOTAR el parseo
        System.out.println("  interpretarMonto: entendí " + monto);
        return monto;
    }

    /**
     * Extrae dinero. Dos maneras distintas de fallar:
     * monto absurdo -> IllegalArgumentException (del sistema, no chequeada);
     * saldo que no alcanza -> SaldoInsuficienteException (propia, chequeada).
     */
    public void extraer(int monto) throws SaldoInsuficienteException {
        System.out.println("  extraer: piden $" + monto + " (saldo $" + saldo + ")");
        if (monto <= 0) {
            throw new IllegalArgumentException("el monto debe ser positivo y es " + monto);
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException(monto, saldo);
        }
        saldo -= monto;
        System.out.println("  extraer: entregado, saldo restante $" + saldo);
    }

    /** Una operación completa del cajero, con todos los casos cubiertos. */
    public void atender(String textoDelMonto) {
        System.out.println("atender(\"" + textoDelMonto + "\")");
        try {                                                    // ---- try EXTERNO
            int monto = interpretarMonto(textoDelMonto);
            try {                                                // ---- try INTERNO
                extraer(monto);                                  // throw DENTRO del try
                System.out.println("  operación exitosa");
            } catch (SaldoInsuficienteException e) {
                // lo esperable del negocio se resuelve acá, cerca
                System.out.println("  no alcanza: " + e.getMessage()
                        + " (faltan $" + e.faltante() + ")");
            }
            System.out.println("  fin del camino con monto numérico");
        } catch (NumberFormatException e) {
            // PRIMERO la específica (es hija de IllegalArgumentException)
            System.out.println("  eso no es un número (" + e.getMessage() + ")");
        } catch (IllegalArgumentException e) {
            // el try interno NO la capturaba: siguió viajando hasta acá
            System.out.println("  monto inválido: " + e.getMessage());
        } finally {
            System.out.println("  finally: devolver la tarjeta (SIEMPRE)");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CajeroAutomatico cajero = new CajeroAutomatico(1000);
        cajero.atender("300");    // caso feliz: todo el camino se ejecuta
        cajero.atender("5000");   // propia chequeada: la captura el try INTERNO
        cajero.atender("-50");    // del sistema: el interno no la captura, sube al EXTERNO
        cajero.atender("mil");    // parseo: explota ANTES del try interno
        System.out.println("main: el cajero sigue en pie, saldo final $" + cajero.saldo);
    }
}
