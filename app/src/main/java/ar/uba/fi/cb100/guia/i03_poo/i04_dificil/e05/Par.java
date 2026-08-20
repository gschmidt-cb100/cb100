package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e05;

/**
 * Par ordenado inmutable de dos valores (posiblemente de distinto tipo).
 * Al ser un {@code record}, Java genera automáticamente los accesores,
 * {@code equals}, {@code hashCode} y {@code toString} basados en sus
 * componentes.
 *
 * @param <A> tipo del primer componente
 * @param <B> tipo del segundo componente
 */
public record Par<A, B>(A primero, B segundo) {

    public static void main(String[] args) {
        Par<String, Integer> p = new Par<>("edad", 30);
        System.out.println("toString: " + p);
        System.out.println("primero:  " + p.primero());
        System.out.println("segundo:  " + p.segundo());

        Par<String, Integer> igual = new Par<>("edad", 30);
        Par<String, Integer> distinto = new Par<>("edad", 31);
        System.out.println("equals con igual:    " + p.equals(igual));
        System.out.println("equals con distinto: " + p.equals(distinto));
        System.out.println("hashCode consistente: " + (p.hashCode() == igual.hashCode()));
    }
}
