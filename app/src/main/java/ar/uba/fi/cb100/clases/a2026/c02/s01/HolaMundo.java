package ar.uba.fi.cb100.clases.a2026.c02.s01;

public class HolaMundo {


    public static boolean esPar1(int n) {
        return n % 2 == 0;
    }

    public static boolean esPar2(int n) {
        if (n % 2 == 0) {
            return true;
        }
        return false;
    }

    public static boolean esPar3(int n) {
        boolean resultado = false;
        if (n % 2 == 0) {
            resultado = true;
        }
        return resultado;
    }

    public static void main() {
        int i = 2;
        switch (i) {
            case 1:
                System.out.println("Hola mundo 1");
                break;
            case 2:
                System.out.println("Hola mundo 2");
                break;
            case 3:
                System.out.println("continua...");
            case 4:
            case 5:
                System.out.println("Hola mundo mayor a 3");
                break;
            default: System.out.println("Hola mundo mayor a 5");
        }
    }
}
