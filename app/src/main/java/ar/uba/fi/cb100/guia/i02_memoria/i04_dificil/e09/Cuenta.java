package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e09;

/**
 * Cuenta bancaria mutable: su saldo cambia con depositar().
 * Al ser mutable, si dos arreglos comparten la misma Cuenta, un deposito
 * hecho a traves de uno se ve reflejado en el otro (aliasing de objetos).
 */
public class Cuenta {
    private int saldo;

    public Cuenta(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldo() {
        return saldo;
    }

    public void depositar(int monto) {
        saldo += monto;
    }
}
