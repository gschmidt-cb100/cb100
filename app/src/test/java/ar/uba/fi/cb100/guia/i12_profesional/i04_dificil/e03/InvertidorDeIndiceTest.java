package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvertidorDeIndiceTest {

    @Test
    @DisplayName("alumnos en varias materias quedan con todas sus materias")
    public void alumnosEnVariasMaterias() {
        Map<String, List<String>> materias = Map.of(
                "Algoritmos", List.of("Ana", "Beto", "Carla"),
                "Analisis", List.of("Ana", "Carla"),
                "Algebra", List.of("Beto"));

        Map<String, List<String>> porAlumno = InvertidorDeIndice.invertir(materias);

        assertEquals(3, porAlumno.size());
        assertEquals(List.of("Algoritmos", "Analisis"), porAlumno.get("Ana"));
        assertEquals(List.of("Algebra", "Algoritmos"), porAlumno.get("Beto"));
        assertEquals(List.of("Algoritmos", "Analisis"), porAlumno.get("Carla"));
    }

    @Test
    @DisplayName("el orden de las claves es el natural (determinista)")
    public void ordenDeterminista() {
        Map<String, List<String>> porAlumno = InvertidorDeIndice.invertir(Map.of(
                "M1", List.of("Zoe", "Ana"),
                "M2", List.of("Mia")));
        assertEquals(List.of("Ana", "Mia", "Zoe"), List.copyOf(porAlumno.keySet()));
    }

    @Test
    @DisplayName("invertir dos veces vuelve al indice original")
    public void invertirDosVecesEsIdentidad() {
        Map<String, List<String>> original = Map.of(
                "Algoritmos", List.of("Ana", "Beto"),
                "Analisis", List.of("Ana"));
        Map<String, List<String>> dosVeces =
                InvertidorDeIndice.invertir(InvertidorDeIndice.invertir(original));
        assertEquals(List.of("Ana", "Beto"), dosVeces.get("Algoritmos"));
        assertEquals(List.of("Ana"), dosVeces.get("Analisis"));
        assertEquals(2, dosVeces.size());
    }

    @Test
    @DisplayName("mapa vacio y materia sin alumnos no generan entradas")
    public void casosBorde() {
        assertTrue(InvertidorDeIndice.invertir(Map.of()).isEmpty());
        assertTrue(InvertidorDeIndice.invertir(Map.of("Vacia", List.of())).isEmpty());
    }
}
