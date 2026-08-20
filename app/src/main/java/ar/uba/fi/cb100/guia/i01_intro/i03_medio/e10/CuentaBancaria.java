package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e10;

/**
 * Ejercicio 10: Cuenta bancaria.
 * Modela una cuenta con saldo, depósitos y extracciones.
 */
public class CuentaBancaria {

    private double saldo;

    public CuentaBancaria() {
        this.saldo = 0;
    }

    public CuentaBancaria(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    /**
     * Deposita un monto positivo en la cuenta.
     *
     * @param monto monto a depositar (debe ser > 0)
     * @throws IllegalArgumentException si el monto no es positivo
     */
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo");
        }
        saldo += monto;
    }

    /**
     * Extrae un monto positivo si hay saldo suficiente.
     *
     * @param monto monto a extraer (debe ser > 0)
     * @throws IllegalArgumentException si el monto no es positivo
     * @throws SaldoInsuficienteException si el saldo no alcanza
     */
    public void extraer(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a extraer debe ser positivo");
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente: saldo=" + saldo + ", monto=" + monto);
        }
        saldo -= monto;
    }

    public static void main(String[] args) {
        var cuenta = new CuentaBancaria();
        cuenta.depositar(1000);
        cuenta.extraer(400);
        System.out.println("Saldo final: " + cuenta.getSaldo());
    }
}
