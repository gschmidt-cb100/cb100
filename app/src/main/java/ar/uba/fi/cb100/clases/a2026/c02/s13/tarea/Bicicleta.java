package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea;

import java.util.Objects;

//INVARIANTE: numeroDeSerie no null ni vacio, kms >= 0 y aumenta, cantUso >= 0 y aumenta.
public class Bicicleta {

    //ATRIBUTOS!
    private final String numeroDeSerie;
    private double kms;
    private int cantUso;
    private EstadoBicicleta estado;

    //CONSTRUCTOR!
    /** Firma: public Bicicleta(String numeroDeSerie)
    Pre: numeroDeSerie no null ni vacio.
    Post: Se crea una bicicleta con el numero de serie indicado, kms = 0, cantUso = 0 y estado = EstadoBicicleta.PARA_USO.
    @Param numeroDeSerie numero de serie de la bicicleta no null ni vacio
    @Throws IllegalArgumentException si numero de serie null o vacio
    */
    public Bicicleta(String numeroDeSerie) {
        if (numeroDeSerie == null || numeroDeSerie.isEmpty()) {
            throw new IllegalArgumentException("El numero de serie es invalido");
        }
        this.numeroDeSerie = numeroDeSerie;
        this.kms = 0;
        this.cantUso = 0;
        this.estado = EstadoBicicleta.PARA_USO;
    }

    //SUMAR KMS!
    /** Firma: public void agregarKms(double kms)
    Pre: kms > 0.
    Post: Suma esos kms a la bicicleta.
    @Param kms los kilometros a sumar a la bicicleta, mayores a 0.
    @Throws IllegalArgumentException si kms <= 0.
    */
    public void agregarKms(double kms) {
        if (kms <= 0) { throw new IllegalArgumentException("kms debe ser mayor a 0."); }
        this.kms += kms;
    }

    //SUMAR USO!
    /** Firma: public void sumarUso()
    Pre: No hay
    Post: Se suma un uso a la bicicleta
    */
    public void sumarUso() { this.cantUso++; }

    //CAMBIAR ESTADO!
    /** Firma: public void cambiarEstado(EstadoBicicleta nuevoEstado)
    Pre: nuevoEstado != null
    Post: Cambia el estado de la bicicleta por el indicado
    @Param nuevoEstado el estado que toma la bicicleta != null.
    @Throws IllegalArgumentException si nuevoEstado es null.
    */
    public void cambiarEstado(EstadoBicicleta nuevoEstado) {
        if (nuevoEstado == null) { throw new IllegalArgumentException("El estado no puede ser null"); }
        this.estado = nuevoEstado;
    }

    //GETTERS!
    //Devuelve el numero de serie de la bicicleta
    public String getNumeroDeSerie() { return this.numeroDeSerie; }

    //Devuelve los kms de la bicicleta
    public double getKms() { return this.kms; }

    //Devuelve la cantidad de usos de la bicicleta
    public int getCantUso() { return this.cantUso; }

    //Devuelve el estado de la bicicleta
    public EstadoBicicleta getEstado() { return this.estado; }

    //CONSULTA SI LA BICICLETA ESTA EN REPARACION!
    //Devuelve true si esta en reparacion
    public boolean enReparacion() { return this.estado == EstadoBicicleta.REPARACION; }

    //METODOS GENERALES!
    //Dos bicicletas son iguales si tienen el mismo numero de serie
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Bicicleta otra = (Bicicleta) o;
        return this.numeroDeSerie.equals(otra.numeroDeSerie);
    }

    @Override
    public int hashCode() { return Objects.hash(numeroDeSerie); }

    @Override
    public String toString() {
        return "Bicicleta " + numeroDeSerie + " (" + kms + " km, " + cantUso + " usos, " + estado + ")";
    }
}
