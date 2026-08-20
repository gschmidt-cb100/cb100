package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e10;

/**
 * Comparativa de las dos estrategias de resolucion de colisiones vistas en
 * la unidad: ENCADENAMIENTO (cadenas de nodos por indice) y DIRECCIONAMIENTO
 * ABIERTO con sondeo lineal y lapidas.
 *
 * La clase mantiene una tabla de cada estrategia (versiones minimas,
 * replicadas aca para que el paquete sea autocontenido) y aplica las mismas
 * operaciones sobre ambas. La idea didactica: por adentro son estructuras
 * muy distintas, pero VISTAS COMO DICCIONARIO se comportan exactamente
 * igual. Esa es la gracia del TDA: el contrato no depende de la
 * implementacion.
 */
public class ComparativaEstrategias {

    // ------------------------------------------------------------------
    // Tabla minima con encadenamiento (String -> Integer).
    // ------------------------------------------------------------------
    private static class TablaEncadenada {
        private static class Nodo {
            final String clave;
            int valor;
            Nodo siguiente;

            Nodo(String clave, int valor, Nodo siguiente) {
                this.clave = clave;
                this.valor = valor;
                this.siguiente = siguiente;
            }
        }

        private final Nodo[] cadenas = new Nodo[16];
        private int tamanio;

        private int indiceDe(String clave) {
            return Math.floorMod(clave.hashCode(), cadenas.length);
        }

        void poner(String clave, int valor) {
            int i = indiceDe(clave);
            for (Nodo nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
                if (nodo.clave.equals(clave)) {
                    nodo.valor = valor;
                    return;
                }
            }
            cadenas[i] = new Nodo(clave, valor, cadenas[i]);
            tamanio++;
        }

        Integer obtener(String clave) {
            int i = indiceDe(clave);
            for (Nodo nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
                if (nodo.clave.equals(clave)) {
                    return nodo.valor;
                }
            }
            return null;
        }

        boolean contiene(String clave) {
            return obtener(clave) != null;
        }

        void quitar(String clave) {
            int i = indiceDe(clave);
            Nodo anterior = null;
            for (Nodo nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
                if (nodo.clave.equals(clave)) {
                    if (anterior == null) {
                        cadenas[i] = nodo.siguiente;
                    } else {
                        anterior.siguiente = nodo.siguiente;
                    }
                    tamanio--;
                    return;
                }
                anterior = nodo;
            }
        }

        int tamanio() {
            return tamanio;
        }
    }

    // ------------------------------------------------------------------
    // Tabla minima de direccionamiento abierto con sondeo lineal y lapidas.
    // ------------------------------------------------------------------
    private static class TablaSondeo {
        private static final Object LAPIDA = new Object();

        private Object[] claves = new Object[16];
        private int[] valores = new int[16];
        private int tamanio;
        private int lapidas;

        private int indiceInicial(String clave) {
            return Math.floorMod(clave.hashCode(), claves.length);
        }

        void poner(String clave, int valor) {
            int i = indiceInicial(clave);
            int primeraLapida = -1;
            while (claves[i] != null) {
                if (claves[i] == LAPIDA) {
                    if (primeraLapida == -1) {
                        primeraLapida = i;
                    }
                } else if (claves[i].equals(clave)) {
                    valores[i] = valor;
                    return;
                }
                i = (i + 1) % claves.length;
            }
            if (primeraLapida != -1) {
                i = primeraLapida;
                lapidas--;
            }
            claves[i] = clave;
            valores[i] = valor;
            tamanio++;
            if ((double) (tamanio + lapidas) / claves.length > 0.5) {
                rehash(claves.length * 2);
            }
        }

        Integer obtener(String clave) {
            int i = indiceInicial(clave);
            while (claves[i] != null) {
                if (claves[i] != LAPIDA && claves[i].equals(clave)) {
                    return valores[i];
                }
                i = (i + 1) % claves.length;
            }
            return null;
        }

        boolean contiene(String clave) {
            return obtener(clave) != null;
        }

        void quitar(String clave) {
            int i = indiceInicial(clave);
            while (claves[i] != null) {
                if (claves[i] != LAPIDA && claves[i].equals(clave)) {
                    claves[i] = LAPIDA;
                    tamanio--;
                    lapidas++;
                    return;
                }
                i = (i + 1) % claves.length;
            }
        }

        int tamanio() {
            return tamanio;
        }

        private void rehash(int nuevaCapacidad) {
            Object[] clavesViejas = claves;
            int[] valoresViejos = valores;
            claves = new Object[nuevaCapacidad];
            valores = new int[nuevaCapacidad];
            tamanio = 0;
            lapidas = 0;
            for (int j = 0; j < clavesViejas.length; j++) {
                if (clavesViejas[j] != null && clavesViejas[j] != LAPIDA) {
                    poner((String) clavesViejas[j], valoresViejos[j]);
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // La comparativa propiamente dicha.
    // ------------------------------------------------------------------

    private TablaEncadenada encadenada = new TablaEncadenada();
    private TablaSondeo sondeo = new TablaSondeo();

    /**
     * Reinicia ambas tablas, inserta cada clave asociada a su posicion en el
     * arreglo (clave -> indice) en las dos, y verifica que se comporten
     * igual como diccionario. Devuelve true si coinciden en todo.
     */
    public boolean coinciden(String[] claves) {
        encadenada = new TablaEncadenada();
        sondeo = new TablaSondeo();
        for (int i = 0; i < claves.length; i++) {
            encadenada.poner(claves[i], i);
            sondeo.poner(claves[i], i);
        }
        return verificar(claves);
    }

    /** Quita la clave en las DOS tablas (para seguir comparando despues). */
    public void quitar(String clave) {
        encadenada.quitar(clave);
        sondeo.quitar(clave);
    }

    /**
     * Verifica que ambas tablas respondan lo mismo para cada clave dada:
     * mismo obtener (incluido null), mismo contiene y mismo tamanio.
     */
    public boolean verificar(String[] claves) {
        if (encadenada.tamanio() != sondeo.tamanio()) {
            return false;
        }
        for (String clave : claves) {
            Integer a = encadenada.obtener(clave);
            Integer b = sondeo.obtener(clave);
            if (a == null ? b != null : !a.equals(b)) {
                return false;
            }
            if (encadenada.contiene(clave) != sondeo.contiene(clave)) {
                return false;
            }
        }
        return true;
    }

    /** Demostracion: mismas operaciones, mismas respuestas. */
    public static void main(String[] args) {
        ComparativaEstrategias comparativa = new ComparativaEstrategias();
        String[] claves = { "juan", "eva", "sol", "ana", "mia", "leo" };
        System.out.println("coinciden al insertar: " + comparativa.coinciden(claves));
        comparativa.quitar("eva");
        comparativa.quitar("leo");
        System.out.println("coinciden tras borrar: " + comparativa.verificar(claves));
    }
}
