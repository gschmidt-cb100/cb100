package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea;

//INVARIANTE: anclajes.length > 0 y no cambia; una bicicleta no esta en dos anclajes.
public class EstacionDeBicicletas {

    //ATRIBUTOS!
    private final Bicicleta[] anclajes;

    //CONSTRUCTOR!
    /** Firma: public EstacionDeBicicletas(int cantAnclajes)
    Pre: cantAnclajes > 0
    Post: Se crea la estacion con la cantidad de anclajes del parametro, todos libres.
    @Param cantAnclajes la cantidad a usar para la creacion siendo mayor a 0.
    @Throws IllegalArgumentException si cantAnclajes <= 0.
    */
    public EstacionDeBicicletas(int cantAnclajes) {
        if (cantAnclajes <= 0) { throw new IllegalArgumentException("cantAnclajes debe ser > 0."); }
        this.anclajes = new Bicicleta[cantAnclajes];
    }

    //PRIMER ANCLAJE LIBRE!
    /** Firma: private int primerAnclajeLibre()
    Pre: No hay.
    Post: Devuelve la posicion (0..n-1) del primer anclaje libre o -1 si no hay.
    */
    private int primerAnclajeLibre() {
        for (int i = 0; i < this.anclajes.length; i++) {
            if (this.anclajes[i] == null) {
                return i;
            }
        }
        return -1;
    }

    //CONSULTA SI ESTA GUARDADA!
    /** Firma: private boolean estaGuardada(Bicicleta bicicleta)
    Pre: bicicleta no es null
    Post: devuelve true si esta guardada.
    @Param bicicleta la bicicleta no nula a buscar
    @Throws IllegalArgumentException si bicicleta es nula
    */
    private boolean estaGuardada(Bicicleta bicicleta) {
        if (bicicleta == null) { throw new IllegalArgumentException("La bicicleta no puede ser null"); }
        for (Bicicleta busqueda : anclajes) {
            if (bicicleta.equals(busqueda)) {
                return true;
            }
        }
        return false;
    }

    //DEVOLVER BICICLETA!
    /** Firma: public void guardar(Bicicleta bicicleta)
    Pre: bicicleta no es null, no esta guardada y hay espacio.
    Post: guarda la bicicleta en el primer anclaje libre.
    @Param bicicleta la bicicleta a guardar
    @Throws IllegalArgumentException si bicicleta es null
    @Throws IllegalStateException si la bicicleta ya esta guardada o no hay espacio.
    */
    public void guardar(Bicicleta bicicleta) {
        if (bicicleta == null) { throw new IllegalArgumentException("bicicleta no puede ser null"); }
        if (estaGuardada(bicicleta)) { throw new IllegalStateException("la bicicleta ya esta guardada"); }
        int libre = primerAnclajeLibre();
        if (libre == -1) { throw new IllegalStateException("no hay espacio para guardarla"); }
        this.anclajes[libre] = bicicleta;
    }

    //CANTIDAD ANCLAJES LIBRES!
    /** Firma: public int cantLibres()
    Pre: No hay
    Post: Devuelve la cantidad de anclajes libres
    */
    public int cantLibres() {
        int total = 0;
        for (int i = 0; i < this.anclajes.length; i++) {
            if (this.anclajes[i] == null) {
                total++;
            }
        }
        return total;
    }

    //RETIRAR BICICLETA!
    /** Firma: public Bicicleta retirar(int anclaje)
    Pre: la bicicleta no esta en reparacion, hay una bicicleta en ese anclaje, anclaje > 0 y <= this.anclajes.length.
    Post: devuelve esa bicicleta, el anclaje queda en null y suma un uso a la bicicleta.
    @Param anclaje el anclaje del cual se retira la bicicleta. anclaje > 0 y anclaje <= this.anclajes.length.
    @Throws IllegalArgumentException si anclaje <= 0 o anclaje > this.anclajes.length.
    @Throws IllegalStateException si la bicicleta esta en reparacion o no hay bicicleta en ese anclaje.
    */
    public Bicicleta retirar(int anclaje) {
        if (anclaje < 1 || anclaje > this.anclajes.length) {
            throw new IllegalArgumentException("el anclaje a buscar es invalido");
        }
        Bicicleta bicicleta = this.anclajes[anclaje - 1];
        if (bicicleta == null) { throw new IllegalStateException("el anclaje esta vacio"); }
        if (bicicleta.enReparacion()) {
            throw new IllegalStateException("la bicicleta se encuentra en reparacion");
        }
        this.anclajes[anclaje - 1] = null;
        bicicleta.sumarUso();
        return bicicleta;
    }

    //BICICLETA CON MAS RETIROS!
    /** Firma: public Bicicleta masRetiros()
    Pre: No hay
    Post: Devuelve la bicicleta anclada con mas retiros, o null si no hay ninguna.
    */
    public Bicicleta masRetiros() {
        Bicicleta mejor = null;
        for (Bicicleta bicicleta : this.anclajes) {
            if (bicicleta != null && (mejor == null || bicicleta.getCantUso() > mejor.getCantUso())) {
                mejor = bicicleta;
            }
        }
        return mejor;
    }

    //BICICLETA CON MAS KMS!
    /** Firma: public Bicicleta masKms()
    Pre: No hay
    Post: Devuelve la bicicleta anclada con mas kms, o null si no hay ninguna.
    */
    public Bicicleta masKms() {
        Bicicleta mejor = null;
        for (Bicicleta bicicleta : this.anclajes) {
            if (bicicleta != null && (mejor == null || bicicleta.getKms() > mejor.getKms())) {
                mejor = bicicleta;
            }
        }
        return mejor;
    }

    //PORCENTAJE DE OCUPACION!
    /** Firma: public double porcentajeOcupacion()
    Pre: No hay
    Post: Devuelve el porcentaje de ocupacion (entre 0 y 100)
    */
    public double porcentajeOcupacion() {
        double ocupados = this.anclajes.length - cantLibres();
        return ocupados * 100.0 / this.anclajes.length;
    }

    //METODOS GENERALES!
    @Override
    public String toString() {
        String resultado = "Estacion (" + porcentajeOcupacion() + " %)";
        for (int i = 0; i < this.anclajes.length; i++) {
            resultado += "\n Anclaje " + (i + 1) + ": " + (this.anclajes[i] == null ? "libre" : this.anclajes[i]);
        }
        return resultado;
    }
}
