package ar.uba.fi.cb100.material.i06_estrategias;

import java.math.BigInteger;

/**
 * Multiplicación rápida de <b>Karatsuba</b> (1960): un ejemplo de división y
 * conquista que multiplica dos números grandes en $O(n^{\log_2 3}) \approx
 * O(n^{1.585})$, mejor que el $O(n^2)$ de la multiplicación escolar.
 * <p>
 * Idea: partiendo cada número en dos mitades ($x = a\cdot 10^m + b$), el producto
 * se arma con <b>tres</b> multiplicaciones de la mitad de tamaño (no cuatro),
 * reusando $(a+b)(c+d)$. Recurrencia $T(n)=3\,T(n/2)+O(n)$ → Teorema Maestro
 * (Caso 1) → $O(n^{\log_2 3})$.
 */
public class Karatsuba {

    private static final BigInteger DIEZ = BigInteger.TEN;

    public static BigInteger multiplicar(BigInteger x, BigInteger y) {
        // Caso base: números chicos, multiplicación directa.
        if (x.bitLength() <= 32 || y.bitLength() <= 32) {
            return x.multiply(y);
        }
        int m = Math.max(x.bitLength(), y.bitLength()) / 2;
        BigInteger potencia = BigInteger.TWO.pow(m);

        BigInteger b = x.mod(potencia);          BigInteger a = x.divide(potencia);
        BigInteger d = y.mod(potencia);          BigInteger c = y.divide(potencia);

        BigInteger ac = multiplicar(a, c);
        BigInteger bd = multiplicar(b, d);
        BigInteger abcd = multiplicar(a.add(b), c.add(d));       // la 3.ª multiplicación
        BigInteger medio = abcd.subtract(ac).subtract(bd);       // ad + bc

        // resultado = ac·2^(2m) + (ad+bc)·2^m + bd
        return ac.shiftLeft(2 * m).add(medio.shiftLeft(m)).add(bd);
    }

    public static void main(String[] args) {
        BigInteger x = new BigInteger("31415926535897932384626433");
        BigInteger y = new BigInteger("27182818284590452353602874");
        BigInteger porKaratsuba = multiplicar(x, y);
        boolean correcto = porKaratsuba.equals(x.multiply(y));
        System.out.println("¿coincide con la multiplicación directa? " + correcto);
        System.out.println(porKaratsuba);
    }
}
