Buenas.

EL de TDA lo veo bien. Cosas a mejorar

1) guardar es void. No devuelve el número de anclaje y tampoco hay un getBicicleta(anclaje), así que quien devuelve la bicicleta no puede saber qué número pasarle después a retirar. Es el punto más importante de usabilidad del TDA.

2) cambiarEstado(EstadoBicicleta) es un setter genérico. En clase quedaron operaciones del dominio (enviarAReparacion, finalizarReparacion) que validan la transición.

3) sumarUso() no valida que la bicicleta no esté en reparación. La regla queda garantizada solo por la estación (F1).

4) Nombres. El enunciado dice “Devolver” y el método se llama guardar; además hay abreviaturas (cantUso, kms, cantLibres, masRetiros) donde en la cursada usamos nombres completos.

Detalles menores.
a) El invariante de Bicicleta no incluye estado != null.
b) isEmpty() deja pasar un número de serie " ".
c) La bicicleta ya anclada es un problema del argumento (IllegalArgumentException en la de clase), no del estado.
d) masRetiros y masKms no documentan qué pasa en un empate.

EL ejercicio 3 lo veo mas facil si haces el TDA intermedio, para reutilizar codigo. Volve a hacerlo con el TDA Intermedio

1) Complejidad. El comparador del sort llama a amplitudTermica, que recorre todas las mediciones en cada comparación. Con n mediciones y c ciudades, el ordenamiento solo cuesta O(n · c log c), cerca de O(n² log n) en el peor caso, contra O(n · c) de la solución de clase.

2) Recorridos duplicados. Por cada ciudad se recorre la lista tres veces (contar, amplitud para filtrar, amplitud para ordenar), y cantidadMediciones y amplitudTermica repiten la misma condición. Es justo lo que resuelve la clase de datos EstadisticaDeCiudad (cantidad, mínima, máxima) en una sola pasada (P6).

3) NullPointerException latente. amplitudTermica hace mayor - menor con Double en null si la ciudad no tiene mediciones válidas. Hoy no explota solo porque se valida cantidadMinimaDeMediciones > 0 y por el orden del && en quien la llama (F1).

4) amplitudTermicaMayor sobra. Es un > envuelto en un método, y las validaciones de null se repiten en cada auxiliar privado cuando ya se validó en el público.

5) Orden de los filtros. Conviene chequear primero la provincia, que es barato, y después contar; sinRepetir además arrastra ciudades de otras provincias o solo con mediciones erróneas.

6) Nombres y mensajes. candidata es una lista, la variable sinRepetir se llama igual que el método, y “Los parametros son invalidos” no dice cuál.

Utiliza validaciones utiles asi resumis codigo.

Saludos
Gustavo.