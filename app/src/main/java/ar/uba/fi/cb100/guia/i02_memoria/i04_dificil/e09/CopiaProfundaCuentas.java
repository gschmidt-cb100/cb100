package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e09;

/**
 * Copia profunda de un arreglo de Cuenta.
 *
 * Una copia superficial (Arrays.copyOf) crearia un arreglo nuevo pero con las
 * MISMAS Cuenta adentro: depositar en una se veria en la otra. La copia
 * profunda crea Cuenta nuevas, totalmente independientes del original.
 */
public class CopiaProfundaCuentas {

    /** Devuelve un arreglo nuevo con copias independientes de cada cuenta. */
    public static Cuenta[] copiaProfunda(Cuenta[] cuentas) {
        Cuenta[] copia = new Cuenta[cuentas.length];
        for (int i = 0; i < cuentas.length; i++) {
            // Nueva Cuenta con el mismo saldo: objeto distinto.
            copia[i] = new Cuenta(cuentas[i].getSaldo());
        }
        return copia;
    }

    public static void main(String[] args) {
        Cuenta[] originales = { new Cuenta(100), new Cuenta(200) };
        Cuenta[] copias = copiaProfunda(originales);

        copias[0].depositar(50); // solo afecta a la copia

        System.out.println("Original[0]: " + originales[0].getSaldo());
        System.out.println("Copia[0]:    " + copias[0].getSaldo());
    }
}
