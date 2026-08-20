package ar.uba.fi.cb100.material.i02_memoria;

/**
 * EJEMPLO INTEGRADOR de la Unidad 2.
 * <p>
 * Muestra las consecuencias del aliasing sobre un objeto mutable, la diferencia
 * entre identidad ({@code ==}) y "hacer una copia", y por qué a veces conviene
 * copiar explícitamente.
 */
public class CuentaCompartida {

    /** Objeto mutable: su estado (saldo) cambia con el tiempo. */
    static class Cuenta {
        private double saldo;

        Cuenta(double saldo) { this.saldo = saldo; }

        void depositar(double monto) { saldo += monto; }

        double saldo() { return saldo; }
    }

    public static void main(String[] args) {
        Cuenta c = new Cuenta(1000);
        Cuenta mismaCuenta = c;          // ALIAS: NO es una copia, es la misma cuenta

        mismaCuenta.depositar(500);
        System.out.println("saldo visto por c: " + c.saldo());      // 1500 (¡cambió!)
        System.out.println("¿es el mismo objeto? " + (c == mismaCuenta));  // true

        // Si querés una cuenta independiente, copiá explícitamente el estado:
        Cuenta copia = new Cuenta(c.saldo());
        copia.depositar(9999);
        System.out.println("c no se afecta: " + c.saldo()
                + " / copia: " + copia.saldo());        // 1500.0 / 11499.0
    }
}
