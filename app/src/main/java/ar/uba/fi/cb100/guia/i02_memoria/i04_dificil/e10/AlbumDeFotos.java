package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e10;

/**
 * TDA AlbumDeFotos: una colección de fotos sobre un arreglo nativo con
 * capacidad fija (por parámetro del constructor).
 *
 * <p>El punto del ejercicio: al quitar una foto, la posición liberada del
 * arreglo se pone en {@code null}. Si no lo hiciéramos, el arreglo seguiría
 * reteniendo la referencia a la foto "quitada" y el recolector de basura
 * nunca podría levantarla aunque nadie más la use: eso es una FUGA de
 * memoria (la misma que arregla {@code elementData[--size] = null} en el
 * código real de {@code ArrayList.remove}).</p>
 *
 * <p>Invariante: las posiciones [0, cantidad) tienen fotos; las posiciones
 * [cantidad, capacidad) están en null.</p>
 */
public class AlbumDeFotos {

    private final String[] fotos;
    private int cantidad;

    /**
     * pre: {@code capacidad} es mayor que 0.<br>
     * post: crea un álbum vacío que admite hasta {@code capacidad} fotos.
     *
     * @param capacidad cantidad máxima de fotos.
     * @throws IllegalArgumentException si la capacidad no es positiva.
     */
    public AlbumDeFotos(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que 0, se recibió: " + capacidad);
        }
        this.fotos = new String[capacidad];
        this.cantidad = 0;
    }

    /**
     * Agrega una foto al final del álbum.
     *
     * <p>pre: {@code foto} no es null y hay lugar.<br>
     * post: la foto queda al final y {@code cantidad()} aumenta en 1.</p>
     *
     * @param foto la foto a agregar.
     * @throws IllegalArgumentException si {@code foto} es null.
     * @throws IllegalStateException si el álbum está lleno.
     */
    public void agregar(String foto) {
        if (foto == null) {
            throw new IllegalArgumentException("La foto no puede ser null");
        }
        if (cantidad == fotos.length) {
            throw new IllegalStateException(
                    "El álbum está lleno (capacidad: " + fotos.length + ")");
        }
        fotos[cantidad] = foto;
        cantidad++;
    }

    /**
     * Quita y devuelve la última foto, dejando en {@code null} la posición
     * liberada para NO retener la referencia (evita la fuga: si nadie más la
     * referencia, el recolector de basura puede levantarla).
     *
     * <p>pre: hay al menos una foto.<br>
     * post: la última foto deja de pertenecer al álbum y se devuelve;
     * {@code cantidad()} disminuye en 1; la posición interna liberada queda
     * en null.</p>
     *
     * @return la foto quitada.
     * @throws IllegalStateException si el álbum está vacío.
     */
    public String quitarUltima() {
        if (cantidad == 0) {
            throw new IllegalStateException("El álbum está vacío: no hay foto para quitar");
        }
        cantidad--;
        String quitada = fotos[cantidad];
        fotos[cantidad] = null;
        return quitada;
    }

    /**
     * pre: {@code indice} está en [0, cantidad()).<br>
     * post: devuelve la foto en esa posición, sin modificar el álbum.
     *
     * @param indice posición de la foto.
     * @return la foto en esa posición.
     * @throws IllegalArgumentException si {@code indice} está fuera de [0, cantidad()).
     */
    public String fotoEn(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IllegalArgumentException(
                    "Índice fuera de rango: " + indice + " (cantidad=" + cantidad + ")");
        }
        return fotos[indice];
    }

    /**
     * pre: -.<br>
     * post: devuelve cuántas fotos tiene el álbum.
     *
     * @return la cantidad de fotos.
     */
    public int cantidad() {
        return cantidad;
    }

    /**
     * SÓLO PARA TESTS DE MEMORIA (por eso es paquete-privado): informa si el
     * arreglo interno todavía retiene una referencia en esa posición física,
     * incluso más allá de {@code cantidad()}. Permite verificar que
     * {@link #quitarUltima()} realmente dejó null la posición liberada,
     * sin exponer el arreglo interno.
     *
     * <p>pre: {@code posicionInterna} está en [0, capacidad).<br>
     * post: devuelve true si esa posición del arreglo interno no es null.</p>
     *
     * @param posicionInterna posición física en el arreglo interno.
     * @return true si esa posición retiene una referencia.
     * @throws IllegalArgumentException si la posición excede la capacidad.
     */
    boolean retieneReferenciaEn(int posicionInterna) {
        if (posicionInterna < 0 || posicionInterna >= fotos.length) {
            throw new IllegalArgumentException(
                    "Posición interna fuera de rango: " + posicionInterna
                            + " (capacidad=" + fotos.length + ")");
        }
        return fotos[posicionInterna] != null;
    }

    /**
     * pre: -.<br>
     * post: devuelve todas las fotos del álbum en orden.
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("AlbumDeFotos (")
                .append(cantidad).append('/').append(fotos.length).append(')');
        for (int i = 0; i < cantidad; i++) {
            texto.append("\n  ").append(i).append(": ").append(fotos[i]);
        }
        return texto.toString();
    }

    /**
     * Demostración breve: quitar deja la posición interna en null.
     */
    public static void main(String[] args) {
        AlbumDeFotos album = new AlbumDeFotos(3);
        album.agregar("playa.jpg");
        album.agregar("montania.jpg");
        System.out.println(album);

        String quitada = album.quitarUltima();
        System.out.println("quitarUltima(): " + quitada);
        System.out.println("cantidad(): " + album.cantidad());
        System.out.println("¿La posición 1 retiene la referencia? "
                + album.retieneReferenciaEn(1) + " (false = el GC puede levantarla)");
    }
}
