package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial2;

/**
 * Programa principal para testear el TDA Tostadora.
 * Cada caso de uso imprime lo esperado y lo obtenido, y los casos de error
 * verifican que se lance la excepción correspondiente.
 */
public class Principal {

    public static void main(String[] args) {

        caso01ConstruirTostadora();
        caso02ConstruirTostadoraConCantidadInvalida();
        caso03IndicarNivelDeCalor();
        caso04IndicarNivelDeCalorFueraDeRango();
        caso05IniciarTostadoEnVariasRanuras();
        caso06IniciarTostadoEnRanuraOcupada();
        caso07IniciarTostadoEnRanuraInexistente();
        caso08FinalizarTostado();
        caso09FinalizarTostadoEnRanuraLibre();
        caso10RanuraMasUsada();
        caso11RanuraMasUsadaSinUso();
        caso12CicloCompletoDeUso();

        System.out.println();
        System.out.println("Fin de los casos de uso.");
    }

//CASOS DE USO --------------------------------------------------------------------------------------------

    /**
     * Construir una tostadora de 4 ranuras: queda apagada, con nivel de calor 1 y todas las ranuras libres.
     */
    private static void caso01ConstruirTostadora() {
        titulo("Caso 01: construir una tostadora de 4 ranuras");

        Tostadora tostadora = new Tostadora(4);

        verificar("estado inicial", EstadoTostadora.APAGADA, tostadora.getEstado());
        verificar("nivel de calor inicial", 1, tostadora.getNivelDeCalor());
        verificar("ranuras libres", 4, tostadora.getCantidadDeRanurasLibres());
        verificar("ranura 1 libre", true, tostadora.getRanura(1).estaLibre());
        verificar("ranura 4 libre", true, tostadora.getRanura(4).estaLibre());
        verificar("panes tostados en ranura 1", 0, tostadora.getRanura(1).getCantidadDePanesTostados());
    }

    /**
     * Construir con 0 o con cantidad negativa de ranuras debe fallar.
     */
    private static void caso02ConstruirTostadoraConCantidadInvalida() {
        titulo("Caso 02: construir con cantidad de ranuras inválida");

        verificarExcepcion("construir con 0 ranuras", () -> new Tostadora(0));
        verificarExcepcion("construir con -3 ranuras", () -> new Tostadora(-3));
    }

    /**
     * Indicar el nivel de calor dentro del rango 1..10.
     */
    private static void caso03IndicarNivelDeCalor() {
        titulo("Caso 03: indicar nivel de calor válido");

        Tostadora tostadora = new Tostadora(2);

        tostadora.indicarNivelDeCalor(7);
        verificar("nivel de calor 7", 7, tostadora.getNivelDeCalor());

        tostadora.indicarNivelDeCalor(1);
        verificar("nivel de calor mínimo (1)", 1, tostadora.getNivelDeCalor());

        tostadora.indicarNivelDeCalor(10);
        verificar("nivel de calor máximo (10)", 10, tostadora.getNivelDeCalor());
    }

    /**
     * Indicar un nivel de calor fuera de rango debe fallar y no modificar el nivel actual.
     */
    private static void caso04IndicarNivelDeCalorFueraDeRango() {
        titulo("Caso 04: indicar nivel de calor fuera de rango");

        Tostadora tostadora = new Tostadora(2);
        tostadora.indicarNivelDeCalor(5);

        verificarExcepcion("nivel 0", () -> tostadora.indicarNivelDeCalor(0));
        verificarExcepcion("nivel 11", () -> tostadora.indicarNivelDeCalor(11));
        verificarExcepcion("nivel -4", () -> tostadora.indicarNivelDeCalor(-4));
        verificar("el nivel no cambió", 5, tostadora.getNivelDeCalor());
    }

    /**
     * Iniciar tostado en dos ranuras distintas: ambas quedan ocupadas y bajan las libres.
     */
    private static void caso05IniciarTostadoEnVariasRanuras() {
        titulo("Caso 05: iniciar tostado en varias ranuras");

        Tostadora tostadora = new Tostadora(4);

        tostadora.iniciarTostado(1);
        verificar("ranuras libres luego de ocupar la 1", 3, tostadora.getCantidadDeRanurasLibres());
        verificar("ranura 1 ocupada", EstadoRanura.OCUPADA, tostadora.getRanura(1).getEstado());

        tostadora.iniciarTostado(3);
        verificar("ranuras libres luego de ocupar la 3", 2, tostadora.getCantidadDeRanurasLibres());
        verificar("ranura 3 ocupada", EstadoRanura.OCUPADA, tostadora.getRanura(3).getEstado());
        verificar("ranura 2 sigue libre", true, tostadora.getRanura(2).estaLibre());
        verificar("ranura 4 sigue libre", true, tostadora.getRanura(4).estaLibre());
    }

    /**
     * Iniciar tostado en una ranura que ya está ocupada debe fallar.
     */
    private static void caso06IniciarTostadoEnRanuraOcupada() {
        titulo("Caso 06: iniciar tostado en ranura ocupada");

        Tostadora tostadora = new Tostadora(2);
        tostadora.iniciarTostado(2);

        verificarExcepcion("iniciar de nuevo en la ranura 2", () -> tostadora.iniciarTostado(2));
        verificar("ranuras libres sin cambios", 1, tostadora.getCantidadDeRanurasLibres());
    }

    /**
     * Iniciar o finalizar tostado en un número de ranura inexistente debe fallar.
     */
    private static void caso07IniciarTostadoEnRanuraInexistente() {
        titulo("Caso 07: ranura inexistente");

        Tostadora tostadora = new Tostadora(3);

        verificarExcepcion("iniciar en ranura 0", () -> tostadora.iniciarTostado(0));
        verificarExcepcion("iniciar en ranura 4", () -> tostadora.iniciarTostado(4));
        verificarExcepcion("finalizar en ranura 0", () -> tostadora.finalizarTostado(0));
        verificarExcepcion("finalizar en ranura 99", () -> tostadora.finalizarTostado(99));
        verificarExcepcion("obtener ranura -1", () -> tostadora.getRanura(-1));
        verificar("ranuras libres sin cambios", 3, tostadora.getCantidadDeRanurasLibres());
    }

    /**
     * Finalizar tostado: la ranura vuelve a estar libre y suma un pan tostado.
     */
    private static void caso08FinalizarTostado() {
        titulo("Caso 08: finalizar tostado");

        Tostadora tostadora = new Tostadora(3);
        tostadora.iniciarTostado(2);
        tostadora.iniciarTostado(3);

        tostadora.finalizarTostado(2);
        verificar("ranura 2 libre", true, tostadora.getRanura(2).estaLibre());
        verificar("panes tostados en ranura 2", 1, tostadora.getRanura(2).getCantidadDePanesTostados());
        verificar("ranura 3 sigue ocupada", EstadoRanura.OCUPADA, tostadora.getRanura(3).getEstado());
        verificar("ranuras libres", 2, tostadora.getCantidadDeRanurasLibres());

        tostadora.finalizarTostado(3);
        verificar("ranuras libres al terminar todo", 3, tostadora.getCantidadDeRanurasLibres());
        verificar("panes tostados en ranura 3", 1, tostadora.getRanura(3).getCantidadDePanesTostados());
        verificar("panes tostados en ranura 1 (nunca usada)", 0, tostadora.getRanura(1).getCantidadDePanesTostados());
    }

    /**
     * Finalizar tostado en una ranura libre debe fallar y no contar panes.
     */
    private static void caso09FinalizarTostadoEnRanuraLibre() {
        titulo("Caso 09: finalizar tostado en ranura libre");

        Tostadora tostadora = new Tostadora(2);

        verificarExcepcion("finalizar ranura 1 sin haber iniciado", () -> tostadora.finalizarTostado(1));
        verificar("panes tostados en ranura 1", 0, tostadora.getRanura(1).getCantidadDePanesTostados());

        tostadora.iniciarTostado(1);
        tostadora.finalizarTostado(1);
        verificarExcepcion("finalizar ranura 1 dos veces seguidas", () -> tostadora.finalizarTostado(1));
        verificar("panes tostados en ranura 1 sigue en 1", 1, tostadora.getRanura(1).getCantidadDePanesTostados());
    }

    /**
     * Ranura más usada: se tuesta 3 veces en la ranura 2, 1 vez en la 1 y 2 veces en la 4.
     */
    private static void caso10RanuraMasUsada() {
        titulo("Caso 10: ranura que más panes tostó");

        Tostadora tostadora = new Tostadora(4);

        tostar(tostadora, 1, 1);
        tostar(tostadora, 2, 3);
        tostar(tostadora, 4, 2);

        verificar("ranura más usada", 2, tostadora.getRanuraMasUsada());
        verificar("panes tostados en ranura 2", 3, tostadora.getRanura(2).getCantidadDePanesTostados());

        // la ranura 4 pasa a 4 panes y debe desplazar a la 2
        tostar(tostadora, 4, 2);
        verificar("ranura más usada luego de usar más la 4", 4, tostadora.getRanuraMasUsada());

        // empate entre la 2 y la 4 (4 panes cada una): se queda con la primera encontrada
        tostar(tostadora, 2, 1);
        verificar("ranura más usada con empate 2 y 4", 2, tostadora.getRanuraMasUsada());
    }

    /**
     * Ranura más usada en una tostadora sin uso: todas tienen 0 panes, se devuelve la primera.
     */
    private static void caso11RanuraMasUsadaSinUso() {
        titulo("Caso 11: ranura más usada sin haber tostado nada");

        Tostadora tostadora = new Tostadora(3);

        verificar("ranura más usada sin uso", 1, tostadora.getRanuraMasUsada());

        // una ranura ocupada pero sin finalizar todavía no cuenta como pan tostado
        tostadora.iniciarTostado(3);
        verificar("ranura más usada con una ocupada sin terminar", 1, tostadora.getRanuraMasUsada());
    }

    /**
     * Ciclo completo: configurar calor, ocupar todas las ranuras, liberarlas de a una y reutilizarlas.
     */
    private static void caso12CicloCompletoDeUso() {
        titulo("Caso 12: ciclo completo de uso");

        Tostadora tostadora = new Tostadora(2);
        tostadora.indicarNivelDeCalor(6);

        tostadora.iniciarTostado(1);
        tostadora.iniciarTostado(2);
        verificar("sin ranuras libres", 0, tostadora.getCantidadDeRanurasLibres());
        verificarExcepcion("no se puede iniciar en ninguna ranura", () -> tostadora.iniciarTostado(1));

        tostadora.finalizarTostado(1);
        verificar("una ranura libre", 1, tostadora.getCantidadDeRanurasLibres());

        tostadora.iniciarTostado(1);
        tostadora.finalizarTostado(1);
        tostadora.finalizarTostado(2);
        verificar("todas libres al final", 2, tostadora.getCantidadDeRanurasLibres());
        verificar("panes tostados en ranura 1", 2, tostadora.getRanura(1).getCantidadDePanesTostados());
        verificar("panes tostados en ranura 2", 1, tostadora.getRanura(2).getCantidadDePanesTostados());
        verificar("ranura más usada", 1, tostadora.getRanuraMasUsada());
        verificar("nivel de calor se mantiene", 6, tostadora.getNivelDeCalor());
    }

//METODOS AUXILIARES --------------------------------------------------------------------------------------

    /**
     * Tuesta la cantidad de panes indicada en la ranura, iniciando y finalizando cada vez.
     */
    private static void tostar(Tostadora tostadora, int numeroDeRanura, int cantidadDePanes) {
        for (int i = 0; i < cantidadDePanes; i++) {
            tostadora.iniciarTostado(numeroDeRanura);
            tostadora.finalizarTostado(numeroDeRanura);
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto);
    }

    /**
     * Compara lo esperado con lo obtenido e imprime OK o ERROR.
     */
    private static void verificar(String descripcion, Object esperado, Object obtenido) {
        boolean ok = esperado.equals(obtenido);
        System.out.println((ok ? "  [OK]    " : "  [ERROR] ") + descripcion
                + " -> esperado: " + esperado + ", obtenido: " + obtenido);
    }

    /**
     * Ejecuta la acción y verifica que lance IllegalArgumentException.
     */
    private static void verificarExcepcion(String descripcion, Runnable accion) {
        try {
            accion.run();
            System.out.println("  [ERROR] " + descripcion + " -> se esperaba una excepción y no se lanzó");
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK]    " + descripcion + " -> " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("  [ERROR] " + descripcion + " -> excepción inesperada: " + e);
        }
    }
}
