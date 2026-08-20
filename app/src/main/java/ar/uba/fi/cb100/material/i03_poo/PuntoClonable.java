package ar.uba.fi.cb100.material.i03_poo;

/**
 * Ejemplo de {@code clone()} implementando {@link Cloneable}.
 * <p>
 * Nota: {@code clone} es delicado (hace copia superficial y su diseño es
 * discutido). En la práctica se prefiere un <b>constructor copia</b> como el de
 * {@link Fraccion}. Lo mostramos porque forma parte de los métodos estándar.
 */
public class PuntoClonable implements Cloneable {

    public int x;
    public int y;

    public PuntoClonable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public PuntoClonable clone() {
        try {
            return (PuntoClonable) super.clone();   // copia superficial campo a campo
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);            // no puede pasar: implementamos Cloneable
        }
    }

    public static void main(String[] args) {
        PuntoClonable p = new PuntoClonable(1, 2);
        PuntoClonable copia = p.clone();
        copia.x = 99;
        System.out.println("original: " + p.x + "," + p.y
                + "   clon: " + copia.x + "," + copia.y);   // original: 1,2  clon: 99,2
    }
}
