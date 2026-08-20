package ar.uba.fi.cb100.material.i01_intro;

/**
 * Estructuras de control en Java: condicionales, ciclos y arreglos.
 * Ya conocés estas ideas de programación; acá ves su sintaxis en Java.
 */
public class ControlDeFlujo {

    public static void main(String[] args) {

        // --- if / else if / else ---
        int nota = 7;
        if (nota >= 8) {
            System.out.println("distinguido");
        } else if (nota >= 4) {
            System.out.println("aprobado");
        } else {
            System.out.println("insuficiente");
        }

        // --- switch expression (moderno): devuelve un valor ---
        String dia = "L";
        String nombre = switch (dia) {
            case "L", "M", "X", "J", "V" -> "día hábil";
            case "S", "D" -> "fin de semana";
            default -> "desconocido";
        };
        System.out.println("El día es un " + nombre);

        // --- for clásico ---
        int suma = 0;
        for (int i = 1; i <= 5; i++) {
            suma += i;                       // 1 + 2 + 3 + 4 + 5
        }
        System.out.println("suma 1..5 = " + suma);

        // --- while y do-while ---
        int cuenta = 3;
        while (cuenta > 0) {
            System.out.print(cuenta + " ");
            cuenta--;
        }
        System.out.println("¡despegue!");

        // --- Arreglos y for-each ---
        int[] numeros = {4, 8, 15, 16, 23, 42};
        int total = 0;
        for (int n : numeros) {              // "para cada n en numeros"
            total += n;
        }
        System.out.println("total del arreglo = " + total
                + " (largo " + numeros.length + ")");

        // --- Matriz (arreglo de arreglos) ---
        int[][] tablero = { {1, 2}, {3, 4} };
        System.out.println("tablero[1][0] = " + tablero[1][0]);   // 3
    }
}
