package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e05;

/**
 * Dos metodos clasicos de hashing para claves numericas:
 *
 * CUADRADO MEDIO: se eleva la clave al cuadrado y se toman los digitos del
 * medio del resultado. La gracia es que los digitos centrales del cuadrado
 * dependen de TODOS los digitos de la clave, asi que mezclan mejor que los
 * extremos.
 *
 * EXTRACCION: se toman algunos digitos de la clave directamente, sin
 * transformarla. Es mas barato pero mezcla peor: digitos distintos de la
 * clave pueden tener distribuciones muy desparejas (por ejemplo, los
 * primeros digitos de un DNI o un anio).
 *
 * Ambos devuelven un numero que despues se reduce modulo la capacidad.
 */
public final class MetodosDeHash {

    private MetodosDeHash() {
        // Clase utilitaria: no se instancia.
    }

    /**
     * Hash por cuadrado medio: eleva la clave al cuadrado (en long, para no
     * desbordar) y devuelve los {@code digitos} digitos del medio.
     *
     * Ejemplo: cuadradoMedio(123, 2) -> 123^2 = 15129 -> digitos del medio
     * "51" -> 51.
     *
     * @param clave   clave numerica no negativa
     * @param digitos cantidad de digitos del medio a tomar (positiva)
     */
    public static int cuadradoMedio(int clave, int digitos) {
        if (clave < 0 || digitos <= 0) {
            throw new IllegalArgumentException(
                    "clave no negativa y digitos positivos: clave=" + clave
                            + " digitos=" + digitos);
        }
        long cuadrado = (long) clave * clave; // long: 46340^2 ya no entra en int.
        String texto = Long.toString(cuadrado);
        if (digitos >= texto.length()) {
            return (int) cuadrado; // No hay de donde recortar: va entero.
        }
        // Centro la ventana de 'digitos' sobre el cuadrado.
        int desde = (texto.length() - digitos) / 2;
        return Integer.parseInt(texto.substring(desde, desde + digitos));
    }

    /**
     * Hash por extraccion: toma {@code cuantos} digitos de la clave a partir
     * de la posicion {@code desde} (0-indexada, contando desde la izquierda).
     *
     * Ejemplo: extraccion(123, 0, 2) -> "12" -> 12.
     *
     * @param clave  clave numerica no negativa
     * @param desde  primer digito a tomar (0 = el de mas a la izquierda)
     * @param cuantos cantidad de digitos a tomar
     */
    public static int extraccion(int clave, int desde, int cuantos) {
        if (clave < 0) {
            throw new IllegalArgumentException("La clave debe ser no negativa: " + clave);
        }
        String texto = Integer.toString(clave);
        if (desde < 0 || cuantos <= 0 || desde + cuantos > texto.length()) {
            throw new IllegalArgumentException(
                    "Rango invalido: desde=" + desde + " cuantos=" + cuantos
                            + " para la clave " + clave);
        }
        return Integer.parseInt(texto.substring(desde, desde + cuantos));
    }

    /** Demostracion de ambos metodos con la misma clave. */
    public static void main(String[] args) {
        int clave = 123;
        System.out.println(clave + "^2 = " + (clave * clave));
        System.out.println("cuadradoMedio(123, 2) = " + cuadradoMedio(clave, 2));
        System.out.println("extraccion(123, 0, 2) = " + extraccion(clave, 0, 2));
        System.out.println("extraccion(20261234, 4, 4) = " + extraccion(20261234, 4, 4));
    }
}
