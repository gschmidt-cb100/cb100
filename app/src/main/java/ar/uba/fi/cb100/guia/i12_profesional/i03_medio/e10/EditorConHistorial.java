package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e10;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * e10: historial con undo/redo — el patrón de todo editor.
 *
 * <p><b>Elección de estructuras:</b> DOS pilas (U5), implementadas con
 * {@code ArrayDeque} — la implementación de pila recomendada por la JDK,
 * con push/pop O(1) amortizado. La pila de <b>deshacer</b> guarda los
 * estados pasados; la de <b>rehacer</b>, los estados que deshicimos.
 * Deshacer mueve el presente a rehacer y saca el tope de deshacer;
 * rehacer hace el viaje inverso. LIFO exacto: lo último que hiciste
 * es lo primero que se deshace.</p>
 *
 * <p><b>La regla clave:</b> escribir algo nuevo BORRA la pila de rehacer.
 * Si deshiciste dos pasos y escribís otra cosa, esa "historia alternativa"
 * que deshiciste ya no se puede rehacer — igual que en cualquier editor.</p>
 */
public final class EditorConHistorial {

    private String textoActual = "";
    private final Deque<String> pilaDeDeshacer = new ArrayDeque<>();
    private final Deque<String> pilaDeRehacer = new ArrayDeque<>();

    /** Reemplaza el texto, guardando el anterior para poder deshacer. */
    public void escribir(String textoNuevo) {
        pilaDeDeshacer.push(textoActual);
        textoActual = textoNuevo;
        pilaDeRehacer.clear();                      // escribir borra el futuro
    }

    /** Vuelve al estado anterior. Sin historia, no hace nada. */
    public void deshacer() {
        if (!pilaDeDeshacer.isEmpty()) {
            pilaDeRehacer.push(textoActual);
            textoActual = pilaDeDeshacer.pop();
        }
    }

    /** Rehace lo último deshecho. Sin nada para rehacer, no hace nada. */
    public void rehacer() {
        if (!pilaDeRehacer.isEmpty()) {
            pilaDeDeshacer.push(textoActual);
            textoActual = pilaDeRehacer.pop();
        }
    }

    public String textoActual() {
        return textoActual;
    }

    public boolean sePuedeRehacer() {
        return !pilaDeRehacer.isEmpty();
    }

    public static void main(String[] args) {
        EditorConHistorial editor = new EditorConHistorial();
        editor.escribir("a");
        editor.escribir("b");
        editor.escribir("c");
        editor.deshacer();
        editor.deshacer();
        System.out.println(editor.textoActual());   // a
        editor.rehacer();
        System.out.println(editor.textoActual());   // b
        editor.escribir("d");
        System.out.println(editor.textoActual());   // d
        System.out.println(editor.sePuedeRehacer()); // false: "c" se perdió
    }
}
