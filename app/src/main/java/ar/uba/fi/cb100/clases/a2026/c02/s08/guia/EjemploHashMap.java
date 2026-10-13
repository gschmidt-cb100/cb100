package ar.uba.fi.cb100.clases.a2026.c02.s08.guia;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EjemploHashMap {

    public static void ejemploHashMap() {

        // 1. Crear un HashMap
        HashMap<String, Integer> notas = new HashMap<>();

        // 2. Agregar elementos: put()
        notas.put("Ana", 8);
        notas.put("Juan", 7);
        notas.put("Pedro", 9);

        System.out.println(notas);

        // 3. Obtener un valor: get()
        System.out.println(notas.get("Ana")); // 8

        // 4. Obtener un valor con alternativa: getOrDefault()
        System.out.println(notas.getOrDefault("Luis", 0)); // 0

        // 5. Verificar si existe una clave: containsKey()
        System.out.println(notas.containsKey("Juan")); // true

        // 6. Verificar si existe un valor: containsValue()
        System.out.println(notas.containsValue(9)); // true

        // 7. Reemplazar un valor: put()
        notas.put("Ana", 10);

        // 8. Agregar solamente si la clave no existe: putIfAbsent()
        notas.putIfAbsent("Ana", 6);   // No modifica el 10
        notas.putIfAbsent("Luis", 8);  // Agrega a Luis

        // 9. Reemplazar si existe la clave: replace()
        notas.replace("Juan", 8);

        // 10. Reemplazar solamente si coincide el valor anterior
        notas.replace("Pedro", 9, 10);

        // 11. Eliminar por clave: remove()
        notas.remove("Luis");

        // 12. Eliminar solo si coinciden clave y valor
        notas.remove("Pedro", 5);  // No lo elimina
        notas.remove("Pedro", 10); // Lo elimina

        // 13. Tamaño del mapa: size()
        System.out.println(notas.size());

        // 14. Comprobar si está vacío: isEmpty()
        System.out.println(notas.isEmpty()); // false

        // 15. Recorrer clave y valor: entrySet()
        for (Map.Entry<String, Integer> entrada : notas.entrySet()) {
            System.out.println(
                    entrada.getKey() + " = " + entrada.getValue()
            );
        }

        // 16. Obtener todas las claves: keySet()
        System.out.println(notas.keySet());

        // 17. Obtener todos los valores: values()
        System.out.println(notas.values());

        // 18. Recorrer con forEach()
        notas.forEach((alumno, nota) ->
                System.out.println(alumno + ": " + nota)
        );

        // 19. Actualizar un valor con compute()
        notas.compute("Ana", (clave, valor) -> valor + 1);

        // 20. Actualizar si existe: computeIfPresent()
        notas.computeIfPresent("Juan", (clave, valor) -> valor + 1);

        // 21. Calcular si no existe: computeIfAbsent()
        notas.computeIfAbsent("Maria", clave -> 10);

        // 22. Combinar un valor: merge()
        notas.merge("Ana", 2, Integer::sum);

        // 23. Reemplazar todos los valores: replaceAll()
        notas.replaceAll((alumno, nota) -> Math.min(nota, 10));

        // 24. Copiar las entradas a otro HashMap
        HashMap<String, Integer> copia = new HashMap<>(notas);

        // 25. Eliminar todos los elementos: clear()
        copia.clear();

        System.out.println("Original: " + notas);
        System.out.println("Copia vacía: " + copia);

        for(String clave: notas.keySet()) {
            System.out.println(clave + " -> " + notas.get(clave));
        }

        for(Integer valor: notas.values()) {
            System.out.println("Valor: " + valor);
        }
    }

    public static void ejemploHashSet() {
        // 1. Crear un HashSet
        HashSet<String> alumnos = new HashSet<>();

        // 2. Agregar elementos: add()
        alumnos.add("Ana");
        alumnos.add("Juan");
        alumnos.add("Pedro");

        // 3. Intentar agregar un duplicado
        boolean agregado = alumnos.add("Ana");
        System.out.println(agregado); // false

        System.out.println(alumnos);

        // 4. Verificar si existe un elemento: contains()
        System.out.println(alumnos.contains("Juan")); // true

        // 5. Eliminar un elemento: remove()
        alumnos.remove("Pedro");

        // 6. Consultar la cantidad: size()
        System.out.println(alumnos.size()); // 2

        // 7. Verificar si está vacío: isEmpty()
        System.out.println(alumnos.isEmpty()); // false

        // 8. Recorrer con for-each
        for (String alumno : alumnos) {
            System.out.println(alumno);
        }

        // 9. Recorrer con forEach()
        alumnos.forEach(System.out::println);

        // 10. Copiar un HashSet
        HashSet<String> copia = new HashSet<>(alumnos);

        // 11. Agregar varios elementos: addAll()
        alumnos.addAll(Arrays.asList("Maria", "Luis", "Ana"));

        // 12. Conservar solo elementos comunes: retainAll()
        copia.retainAll(Arrays.asList("Ana", "Luis"));

        // 13. Eliminar elementos de otra colección: removeAll()
        alumnos.removeAll(Arrays.asList("Juan", "Luis"));

        // 14. Eliminar los elementos que cumplan una condición
        alumnos.removeIf(nombre -> nombre.startsWith("M"));

        // 15. Convertir a array
        Object[] array = alumnos.toArray();

        // También se puede indicar el tipo
        String[] nombres = alumnos.toArray(new String[0]);

        // 16. Crear un HashSet desde otra colección
        HashSet<String> otro = new HashSet<>(
                Arrays.asList("Ana", "Pedro", "Sofia")
        );

        // 17. Comparar conjuntos: equals()
        System.out.println(alumnos.equals(otro));

        // 18. Obtener el hashCode()
        System.out.println(alumnos.hashCode());

        // 19. Eliminar todos los elementos: clear()
        copia.clear();

        System.out.println("Alumnos: " + alumnos);
        System.out.println("Copia: " + copia);
    }
}
