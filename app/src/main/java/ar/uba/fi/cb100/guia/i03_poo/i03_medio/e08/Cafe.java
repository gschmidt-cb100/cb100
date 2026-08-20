package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e08;

/** Otra subclase concreta: prepara un café reutilizando el mismo esqueleto. */
public class Cafe extends Bebida {

    @Override
    protected String agregarPrincipal() {
        return "Filtrar café molido";
    }

    @Override
    protected String servir() {
        return "Servir en pocillo con azúcar aparte";
    }
}
