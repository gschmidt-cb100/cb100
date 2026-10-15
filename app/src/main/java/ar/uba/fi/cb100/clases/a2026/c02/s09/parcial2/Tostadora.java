package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial2;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

public class Tostadora {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final int NIVEL_DE_CALOR_MINIMO = 1;
    private static final int NIVEL_DE_CALOR_MAXIMO = 10;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int nivelDeCalor;
    private Ranura[] ranuras;
    private EstadoTostadora estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    // constructor para la tostadora
    public Tostadora(int ranuras) {
        ValidacionesUtiles.esMayorQueCero(ranuras, "La cantidad de ranuras debe ser mayor que cero.");
        this.ranuras = new Ranura[ranuras];
        this.nivelDeCalor = 1;
        this.estado = EstadoTostadora.APAGADA;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Indica el nivel de calor de la tostadora.
     * @param nivelDeCalor: el nivel de calor a establecer (debe estar entre 1 y 10).
     */
    public void indicarNivelDeCalor(int nivelDeCalor) {
        ValidacionesUtiles.validarRango(nivelDeCalor, NIVEL_DE_CALOR_MINIMO, NIVEL_DE_CALOR_MAXIMO, "El nivel de calor debe estar entre 1 y 10.");
        this.nivelDeCalor = nivelDeCalor;
    }

    /**
     * Inicia el proceso de tostado en la ranura especificada.
     * @param numeroDeRanura: el número de la ranura en la que se desea iniciar el tostado (debe estar entre 1 y la cantidad de ranuras).
     * @throws IllegalArgumentException si el número de ranura está fuera del rango permitido o si la ranura no está libre.
     */
    public void iniciarTostado(int numeroDeRanura) {
        ValidacionesUtiles.validarRango(numeroDeRanura, 1, this.ranuras.length, "El número de ranura debe estar entre 1 y " + this.ranuras.length + ".");
        this.ranuras[numeroDeRanura - 1].iniciarTostado();
    }

    /**
     * Finaliza el proceso de tostado en la ranura especificada.
     * @param numeroDeRanura: el número de la ranura en la que se desea finalizar el tostado (debe estar entre 1 y la cantidad de ranuras).
     * @throws IllegalArgumentException si el número de ranura está fuera del rango permitido o
     */
    public void finalizarTostado(int numeroDeRanura) {
        ValidacionesUtiles.validarRango(numeroDeRanura, 1, this.ranuras.length, "El número de ranura debe estar entre 1 y " + this.ranuras.length + ".");
        this.ranuras[numeroDeRanura - 1].finalizarTostado();
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve el nivel de calor de la tostadora.
     * @return el nivel de calor de la tostadora.
     */
    public int getNivelDeCalor() {
        return nivelDeCalor;
    }

    /**
     * Devuelve el estado de la tostadora.
     * @return el estado de la tostadora.
     */
    public EstadoTostadora getEstado() {
        return estado;
    }

    /**
     * Cuenta la cantidad de ranuras libres en la tostadora.
     * @return la cantidad de ranuras libres.
     */
    public int getCantidadDeRanurasLibres() {
        int resultado = 0;
        for(Ranura ranura: this.ranuras) {
            if (ranura.estaLibre()) {
                resultado++;
            }
        }
        return resultado;
    }

    /**
     * Devuelve el número de ranura más usada, es decir, la ranura que ha tostado más panes.
     * @return  el número de ranura más usada, o 0 si no hay ranuras. En caso de empate, devuelve la primera ranura más usada.
     */
    public int getRanuraMasUsada() {
        Ranura ranuraMasUsada = null;
        int numero = 0;
        for(int i = 0; i < this.ranuras.length; i++) {
            Ranura ranura = this.ranuras[i];
            if (ranuraMasUsada == null ||
                ranura.getCantidadDePanesTostados() > ranuraMasUsada.getCantidadDePanesTostados()) {
                ranuraMasUsada = ranura;
                numero = i;
            }
        }
        return numero + 1;
    }

    /**
     * Devuelve la ranura correspondiente al número de ranura especificado.
     * @param numeroDeRanura:   el número de la ranura que se desea obtener (debe estar entre 1 y la cantidad de ranuras).
     * @return la ranura correspondiente al número de ranura especificado.
     * @throws IllegalArgumentException si el número de ranura está fuera del rango permitido.
     */
    public Ranura getRanura(int numeroDeRanura) {
        ValidacionesUtiles.validarRango(numeroDeRanura, 1, this.ranuras.length, "El número de ranura debe estar entre 1 y " + this.ranuras.length + ".");
        return this.ranuras[numeroDeRanura - 1];
    }

}
