package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e01;

import java.util.ArrayList;
import java.util.List;

/**
 * TECNICA: BACKTRACKING (busqueda exhaustiva con construccion incremental).
 *
 * Problema clasico: con los digitos 1..9 EN ESE ORDEN, y eligiendo entre
 * cada par de digitos consecutivos un operador de {+, -, concatenacion},
 * encontrar todas las expresiones cuyo valor es un objetivo dado
 * (el caso famoso es objetivo = 100, que tiene exactamente 11 soluciones).
 *
 * Por que backtracking y no fuerza bruta "a ciegas": el espacio de
 * decisiones es un arbol ternario de profundidad 8 (una decision entre
 * cada par de digitos), o sea 3^8 = 6561 hojas. Lo recorremos en
 * profundidad construyendo la expresion DE A UN DIGITO, y al volver de
 * cada rama "deshacemos" la decision (aca el deshacer es implicito:
 * pasamos el estado por parametro, asi cada rama tiene su propia copia).
 *
 * El truco de la evaluacion incremental: para no parsear el String al
 * final, llevamos dos numeros:
 *  - total: el valor de toda la expresion armada hasta ahora, y
 *  - ultimoTermino: el valor CON SIGNO del ultimo termino.
 * Al concatenar un digito d, el ultimo termino t se convierte en
 * t*10 + d (si t >= 0) o t*10 - d (si t < 0), y el total se corrige
 * restando el termino viejo y sumando el nuevo. Asi cada nodo del arbol
 * se procesa en O(1).
 *
 * Complejidad: O(3^8) nodos, cada uno O(1) de calculo mas el armado del
 * String, que es O(largo) solo en las hojas que dan el objetivo.
 */
public class GeneradorDeExpresiones {

    /**
     * Devuelve todas las expresiones con los digitos 1..9 en orden y
     * operadores {+, -, concatenacion} cuyo valor es {@code objetivo}.
     * Las expresiones se escriben sin espacios, por ejemplo
     * "1+2+3-4+5+6+78+9".
     */
    public List<String> expresionesQueDan(int objetivo) {
        List<String> resultado = new ArrayList<>();
        // Arrancamos con el digito 1 ya colocado: expresion "1",
        // total 1 y ultimo termino 1. El proximo digito a decidir es el 2.
        generar(2, "1", 1, 1, objetivo, resultado);
        return resultado;
    }

    /**
     * Un nodo del arbol de decisiones: ya usamos los digitos 1..digito-1.
     *
     * @param digito        proximo digito a incorporar (2..9); si es 10 ya no quedan.
     * @param expresion     texto armado hasta ahora.
     * @param total         valor de la expresion armada.
     * @param ultimoTermino valor con signo del ultimo termino (para poder concatenar).
     */
    private void generar(int digito, String expresion, long total, long ultimoTermino,
                         int objetivo, List<String> resultado) {
        if (digito > 9) {
            // Hoja del arbol: la expresion esta completa, la evaluamos (ya esta evaluada).
            if (total == objetivo) {
                resultado.add(expresion);
            }
            return;
        }
        // Decision 1: sumar el digito como termino nuevo.
        generar(digito + 1, expresion + "+" + digito, total + digito, digito, objetivo, resultado);
        // Decision 2: restar el digito como termino nuevo.
        generar(digito + 1, expresion + "-" + digito, total - digito, -digito, objetivo, resultado);
        // Decision 3: concatenar el digito al ultimo termino.
        long terminoNuevo = ultimoTermino >= 0
                ? ultimoTermino * 10 + digito
                : ultimoTermino * 10 - digito;
        generar(digito + 1, expresion + digito, total - ultimoTermino + terminoNuevo,
                terminoNuevo, objetivo, resultado);
    }

    /** Demostracion: las 11 expresiones que dan 100. */
    public static void main(String[] args) {
        GeneradorDeExpresiones generador = new GeneradorDeExpresiones();
        List<String> soluciones = generador.expresionesQueDan(100);
        System.out.println("Expresiones con 1..9 que dan 100: " + soluciones.size());
        for (String expresion : soluciones) {
            System.out.println("  " + expresion + " = 100");
        }
    }
}
