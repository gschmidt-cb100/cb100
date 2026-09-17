# Anexo B — El generador de laberintos: especificación exacta

**TP 2 — CB100 — 2º cuatrimestre 2026**

El generador es la única pieza del TP que se intercambia con otro grupo.
Para que el generador ajeno funcione en su juego **sin cambiar una línea**,
el contrato está reducido a lo mínimo: **un método, texto de entrada, texto
de salida**. No hay ninguna clase que compartir.

---

## B.1 La interfaz

```java
package ar.uba.fi.cb100.tp2.generador;

/**
 * Genera un plano de la mazmorra a partir de los parametros que recibe.
 *
 * Recibe un JSON con el formato de B.2 y devuelve un JSON con el formato
 * de B.3. Con el mismo JSON de entrada devuelve siempre el mismo plano.
 *
 * Lanza IllegalArgumentException si la entrada es invalida: un campo que
 * falta, filas o columnas menores a 5, cofres o monstruos negativos, o mas
 * cofres y monstruos de los que entran en el plano.
 */
public interface GeneradorDeLaberinto {
    String generar(String parametrosJson);
}
```

Por qué `String` y no un `record` compartido: para que otro grupo use su
generador sólo tiene que copiar **un archivo `.java`** que implementa esta
interfaz. Si el método recibiera un objeto, habría que ponerse de acuerdo en
la clase de ese objeto, su paquete, sus campos. Con JSON, el acuerdo es este
documento. Y de paso, el generador se podría escribir en otro lenguaje.

---

## B.2 El JSON de entrada

```json
{
  "version": 1,
  "dificultad": "MEDIO",
  "filas": 15,
  "columnas": 20,
  "semilla": 42,
  "cofres": 2,
  "monstruos": 2,
  "conSalida": false
}
```

| Campo | Tipo | Significado |
|---|---|---|
| `version` | entero | Siempre `1`. Si algún día cambia el formato, cambia este número. |
| `dificultad` | `"FACIL"`, `"MEDIO"` o `"DIFICIL"` | El generador puede usarla para decidir cuánta agua y lava pone. Otro grupo puede interpretarla distinto: es lo único del contrato que queda a criterio. |
| `filas`, `columnas` | entero ≥ 5 | Tamaño del plano, **incluido** el borde de pared. |
| `semilla` | entero largo | La misma semilla con los mismos parámetros produce el mismo plano. |
| `cofres`, `monstruos` | entero ≥ 0 | Cuántos poner **en este plano**. El juego reparte los totales de la configuración entre los planos. |
| `conSalida` | booleano | Sólo un plano de todo el mundo tiene la salida. |

---

## B.3 El JSON de salida

```json
{
  "version": 1,
  "generador": "Grupo 03",
  "parametros": { "version": 1, "dificultad": "MEDIO", "filas": 9, "columnas": 12,
                  "semilla": 7, "cofres": 1, "monstruos": 1, "conSalida": true },
  "entrada": { "fila": 2, "columna": 2 },
  "salida":  { "fila": 8, "columna": 10 },
  "plano": [
    "############",
    "#E..#......#",
    "#.#.#.##.#.#",
    "#.#...#..~.#",
    "#.###.#.##.#",
    "#...#...C..#",
    "#.#.#.#.##.#",
    "#.M.....#S.#",
    "############"
  ]
}
```

| Campo | Significado |
|---|---|
| `generador` | Nombre del grupo. Para saber de quién es el plano cuando algo falla. |
| `parametros` | Copia exacta de la entrada. Así el archivo se explica solo y se puede regenerar. |
| `entrada` | Dónde aparece el héroe. Fila y columna **de 1 a n**. Coincide con la `E` del plano. |
| `salida` | Dónde está la salida, o `null` si `conSalida` era `false`. Coincide con la `S`. |
| `plano` | Una cadena por fila, todas del mismo largo. Es la única fuente de verdad: `entrada` y `salida` son redundantes a propósito, para poder verificar que coinciden. |

### Los símbolos

Son **fijos**: los define este anexo, no cada grupo.

| Símbolo | Significa | Quién decide qué es |
|---|---|---|
| `#` | pared | — |
| `.` | piso | — |
| `~` | agua | — |
| `^` | lava | — |
| `C` | un cofre, parado sobre piso | **el juego que lo carga** sortea el tipo de su propio catálogo |
| `M` | un monstruo, parado sobre piso | **el juego que lo carga** sortea el tipo de su propio catálogo |
| `E` | la entrada: donde aparece el héroe | — |
| `S` | la salida | — |

Esto es lo que hace posible el intercambio: el generador dice **dónde**, el
juego dice **qué**. Un generador ajeno no necesita conocer los cinco
monstruos ni los diez cofres del grupo que lo usa.

**No aparecen en el plano** las escaleras ni los pasillos: dependen de los
planos vecinos, y eso el generador no lo sabe. Los pone el juego después
(ver B.6).

---

## B.4 Las reglas de un plano válido

El juego **valida todo plano antes de usarlo**, propio o ajeno, con estas
once reglas. Si alguna falla, se rechaza con un mensaje que dice cuál.

| # | Regla | Cómo se verifica |
|---|---|---|
| R1 | **Dimensiones**: `plano` tiene exactamente `filas` cadenas de exactamente `columnas` caracteres, y ambas son ≥ 5. | Contar. |
| R2 | **Sólo los ocho símbolos** de B.3. | Recorrer. |
| R3 | **Borde de pared**: la primera y última fila, y la primera y última columna, son todas `#`. | Recorrer el borde. |
| R4 | **Una entrada, y la salida que corresponde**: exactamente una `E`; exactamente una `S` si `conSalida`, ninguna si no. `entrada` y `salida` coinciden con ellas. | Contar y comparar. |
| R5 | **Conexo**: desde `E` se llega a **toda** celda transitable. Transitable es todo lo que no es `#`: piso, agua, lava, cofre, monstruo, salida. | **BFS con cola** desde `E`: la cantidad de celdas alcanzadas es igual a la cantidad de transitables. |
| R6 | **Con bifurcaciones**: al menos el **10 %** de las celdas transitables tienen **3 o más** vecinos transitables (arriba, abajo, izquierda, derecha). | Contar vecinos de cada celda. |
| R7 | **Proporción**: entre el **30 % y el 60 %** de las celdas del plano son transitables. | Contar. |
| R8 | **Cantidades exactas**: hay tantas `C` como `cofres` y tantas `M` como `monstruos`. | Contar. |
| R9 | **La salida es la más lejana**: si hay `S`, su distancia a `E` en el BFS es la máxima del plano. Si hay empate, cualquiera de las celdas empatadas vale. | Con el mismo BFS de R5. |
| R10 | **Agua y lava con tope**: entre las dos, como mucho el **25 %** de las transitables. | Contar. |
| R11 | **Ningún monstruo pegado a la entrada**: toda `M` está a distancia Manhattan ≥ 3 de `E`. | Restar coordenadas. |

Las once reglas se comprueban con **recorridos, conteos y un BFS**. Nada
más. Un validador completo son unas 80 líneas.

**Determinismo**: no es una regla del validador porque no se puede verificar
con un solo plano, pero es parte del contrato: dos llamadas con el mismo JSON
de entrada devuelven el mismo `plano`. Se prueba con un test.

---

## B.5 El plano de ejemplo, verificado

El de B.3 pasa las once reglas. Los números:

| | |
|---|---|
| Celdas | 9 × 12 = 108 |
| Transitables | 49 (45 %) → R7 ✓ |
| Con 3 o más vecinos | 10 de 49 (20 %) → R6 ✓ |
| Distancia máxima desde `E` | 16, y `S` está a 16 → R9 ✓ |
| `M` en (8,3), `E` en (2,2) | distancia 7 → R11 ✓ |
| Agua | 1 celda (2 %) → R10 ✓ |

Un detalle que muestra por qué R9 vale la pena: en un primer borrador de este
mismo plano, la `S` estaba en (8,11), a distancia 15. La celda más lejana era
(8,10), una al lado. El validador lo rechazó. A ojo, nadie lo hubiera visto.

---

## B.6 Qué hace el juego con el plano

Una vez validado:

1. Por cada `C`, sortea un tipo de cofre **de su catálogo** y crea el objeto
   en esa celda. Por cada `M`, lo mismo con un monstruo.
2. Reemplaza `C`, `M`, `E` y `S` por piso en su grilla interna: son entidades
   y posiciones, no terreno. La salida queda registrada como posición.
3. Coloca las **conexiones**, que el generador no conoce:
   - **Escalera** entre el piso *k* y el *k+1* de un tablero: elige una
     celda que sea transitable en los dos planos, que no sea `E`, `S`, `C`
     ni `M` en ninguno, y la marca en ambos. Si no hay ninguna, abre una:
     convierte en piso una celda que sea piso en uno y pared en el otro.
   - **Pasillo** entre dos tableros: elige una celda del borde de cada uno
     cuya vecina interior sea transitable, y las une.
4. Verifica con el **grafo del mundo** (plano = vértice, conexión = arista)
   que la salida es alcanzable desde la entrada del primer plano.

Nada de esto toca al generador: por eso el generador ajeno "simplemente
anda".

---

## B.7 Los tests que entrega el grupo

Sobre **su** generador:

- **Determinismo**: dos llamadas con el mismo JSON dan el mismo `plano`.
- **Distintas semillas, distintos planos**: al menos en un caso.
- **Válido en las tres dificultades**: con los tamaños de la configuración,
  el plano pasa las once reglas. Repetido con 20 semillas distintas, para que
  no sea casualidad.
- **Entrada inválida**: un JSON sin `filas`, con `filas: 3`, con
  `cofres: -1`, o con más cofres que celdas, lanza
  `IllegalArgumentException`.

Sobre **su** validador, un plano hecho a mano por cada regla, que la viola y
sólo a ella, y que el validador rechaza nombrándola. Once tests.

Y la mitad del test del intercambio se puede escribir **antes** de recibir
el generador ajeno: "dado un JSON válido de B.3, el juego lo carga y arranca
una partida".

---

## B.8 Lista de control para el día del intercambio

Lo que el grupo entrega:

- [ ] Un único archivo `GeneradorGrupoNN.java` que implementa
      `GeneradorDeLaberinto` y **no depende de ninguna otra clase del grupo**.
      Puede usar Gson o Jackson y `java.util.Random`. Su pila propia, si es
      una clase aparte, va adentro como clase anidada.
- [ ] Tres JSON de salida de ejemplo, uno por dificultad, generados con
      semilla 1, para que el otro grupo los valide antes de compilar nada.
- [ ] El nombre del grupo en el campo `generador`.

Lo que el grupo hace con lo que recibe:

- [ ] Validar los tres JSON de ejemplo con su validador. Si alguno falla,
      anotar qué regla, antes de tocar código.
- [ ] Copiar el `.java` al paquete `generador` de su proyecto y reemplazar
      **una línea**: `new GeneradorGrupoNN()` donde antes había el propio.
- [ ] Jugar una partida en cada dificultad.
- [ ] En el informe: qué pasó a la primera, qué regla falló si falló, y de
      quién era la falla: del generador, del validador, o del contrato.
