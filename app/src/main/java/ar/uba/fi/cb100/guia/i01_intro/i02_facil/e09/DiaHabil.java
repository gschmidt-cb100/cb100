package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e09;

/**
 * Ejercicio 9 (facil): Clasificacion de dias.
 * L, M, X, J, V -> "habil"; S, D -> "fin de semana"; otro -> "desconocido".
 */
public class DiaHabil {

    private DiaHabil() {
    }

    /**
     * Clasifica un dia representado por su inicial.
     *
     * @param d inicial del dia (L, M, X, J, V, S, D)
     * @return "habil", "fin de semana" o "desconocido"
     */
    public static String tipoDeDia(char d) {
        return switch (d) {
            case 'L', 'M', 'X', 'J', 'V' -> "habil";
            case 'S', 'D' -> "fin de semana";
            default -> "desconocido";
        };
    }

    public static void main(String[] args) {
        System.out.println("L -> " + tipoDeDia('L'));
        System.out.println("S -> " + tipoDeDia('S'));
        System.out.println("Z -> " + tipoDeDia('Z'));
    }
}
