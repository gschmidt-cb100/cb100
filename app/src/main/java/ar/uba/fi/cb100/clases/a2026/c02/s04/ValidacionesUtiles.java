package ar.uba.fi.cb100.clases.a2026.c02.s04;

public class ValidacionesUtiles {

    /**
     * Valida que el número sea mayor que cero o lanza una excepción con un mensaje que incluye los textos proporcionados.
     * @param numero: el número a validar
     * @param textos: los textos que se incluirán en el mensaje de la excepción si el número no es mayor que cero
     * @throws IllegalArgumentException si el número no es mayor que cero
     */
    public static void esMayorQueCero(int numero, String... textos) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número " + toString(textos) + " debe ser mayor que cero.");
        }
    }

    /**
     * Convierte un arreglo de textos en una cadena de texto separada por espacios.
     * @param textos: los textos a convertir
     * @return una cadena de texto que contiene los textos separados por espacios, precedida por " de "
     */
    private static String toString(String ... textos) {
        String temp = "";
        if (textos != null) {
            for(String texto: textos) {
                temp += texto + " ";
            }
        }
        return " de " + temp.trim();
    }

    public static void validarDistintoDeNull(Object o, String... nombreVariable) {
        if (o == null) {
            throw new IllegalArgumentException("El TDA " + toString(nombreVariable) + " debe ser distinto de null");
        }
    }

    public static void validarRango(int numero, int rangoInferior, int rangoSuperior, String... nombreVariable) {
        if (numero < rangoInferior ||
            numero > rangoSuperior) {
            throw new IllegalArgumentException("El número " + toString(nombreVariable) + " debe estar entre " + rangoInferior + " y " + rangoSuperior + ".");
        }
    }

    /**
     * Valida que el valor booleano sea falso. Si es verdadero, lanza una excepción con el mensaje proporcionado.
     * @param valor: el valor booleano a validar
     * @param texto: el mensaje de la excepción si el valor es verdadero
     * @throws IllegalArgumentException si el valor es verdadero
     */
    public static void validarFalso(boolean valor, String texto) {
        if (valor) {
            throw new IllegalArgumentException(texto);
        }
    }

}
