package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PullRequestTest {

    private PullRequest pr;

    @BeforeEach
    void preparar() {
        pr = new PullRequest("Ana");
        pr.registrarCheck("build", true);
        pr.registrarCheck("tests", true);
    }

    @Test
    @DisplayName("Sin aprobaciones no se puede mergear")
    void sinAprobaciones() {
        assertFalse(pr.puedeMergearse());
    }

    @Test
    @DisplayName("Con una sola aprobación tampoco alcanza")
    void unaAprobacion() {
        pr.aprobar("Beto");
        assertFalse(pr.puedeMergearse());
    }

    @Test
    @DisplayName("Dos aprobaciones pero un check rojo bloquean el merge")
    void checkRojo() {
        pr.aprobar("Beto");
        pr.aprobar("Carla");
        pr.registrarCheck("lint", false);
        assertFalse(pr.puedeMergearse());
    }

    @Test
    @DisplayName("Dos aprobaciones ajenas y todo el CI verde: merge")
    void todoOk() {
        pr.aprobar("Beto");
        pr.aprobar("Carla");
        assertTrue(pr.puedeMergearse());
    }

    @Test
    @DisplayName("La aprobación del autor se ignora y las repetidas cuentan una")
    void autorYRepetidas() {
        pr.aprobar("Ana");                          // autora: ignorada
        pr.aprobar("Beto");
        pr.aprobar("Beto");                         // repetida: el Set deduplica
        assertEquals(1, pr.aprobaciones().size());
        assertFalse(pr.puedeMergearse());

        pr.aprobar("Carla");
        assertTrue(pr.puedeMergearse());
        assertFalse(pr.aprobaciones().contains("Ana"));
    }
}
