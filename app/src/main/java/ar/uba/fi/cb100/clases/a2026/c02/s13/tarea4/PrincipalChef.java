package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4.datos.Ingrediente;
import ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4.datos.Receta;

public class PrincipalChef {

    public static void main(String[] args) {
        Receta bizcochuelo = crearReceta("Bizcochuelo", "harina", 500, "azucar", 300, "huevo", 4);

        Receta budin = crearReceta("Budín", "harina", 400, "azucar", 300, "huevo", 2);              // azucar igual: no
        Receta galletitas = crearReceta("Galletitas", "harina", 300, "azucar", 100);                // falta huevo: no
        Receta torta = crearReceta("Torta", "harina", 600, "azucar", 200, "huevo", 3);              // más harina: no
        Receta muffins = crearReceta("Muffins", "harina", 250, "azucar", 150, "huevo", 2, "leche", 100);   // cumple
        Receta panqueques = crearReceta("Panqueques", "harina", 200, "azucar", 50, "huevo", 3);     // cumple, pero después

        Chef chef = new Chef();

        List<Receta> alternativas = List.of(budin, galletitas, torta, muffins, panqueques);
        System.out.println(chef.buscarRecetaSustituta(bizcochuelo, alternativas));                  // Muffins

        System.out.println(chef.buscarRecetaSustituta(bizcochuelo, List.of(budin, galletitas, torta)));   // null
        System.out.println(chef.buscarRecetaSustituta(bizcochuelo, List.of(bizcochuelo)));          // null (misma cantidad)
        System.out.println(chef.buscarRecetaSustituta(bizcochuelo, new ArrayList<>()));             // null

        try {
            chef.buscarRecetaSustituta(new Receta("Vacía"), alternativas);
        } catch (IllegalArgumentException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }
        try {
            chef.buscarRecetaSustituta(bizcochuelo, null);
        } catch (IllegalArgumentException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }
    }

    /**
     * Arma una receta a partir de pares (nombre de ingrediente, cantidad).
     * @param nombre nombre de la receta
     * @param ingredientes pares nombre (String), cantidad (número)
     * @return la receta con esos ingredientes
     */
    private static Receta crearReceta(String nombre, Object... ingredientes) {
        Receta receta = new Receta(nombre);
        for (int i = 0; i < ingredientes.length; i += 2) {
            receta.agregarIngrediente(new Ingrediente((String) ingredientes[i], ((Number) ingredientes[i + 1]).doubleValue()));
        }
        return receta;
    }
}
