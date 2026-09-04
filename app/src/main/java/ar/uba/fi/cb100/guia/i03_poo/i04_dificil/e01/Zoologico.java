package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e01;

/**
 * TDA Zoologico: administra los animales ingresados sobre un arreglo nativo
 * cuya capacidad se fija por parámetro del constructor (nunca hardcodeada).
 *
 * <p>Invariante: las posiciones [0, cantidad) del arreglo tienen animales
 * (sin nulls intercalados) y no hay dos animales con el mismo nombre.</p>
 */
public class Zoologico {

    private final Animal[] animales;
    private int cantidad;

    /**
     * pre: {@code capacidadMaxima} es mayor que 0.<br>
     * post: crea un zoológico vacío que admite hasta {@code capacidadMaxima}
     * animales.
     *
     * @param capacidadMaxima cantidad máxima de animales que entran.
     * @throws IllegalArgumentException si la capacidad no es positiva.
     */
    public Zoologico(int capacidadMaxima) {
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que 0, se recibió: " + capacidadMaxima);
        }
        this.animales = new Animal[capacidadMaxima];
        this.cantidad = 0;
    }

    /**
     * Ingresa un animal al zoológico. Se recibe el TDA Animal ENTERO,
     * no sus atributos sueltos.
     *
     * <p>pre: {@code animal} no es null, hay lugar y no existe otro animal
     * con el mismo nombre.<br>
     * post: el animal queda ingresado y {@code cantidadDeAnimales()} aumenta en 1.</p>
     *
     * @param animal el animal a ingresar.
     * @throws IllegalArgumentException si {@code animal} es null.
     * @throws IllegalStateException si el zoológico está lleno o ya hay un
     *         animal con ese nombre.
     */
    public void ingresar(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("El animal no puede ser null");
        }
        if (cantidad == animales.length) {
            throw new IllegalStateException(
                    "El zoológico está lleno (capacidad: " + animales.length + ")");
        }
        if (buscarPorNombre(animal.nombre()) != null) {
            throw new IllegalStateException(
                    "Ya hay un animal llamado '" + animal.nombre() + "'");
        }
        animales[cantidad] = animal;
        cantidad++;
    }

    /**
     * Busca un animal por su nombre. Decisión de diseño: no encontrarlo NO es
     * un error del que llama (preguntar es legítimo), así que devolvemos null
     * en vez de lanzar una excepción; el nombre es la única búsqueda por
     * "atributo suelto" admitida (es el identificador).
     *
     * <p>pre: {@code nombre} no es null ni vacío.<br>
     * post: devuelve el animal con ese nombre, o null si no hay ninguno;
     * el zoológico no se modifica.</p>
     *
     * @param nombre el nombre a buscar.
     * @return el animal encontrado, o null si no está.
     * @throws IllegalArgumentException si {@code nombre} es null o vacío.
     */
    public Animal buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre a buscar no puede ser null ni vacío");
        }
        for (int i = 0; i < cantidad; i++) {
            if (animales[i].nombre().equals(nombre)) {
                return animales[i];
            }
        }
        return null;
    }

    /**
     * pre: -.<br>
     * post: devuelve el animal de mayor peso, o null si el zoológico está
     * vacío ("sin valor" = null, nunca un centinela); no se modifica nada.
     *
     * @return el animal más pesado, o null si no hay animales.
     */
    public Animal elMasPesado() {
        Animal maximo = null;
        for (int i = 0; i < cantidad; i++) {
            if (maximo == null || animales[i].pesoEnKilos() > maximo.pesoEnKilos()) {
                maximo = animales[i];
            }
        }
        return maximo;
    }

    /**
     * pre: -.<br>
     * post: devuelve la suma de los pesos de todos los animales (0 si está
     * vacío). Es calculable recorriendo, por eso es MÉTODO y no atributo.
     *
     * @return el peso total en kilos.
     */
    public double pesoTotal() {
        double total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += animales[i].pesoEnKilos();
        }
        return total;
    }

    /**
     * pre: -.<br>
     * post: devuelve cuántos animales hay ingresados.
     *
     * @return la cantidad de animales.
     */
    public int cantidadDeAnimales() {
        return cantidad;
    }

    /**
     * pre: -.<br>
     * post: devuelve una descripción con TODOS los animales del zoológico.
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("Zoologico (")
                .append(cantidad).append('/').append(animales.length).append(" animales)");
        for (int i = 0; i < cantidad; i++) {
            texto.append("\n  - ").append(animales[i]);
        }
        return texto.toString();
    }

    /**
     * Demostración breve del TDA Zoologico.
     */
    public static void main(String[] args) {
        Zoologico zoologico = new Zoologico(5);
        zoologico.ingresar(new Animal("Clemente", "Elefante", 4200));
        zoologico.ingresar(new Animal("Rita", "Jirafa", 900));
        zoologico.ingresar(new Animal("Simba", "Leon", 190));

        System.out.println(zoologico);
        System.out.println("elMasPesado(): " + zoologico.elMasPesado());
        System.out.println("pesoTotal(): " + zoologico.pesoTotal() + " kg");
        System.out.println("buscarPorNombre(\"Rita\"): " + zoologico.buscarPorNombre("Rita"));
        System.out.println("buscarPorNombre(\"Dumbo\"): " + zoologico.buscarPorNombre("Dumbo"));
    }
}
