package ar.uba.fi.cb100.material.i01_intro;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Rangos de los tipos, desbordamiento (overflow), conversiones entre tipos,
 * wrappers, autoboxing y números grandes (BigInteger / BigDecimal).
 */
public class RangosYConversiones {

    public static void main(String[] args) {
        // --- Rangos de los tipos primitivos ---
        System.out.println("int:  " + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE);
        System.out.println("long: " + Long.MIN_VALUE + " .. " + Long.MAX_VALUE);

        // --- Overflow: pasarse del máximo "da la vuelta" a negativo ---
        int max = Integer.MAX_VALUE;
        System.out.println("max + 1 = " + (max + 1));   // ¡desborda!

        // --- Casting entre primitivos ---
        double d = 9.99;
        int truncado = (int) d;        // 9  (narrowing: se pierde el decimal)
        long l = truncado;             // widening automático

        // --- Wrappers y autoboxing ---
        Integer boxed = 42;            // autoboxing: int -> Integer
        int back = boxed;              // unboxing: Integer -> int

        // --- Conversión desde/hacia String ---
        int n = Integer.parseInt("123");
        double x = Double.parseDouble("3.14");
        String s = Integer.toString(255);
        String hex = Integer.toHexString(255);       // "ff"

        // --- Números grandes: sin límite y sin error de redondeo ---
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= 30; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        BigDecimal precio = new BigDecimal("19.99").multiply(new BigDecimal("3"));

        System.out.println("truncado=" + truncado + " l=" + l + " back=" + back);
        System.out.println("n=" + n + " x=" + x + " s=" + s + " hex=" + hex);
        System.out.println("30! = " + factorial);
        System.out.println("precio exacto = " + precio);
    }
}
