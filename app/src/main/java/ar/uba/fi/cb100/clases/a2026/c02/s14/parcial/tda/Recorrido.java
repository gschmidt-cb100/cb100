package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Recorrido: una secuencia ordenada de paradas.
 *
 * Responsabilidad: mantener las paradas en orden y responder las consultas
 * sobre el recorrido completo (duración, distancia, paradas en común).
 *
 * Estructura elegida: un vector {@code Parada[]} más la cantidad de
 * posiciones ocupadas. Justificación:
 *  - El enunciado sólo permite usar hasta vectores.
 *  - El recorrido es una secuencia con posición: el vector da acceso directo
 *    por posición en O(1) y agregar al final en O(1) amortizado.
 *  - Insertar y eliminar en el medio cuestan O(n) por el corrimiento. Se
 *    acepta: un recorrido tiene pocas paradas y se consulta mucho más de lo
 *    que se modifica.
 *  - Cuando el vector se llena se duplica su tamaño, así el recorrido no
 *    tiene un máximo de paradas.
 *
 * Invariante:
 *  - Las paradas ocupan las posiciones 0..cantidad-1 del vector, sin huecos.
 *  - Están ordenadas por distancia desde el inicio, de menor a mayor.
 *  - No hay dos paradas iguales.
 *
 * Hipótesis:
 *  H1. Las posiciones que ve el usuario van de 1 a la cantidad de paradas.
 *  H2. Como la parada trae su distancia desde el inicio, el orden de la
 *      secuencia tiene que respetarla: no se puede poner una parada antes
 *      de otra que está más cerca del inicio.
 *  H3. Un recorrido no pasa dos veces por la misma parada.
 *  H4. La duración total es la suma de los tiempos de las paradas. Al
 *      insertar o eliminar una parada no se recalculan los tiempos de las
 *      vecinas: los informa quien carga el recorrido.
 *  H5. La distancia total es la distancia desde el inicio de la última
 *      parada. Un recorrido sin paradas tiene distancia y duración cero.
 *  H6. Dos recorridos comparten una parada si tienen paradas iguales (mismo
 *      nombre y dirección), aunque estén a distinta distancia en cada uno.
 *  H7. La búsqueda por nombre es por nombre completo, sin distinguir mayúsculas.
 */
public class Recorrido {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final int CAPACIDAD_INICIAL = 10;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String nombre;
    private Parada[] paradas;
    private int cantidadDeParadas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code nombre} no es null ni está vacío.
     * post: crea el recorrido sin paradas. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Recorrido(String nombre) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre del recorrido no puede estar vacío.");
        this.nombre = nombre.trim();
        this.paradas = new Parada[CAPACIDAD_INICIAL];
        this.cantidadDeParadas = 0;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code parada} no es null, no está ya en el recorrido y su
     *      distancia desde el inicio no es menor que la de la última parada.
     * post: la parada queda al final del recorrido. Si no se cumple la pre,
     *       lanza {@link IllegalArgumentException}.
     */
    public void agregarParada(Parada parada) {
        insertarParada(cantidadDeParadas + 1, parada);
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de paradas más uno;
     *      {@code parada} no es null, no está ya en el recorrido y su
     *      distancia desde el inicio queda entre la de la parada anterior y
     *      la de la siguiente.
     * post: la parada queda en esa posición y las que estaban desde ahí en
     *       adelante se corren un lugar. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public void insertarParada(int posicion, Parada parada) {
        ValidacionesUtiles.validarRango(posicion, 1, cantidadDeParadas + 1, "posición");
        ValidacionesUtiles.validarDistintoDeNull(parada, "parada");
        ValidacionesUtiles.validarFalso(contiene(parada), "La parada ya está en el recorrido.");
        validarOrdenPorDistancia(posicion, parada);

        if (cantidadDeParadas == paradas.length) {
            duplicarCapacidad();
        }
        int indice = posicion - 1;
        for (int i = cantidadDeParadas; i > indice; i--) {
            paradas[i] = paradas[i - 1];
        }
        paradas[indice] = parada;
        cantidadDeParadas++;
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de paradas.
     * post: quita la parada de esa posición, corre un lugar las siguientes y
     *       devuelve la parada quitada. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Parada eliminarParada(int posicion) {
        ValidacionesUtiles.validarRango(posicion, 1, cantidadDeParadas, "posición");
        int indice = posicion - 1;
        Parada eliminada = paradas[indice];
        for (int i = indice; i < cantidadDeParadas - 1; i++) {
            paradas[i] = paradas[i + 1];
        }
        paradas[cantidadDeParadas - 1] = null;
        cantidadDeParadas--;
        return eliminada;
    }

    /**
     * post: devuelve la duración estimada del recorrido en minutos: la suma
     *       de los tiempos de todas las paradas. Cero si no hay paradas.
     */
    public int obtenerDuracionTotal() {
        int duracion = 0;
        for (int i = 0; i < cantidadDeParadas; i++) {
            duracion += paradas[i].getTiempoDesdeLaParadaAnterior();
        }
        return duracion;
    }

    /**
     * post: devuelve la distancia total del recorrido en km: la distancia
     *       desde el inicio de la parada más lejana. Cero si no hay paradas.
     */
    public double obtenerDistanciaTotal() {
        Parada masLejana = obtenerParadaMasLejana();
        if (masLejana == null) {
            return 0;
        }
        return masLejana.getDistanciaDesdeElInicio();
    }

    /**
     * post: devuelve la parada más alejada del inicio, o null si el
     *       recorrido no tiene paradas. Por el invariante es la última.
     */
    public Parada obtenerParadaMasLejana() {
        if (estaVacio()) {
            return null;
        }
        return paradas[cantidadDeParadas - 1];
    }

    /**
     * pre: {@code nombre} no es null.
     * post: devuelve un vector con las paradas que tienen ese nombre, en el
     *       orden del recorrido. Si no hay ninguna, el vector está vacío.
     */
    public Parada[] buscarParadas(String nombre) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        Parada[] encontradas = new Parada[cantidadDeParadas];
        int cantidadDeEncontradas = 0;
        for (int i = 0; i < cantidadDeParadas; i++) {
            if (paradas[i].tieneNombre(nombre)) {
                encontradas[cantidadDeEncontradas] = paradas[i];
                cantidadDeEncontradas++;
            }
        }
        return recortar(encontradas, cantidadDeEncontradas);
    }

    /**
     * pre: {@code otro} no es null.
     * post: devuelve true si alguna parada de este recorrido está también en
     *       el otro. Corta en la primera coincidencia.
     */
    public boolean tieneParadaEnComun(Recorrido otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "recorrido");
        for (int i = 0; i < cantidadDeParadas; i++) {
            if (otro.contiene(paradas[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * pre: {@code otro} no es null.
     * post: devuelve un vector con las paradas de este recorrido que están
     *       también en el otro, en el orden de este recorrido. Si no
     *       comparten ninguna, el vector está vacío.
     */
    public Parada[] obtenerParadasEnComun(Recorrido otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "recorrido");
        Parada[] comunes = new Parada[cantidadDeParadas];
        int cantidadDeComunes = 0;
        for (int i = 0; i < cantidadDeParadas; i++) {
            if (otro.contiene(paradas[i])) {
                comunes[cantidadDeComunes] = paradas[i];
                cantidadDeComunes++;
            }
        }
        return recortar(comunes, cantidadDeComunes);
    }

    /**
     * pre: {@code parada} no es null.
     * post: devuelve true si el recorrido tiene una parada igual a la recibida.
     */
    public boolean contiene(Parada parada) {
        ValidacionesUtiles.validarDistintoDeNull(parada, "parada");
        for (int i = 0; i < cantidadDeParadas; i++) {
            if (paradas[i].equals(parada)) {
                return true;
            }
        }
        return false;
    }

    /** post: devuelve true si el recorrido no tiene paradas. */
    public boolean estaVacio() {
        return cantidadDeParadas == 0;
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de paradas.
     * post: devuelve la parada de esa posición. Si no se cumple la pre,
     *       lanza {@link IllegalArgumentException}.
     */
    public Parada getParada(int posicion) {
        ValidacionesUtiles.validarRango(posicion, 1, cantidadDeParadas, "posición");
        return paradas[posicion - 1];
    }

    /**
     * Valida que la parada, puesta en esa posición, no rompa el orden por
     * distancia: no puede estar más cerca del inicio que la anterior ni más
     * lejos que la siguiente.
     */
    private void validarOrdenPorDistancia(int posicion, Parada parada) {
        int indice = posicion - 1;
        if (indice > 0) {
            ValidacionesUtiles.validarFalso(
                    parada.getDistanciaDesdeElInicio() < paradas[indice - 1].getDistanciaDesdeElInicio(),
                    "La parada está más cerca del inicio que la parada anterior.");
        }
        if (indice < cantidadDeParadas) {
            ValidacionesUtiles.validarFalso(
                    parada.getDistanciaDesdeElInicio() > paradas[indice].getDistanciaDesdeElInicio(),
                    "La parada está más lejos del inicio que la parada siguiente.");
        }
    }

    private void duplicarCapacidad() {
        Parada[] masGrande = new Parada[paradas.length * 2];
        for (int i = 0; i < cantidadDeParadas; i++) {
            masGrande[i] = paradas[i];
        }
        paradas = masGrande;
    }

    /** Devuelve un vector nuevo con las primeras {@code cantidad} paradas del original. */
    private static Parada[] recortar(Parada[] original, int cantidad) {
        Parada[] recortado = new Parada[cantidad];
        for (int i = 0; i < cantidad; i++) {
            recortado[i] = original[i];
        }
        return recortado;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Recorrido " + nombre + ": " + cantidadDeParadas + " paradas, "
                + obtenerDistanciaTotal() + " km, " + obtenerDuracionTotal() + " min";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public int getCantidadDeParadas() {
        return cantidadDeParadas;
    }
}
