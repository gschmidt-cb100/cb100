package ar.uba.fi.cb100.clases.a2026.c02.s01;

import ar.uba.fi.cb100.material.i01_intro.DivisionPorCeroException;
import ar.uba.fi.cb100.material.i01_intro.Rectangulo;

public class HolaMundo {


    public static boolean esPar1(int n) {
        return n % 2 == 0;
    }

    public static boolean esPar1(int n, int a) {
        return true;
    }

    public static boolean esPar1(char n) {
        return false;
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
        try {
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
                default:
                    System.out.println("Hola mundo mayor a 5");
            }


            System.out.println("El 4 es par: " + HolaMundo.esPar1(4));


            {
                Rectangulo r = new Rectangulo(); // se crea el objeto
                r.base = 3; // se asignan sus campos
                r.altura = 4;
                System.out.println(r.area());

                Rectangulo r2 = r;
                r2.base = 10;
                HolaMundo.swap(r2);
                swap(r2);

                Rectangulo r1 = new Rectangulo(); // se crea el objeto
                r1.base = 5; // se asignan sus campos
                r1.altura = 6;
                System.out.println(r1.area());


            }

            metodo1(0);
            try {
                int j = 10;
                metodo1(j);
                System.out.println("no se llega aqui"); //No se ejecuta
            } catch (DivisionPorCeroException d) {
                System.out.println("se captura la excepcion");
            } catch (RuntimeException d) {
                System.out.println("se captura la excepcion RuntimeException");
            } finally {
                System.out.println("se ejecuta siempre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        metodo1(0);
    }

    private static void metodo1(int j) {
        metodo2(j+1);
        System.out.println("no se llega aqui"); //No se ejecuta
    }

    private static void metodo2(int j) {
        metodo3(j+1);
        System.out.println("no se llega aqui"); //No se ejecuta
    }

    private static void metodo3(int j) {
        if (j >= 5) {
            throw new DivisionPorCeroException(0);
        }
        if (j >= 2) {
            throw new RuntimeException("error");
        }
    }

    public static void swap(Rectangulo r) {
        double temp = r.base;
        r.base = r.altura;
        r.altura = temp;
        r.area();
    }
}
