package ar.uba.fi.cb100.material.i03_poo;

/**
 * <b>Encapsulamiento</b>: el estado ({@code saldo}) es privado y sólo se puede
 * tocar por métodos que <b>protegen los invariantes</b> (el saldo nunca queda
 * negativo). Nadie puede dejar el objeto en un estado inválido desde afuera.
 */
public class CuentaBancaria {

    private double saldo;   // privado: nadie lo cambia directamente

    public CuentaBancaria(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("el saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    public double saldo() {
        return saldo;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("el monto debe ser positivo");
        }
        saldo += monto;
    }

    public void extraer(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("el monto debe ser positivo");
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException(saldo, monto);
        }
        saldo -= monto;
    }

    /** Excepción propia del dominio de la cuenta. */
    public static class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(double saldo, double monto) {
            super("saldo insuficiente: hay " + saldo + " y se quisieron extraer " + monto);
        }
    }

    public static void main(String[] args) {
        CuentaBancaria c = new CuentaBancaria(1000);
        c.depositar(500);
        c.extraer(300);
        System.out.println("saldo: " + c.saldo());   // 1200.0
        try {
            c.extraer(99999);
        } catch (SaldoInsuficienteException e) {
            System.out.println("rechazado: " + e.getMessage());
        }
    }
}
