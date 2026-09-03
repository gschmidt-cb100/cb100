package ar.uba.fi.cb100.clases.a2026.c02.s03.herencia;

public abstract class Animal {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private String nombre;
    //protected String nombreProtegido;
    private int cantidadDePatas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public Animal() {
        this.setNombre("");
    }

    public Animal(String nombre) {
        this.setNombre(nombre);
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    public abstract String sonido();

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public int getCantidadDePatas() {
        return cantidadDePatas;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidadDePatas(int cantidadDePatas) {
        this.cantidadDePatas = cantidadDePatas;
    }
}
