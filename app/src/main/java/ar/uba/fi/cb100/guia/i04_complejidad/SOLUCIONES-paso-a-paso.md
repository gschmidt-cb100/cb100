# Guía 4 — Soluciones paso a paso: el cálculo de complejidad

> **Cómo leer este archivo.** Cada ejercicio de la guía está resuelto con el
> **proceso completo** de cálculo de su complejidad, no sólo el resultado.
> Los algoritmos **recursivos** (y los iterativos que "achican" el problema)
> están resueltos **por los dos métodos**: expansión de la ecuación de
> recurrencia **y** Teorema Maestro, mostrando paso a paso **cómo se elige
> el teorema y el caso** que corresponde. Cuando los dos métodos aplican,
> tienen que dar lo mismo: usalo como verificación.
> El código de cada ejercicio está en su paquete (`i02_facil/eNN`, etc.).

---

## El procedimiento general (antes de resolver nada)

Ante cualquier algoritmo, seguí estas preguntas **en orden**:

**Paso 1 — ¿Es iterativo o recursivo?**
- Si es **iterativo** (bucles): se aplican las reglas de la Unidad 4
  (secuencia se suma, bucles se multiplican, bucle que divide es log).
- Si es **recursivo** (se llama a sí mismo): se plantea la **ecuación de
  recurrencia** leyéndola del código y se resuelve por expansión o teorema.
- Ojo: hay bucles que **achican el problema** (dividen la variable de
  control) y se pueden analizar de las dos maneras. Acá los resolvemos
  por ambas para practicar.

**Paso 2 — Plantear la ecuación (leyéndola del código).**
- ¿**Cuántas** llamadas recursivas hay? → ese número es el coeficiente `a`.
- ¿Sobre qué **tamaño** se hace cada llamada? → el argumento de T:
  `T(n-b)` si **resta**, `T(n/b)` si **divide**.
- ¿Qué **trabajo propio** hace la función fuera de las llamadas
  (comparaciones, bucles, fusiones)? → es el término `f(n)` que se suma.

**Paso 3 — Elegir el teorema. Es UNA sola pregunta:**

```
            ¿Cómo se achica el problema en cada llamada?
                    |
        +-----------+------------+
        |                        |
   RESTA una constante      DIVIDE por una constante
   T(n) = a·T(n-b) + O(n^k) T(n) = a·T(n/b) + f(n)
        |                        |
   TEOREMA POR SUSTRACCIÓN   TEOREMA POR DIVISIÓN
```

**Paso 4a — Si es por SUSTRACCIÓN, mirar solamente `a`** (la cantidad
de llamadas recursivas):

| Condición | Resultado | Lectura |
|-----------|-----------|---------|
| a < 1 | O(n^k) | (raro) la recursión se apaga |
| **a = 1** | **O(n^(k+1))** | el trabajo O(n^k) se repite ~n/b veces: **sube un grado** |
| **a > 1** | **O(n^k · a^(n/b))** | el árbol se multiplica por nivel y hay n niveles: **exponencial** |

**Paso 4b — Si es por DIVISIÓN, comparar fuerzas**: calcular el número
**n^(log_b a)** (el "peso de la recursión") y compararlo con **f(n)**
(el trabajo propio):

| Comparación | Caso | Resultado |
|-------------|------|-----------|
| f(n) crece **menos** que n^(log_b a) | Caso 1 | Θ(n^(log_b a)) — mandan las hojas |
| f(n) crece **igual** que n^(log_b a) | Caso 2 | Θ(n^(log_b a) · log n) — todos los niveles empatan |
| f(n) crece **más** que n^(log_b a) | Caso 3 | Θ(f(n)) — manda la raíz |

**Paso 5 — Verificar por expansión**: desarrollar la recurrencia
reemplazo por reemplazo hasta ver el patrón, cerrar en el caso base,
y comprobar que da lo mismo que el teorema.

---

# NIVEL FÁCIL

## Fácil e01 — `SumaDeArreglo` · **Θ(n)**

Iterativo puro. Un solo `for` de `n` vueltas con cuerpo O(1):

```
long acumulador = 0;                  → O(1)
for (i = 0; i < a.length; i++)        → n vueltas
    acumulador += a[i];               → O(1) por vuelta
return acumulador;                    → O(1)
```

Regla del bucle: (n vueltas) × (O(1) por vuelta) = **O(n)**. El conteo fino
da ≈ 3n + 4 operaciones; las constantes se descartan: **Θ(n)**.

## Fácil e02 — `ParesIguales` · **Θ(n²)**

Dos bucles anidados, pero el interno **no** hace n vueltas fijas: para cada
`i` hace `n-1-i`. El total es la **suma aritmética**:

```
(n-1) + (n-2) + ... + 1 + 0  =  n(n-1)/2  =  Θ(n²)
```

Moraleja: aunque el bucle interno se "acorte", la suma aritmética sigue
siendo cuadrática (queda n²/2 y la constante 1/2 se descarta).

## Fácil e03 — `MaximoDeArreglo` · **Θ(n)**

Un bucle de n−1 vueltas, cuerpo O(1) (una comparación y a veces una
asignación). **Θ(n)**. No hay mejor caso más barato: siempre hay que mirar
todos los elementos para estar seguro del máximo.

## Fácil e04 — `DeteccionDuplicados` · **O(n²), Ω(1)**

Misma estructura que e02 (suma aritmética → cuadrático), pero con **corte
temprano** (`return true`). Por eso las dos cotas son distintas:

- **Peor caso** (sin duplicados: recorre todo): la suma completa → **O(n²)**.
- **Mejor caso** (los dos primeros son iguales): una comparación → **Ω(1)**.

Como mejor y peor no coinciden, acá **no hay Θ** del algoritmo completo.

## Fácil e05 — `PotenciaLenta` · **Θ(exp)**

Un bucle de exactamente `exp` vueltas, cuerpo O(1) → **Θ(exp)**. El tamaño
de la entrada acá es el **valor** del exponente. Comparalo con el e02 del
nivel medio: mismo problema, algoritmo Θ(log exp).

## Fácil e06 — `CantidadDigitos` · **Θ(log n)** (¡por los dos métodos!)

El `while` **divide por 10** la variable en cada vuelta: regla del "bucle
que divide" → tantas vueltas como veces entra el 10 en n: **Θ(log₁₀ n)**.

**Como recurrencia** (el bucle achica el problema, así que también se puede
leer así): `T(n) = T(n/10) + O(1)`.

- **¿Qué teorema?** La llamada **divide** (n/10) → teorema por **división**.
- **Identificar**: a = 1 (una "llamada"), b = 10, f(n) = O(1) = n⁰.
- **Peso de la recursión**: n^(log₁₀ 1) = n⁰ = 1.
- **Comparar**: f(n) = 1 **empata** con 1 → **Caso 2**.
- **Resultado**: Θ(n⁰ · log n) = **Θ(log n)**. ✔ coincide.

¿Y la base 10 del logaritmo? No importa: log₁₀ n = log₂ n / log₂ 10, o sea
difieren en una **constante** (≈3.32), y las constantes se descartan.

## Fácil e07 — `BusquedaLineal` · **O(n), Ω(1)**

Bucle con corte temprano: peor caso recorre todo (no está, o está último)
→ **O(n)**; mejor caso está primero → **Ω(1)**.

## Fácil e08 — `InvertirArreglo` · **Θ(n)**

Un bucle de n vueltas, cuerpo O(1). **Θ(n)**. (También usa Θ(n) de memoria
por el arreglo nuevo — la complejidad **espacial** también se analiza.)

## Fácil e09 — `PotenciaDeDos` · **O(log n)**

El `while` divide n por 2 en cada vuelta → a lo sumo log₂ n vueltas →
**O(log n)**. Como recurrencia: `T(n) = T(n/2) + O(1)`; división, a = 1,
b = 2, n^(log₂ 1) = n⁰ = 1 = f(n) → **Caso 2** → Θ(1 · log n) = **Θ(log n)**
para el peor caso (número impar corta antes: por eso O y no Θ del algoritmo).

## Fácil e10 — `ConteoIteraciones` · **Θ(log n)**

El `for` hace `i *= 2`: la variable de control se **duplica**, así que hace
⌈log₂ n⌉ vueltas → **Θ(log n)**. Es la regla 5 de la unidad vista al revés:
duplicar la variable hasta llegar a n = dividir n hasta llegar a 1.

---

# NIVEL MEDIO

## Medio e01 — `BusquedaBinaria` · **Θ(log n)** peor caso

Aunque está escrita **iterativa**, cada vuelta del `while` descarta la
**mitad** del rango: el problema se achica dividiendo. La analizamos por
los dos métodos.

**La ecuación, leída del algoritmo**: en cada paso hay **una** comparación
con el del medio (O(1)) y sigue **un solo** subproblema de tamaño n/2
(la mitad donde puede estar x):

```
T(n) = T(n/2) + O(1),        T(1) = O(1)
```

**Método 1 — Teorema Maestro, paso a paso:**

1. ¿Resta o divide? El argumento es n/2: **divide** → teorema por división.
2. Identificar: **a = 1** (un subproblema), **b = 2** (mitad), **f(n) = O(1) = n⁰**.
3. Peso de la recursión: n^(log_b a) = n^(log₂ 1) = **n⁰ = 1**.
4. Comparar: f(n) = 1 y n^(log₂ 1) = 1 → **crecen igual** → **Caso 2**.
5. Resultado: Θ(n^(log₂ 1) · log n) = Θ(1 · log n) = **Θ(log n)**.

**Método 2 — Expansión, reemplazo por reemplazo:**

```
T(n) = T(n/2) + 1
     = [T(n/4) + 1] + 1        = T(n/4) + 2
     = [T(n/8) + 1] + 2        = T(n/8) + 3
     ...
     = T(n/2^k) + k
```

Se llega al caso base cuando n/2^k = 1, o sea k = log₂ n:

```
T(n) = T(1) + log₂ n = Θ(log n)    ✔ coincide con el teorema
```

Mejor caso: x está justo en el medio en el primer intento → Ω(1).

## Medio e02 — `PotenciaRapida` · **Θ(log exp)**

El `while` hace `e >>= 1` (dividir por 2) en cada vuelta, con cuerpo O(1).

**Recurrencia sobre el exponente**: `T(e) = T(e/2) + O(1)` — exactamente la
misma que la búsqueda binaria.

- **Teorema (división)**: a = 1, b = 2, f = n⁰ → n^(log₂ 1) = 1 = f →
  **Caso 2** → **Θ(log e)**.
- **Expansión**: T(e) = T(e/2^k) + k; caso base en k = log₂ e → Θ(log e). ✔

Contra `PotenciaLenta` (fácil e05): para exp = 1.000.000, esto son ~20
vueltas contra un millón.

## Medio e03 — `BubbleSort` · **O(n²), Ω(n)**

Dos bucles anidados: el interno hace (n−1−i) vueltas → suma aritmética
(n−1) + (n−2) + ... + 1 = n(n−1)/2 → **O(n²)** en el peor caso.

Con la bandera `huboIntercambio`, si el arreglo **ya está ordenado** la
primera pasada no intercambia nada y corta: **Ω(n)** (una pasada completa).

## Medio e04 — `InsertionSort` · **O(n²), Ω(n)**

El `while` interno corre la clave hacia la izquierda tantos lugares como
haga falta:

- **Peor caso** (arreglo al revés): la clave i-ésima retrocede i lugares →
  1 + 2 + ... + (n−1) = n(n−1)/2 → **O(n²)**.
- **Mejor caso** (ya ordenado): el `while` no ejecuta nunca; sólo n−1
  comparaciones → **Ω(n)**.

## Medio e05 — `MergeSort` · **Θ(n log n)** siempre

**La ecuación, leída del código de `ordenarRango`:**

```java
ordenarRango(a, aux, inicio, medio);      // 1.ª llamada sobre n/2 → T(n/2)
ordenarRango(a, aux, medio + 1, fin);     // 2.ª llamada sobre n/2 → T(n/2)
mezclar(a, aux, inicio, medio, fin);      // recorre el rango entero → O(n)
```

**Dos** llamadas (a = 2), cada una sobre la **mitad** (b = 2), más una
fusión lineal (f(n) = n):

```
T(n) = 2·T(n/2) + n
```

**Método 1 — Teorema Maestro, paso a paso:**

1. ¿Resta o divide? n/2: **divide** → división.
2. **a = 2, b = 2, f(n) = n**.
3. Peso de la recursión: n^(log₂ 2) = **n¹ = n**.
4. Comparar: f(n) = n contra n → **empatan** → **Caso 2**.
5. Resultado: Θ(n^(log₂ 2) · log n) = **Θ(n log n)**.

**Método 2 — Expansión** (el molde: T(n/2) = 2T(n/4) + n/2):

```
T(n) = 2·T(n/2) + n
     = 2·[2T(n/4) + n/2] + n   = 4·T(n/4) + n + n      = 4T(n/4) + 2n
     = 4·[2T(n/8) + n/4] + 2n  = 8·T(n/8) + n + 2n     = 8T(n/8) + 3n
     ...
     = 2^k · T(n/2^k) + k·n
```

Caso base: n/2^k = 1 → k = log₂ n → T(n) = n·T(1) + n·log₂ n =
**Θ(n log n)**. ✔ Es Θ (y no sólo O) porque la fusión recorre el rango
entero siempre, esté como esté el arreglo.

## Medio e06 — `SumaRecursiva` · **Θ(n)**

**La ecuación, leída de `sumarDesde`:** **una** llamada sobre un problema
con **un elemento menos** (i+1 deja n−1 pendientes), más O(1):

```
T(n) = T(n-1) + O(1),        T(0) = O(1)
```

**Método 1 — Teorema Maestro, paso a paso:**

1. ¿Resta o divide? El problema pasa de n a n−1: **resta** → **sustracción**.
   (Ojo: éste es el error clásico — usar el teorema de división acá.)
2. Identificar: **a = 1** (una llamada), **b = 1** (resta 1), **k = 0**
   (el trabajo propio es O(1) = O(n⁰)).
3. Mirar `a`: **a = 1** → segundo renglón de la tabla → O(n^(k+1)).
4. Resultado: O(n^(0+1)) = **O(n)**.

**Método 2 — Expansión:**

```
T(n) = T(n-1) + 1
     = [T(n-2) + 1] + 1       = T(n-2) + 2
     = T(n-3) + 3
     ...
     = T(n-k) + k
```

Caso base: n−k = 0 → k = n → T(n) = T(0) + n = **Θ(n)**. ✔

## Medio e07 — `Factorial` · **Θ(n)**

Misma ecuación que e06 — es EL ejemplo de la teoría: `T(n) = T(n-1) + O(1)`.

- **Sustracción**: a = 1, b = 1, k = 0 → a = 1 → O(n^(0+1)) = **O(n)**.
- **Expansión**: T(n) = T(n−k) + k → k = n−1 → **Θ(n)**. ✔

Lectura: n llamadas apiladas, cada una con trabajo constante. (De paso:
también usa Θ(n) de **stack** — si n fuese enorme, `StackOverflowError`.)

## Medio e08 — `MaximoDyV` · **Θ(n)** (¡Caso 1: no todo divide-y-vencerás es n log n!)

**La ecuación, leída de `maximoRango`:** **dos** llamadas sobre mitades,
más un `Math.max` (O(1)):

```
T(n) = 2·T(n/2) + O(1)
```

**Método 1 — Teorema Maestro, paso a paso:**

1. Divide (n/2) → **división**.
2. **a = 2, b = 2, f(n) = O(1) = n⁰**.
3. Peso de la recursión: n^(log₂ 2) = **n¹**.
4. Comparar: f(n) = n⁰ crece **menos** que n¹ (con ε = 1) → **Caso 1**
   (la recursión domina: el costo está en las hojas del árbol).
5. Resultado: Θ(n^(log₂ 2)) = **Θ(n)**.

**Método 2 — Expansión** (fijate que acá NO aparece "un n más" por nivel,
sino potencias de 2):

```
T(n) = 2·T(n/2) + 1
     = 2·[2T(n/4) + 1] + 1     = 4·T(n/4) + 2 + 1
     = 4·[2T(n/8) + 1] + 2 + 1 = 8·T(n/8) + 4 + 2 + 1
     ...
     = 2^k · T(n/2^k) + (2^k - 1)
```

Caso base: k = log₂ n, 2^k = n → T(n) = n·T(1) + (n − 1) = **Θ(n)**. ✔

La suma 1 + 2 + 4 + ... es **geométrica**: la domina el último término (n),
no la cantidad de niveles. Por eso da Θ(n) y no Θ(n log n): comparalo con
Mergesort, donde el trabajo por nivel era n **en todos** los niveles.

## Medio e09 — `ContarApariciones` · **Θ(log n)**

Dos búsquedas binarias modificadas (primera y última posición), una detrás
de otra. Regla de la **secuencia**: se suma → Θ(log n) + Θ(log n) =
2·Θ(log n) = **Θ(log n)**. Cada una es Caso 2 del teorema por división,
igual que e01. (La versión ingenua —búsqueda lineal contando— es Θ(n).)

## Medio e10 — `SelectionSort` · **Θ(n²)** siempre

Suma aritmética del bucle interno: (n−1) + (n−2) + ... + 1 = n(n−1)/2 →
**Θ(n²)**. A diferencia de Bubble y Insertion, acá **no hay corte posible**:
para saber el mínimo del resto hay que mirarlo entero, esté como esté.
Por eso es Θ (mejor = peor) y no sólo O.

---

# NIVEL DIFÍCIL

## Difícil e01 — `QuickSort` · **Θ(n log n) promedio, O(n²) peor** (¡un teorema para cada caso!)

Quicksort es el ejemplo perfecto de que **la ecuación depende de cómo caiga
el pivote**, y cada situación pide un teorema distinto.

**La ecuación, leída del código:** `particionarLomuto` recorre el rango
entero → **f(n) = Θ(n)**. Después hay **dos** llamadas, pero ¿de qué tamaño?
Depende de dónde quedó el pivote.

**Caso promedio (pivote parte al medio):** dos llamadas de tamaño ≈ n/2:

```
T(n) = 2·T(n/2) + n
```

1. Divide → teorema por **división**. a = 2, b = 2, f(n) = n.
2. n^(log₂ 2) = n¹; f(n) = n **empata** → **Caso 2**.
3. Resultado: **Θ(n log n)**. (Misma cuenta que Mergesort.)

**Peor caso (pivote siempre es el mayor/menor — p. ej. arreglo ya ordenado
con Lomuto):** la partición deja **un** subproblema de tamaño n−1 (el otro
queda vacío):

```
T(n) = T(n-1) + n
```

1. ¡Ahora **resta**! → teorema por **sustracción**. a = 1, b = 1, **k = 1**
   (el trabajo propio es Θ(n) = Θ(n¹)).
2. a = 1 → O(n^(k+1)) = O(n^(1+1)) = **O(n²)**.

**Verificación por expansión del peor caso:**

```
T(n) = T(n-1) + n
     = [T(n-2) + (n-1)] + n     = T(n-2) + (n-1) + n
     = T(n-3) + (n-2) + (n-1) + n
     ...
     = T(1) + 2 + 3 + ... + n   = Θ(n²)      ✔ (suma aritmética)
```

Moraleja: **el mismo código** tiene dos ecuaciones distintas según los
datos; el análisis de complejidad siempre pregunta "¿en qué caso estoy?".

## Difícil e02 — `QuickSelect` · **Θ(n) promedio** (¡por fin un Caso 3!), O(n²) peor

Como Quicksort, pero después de particionar sigue por **una sola** mitad.

**Caso promedio:** una llamada de tamaño n/2, partición Θ(n):

```
T(n) = T(n/2) + n
```

**Teorema Maestro por división, paso a paso:**

1. **a = 1, b = 2, f(n) = n**.
2. Peso de la recursión: n^(log₂ 1) = **n⁰ = 1**.
3. Comparar: f(n) = n crece **más** que 1 (con ε = 1) → **Caso 3**
   (el trabajo local domina: manda la raíz del árbol).
4. Chequeo de regularidad del Caso 3: a·f(n/b) = 1·(n/2) = n/2 ≤ c·n
   con c = 1/2 < 1. ✔
5. Resultado: Θ(f(n)) = **Θ(n)**.

**Verificación por expansión:**

```
T(n) = T(n/2) + n
     = [T(n/4) + n/2] + n       = T(n/4) + n + n/2
     = T(n/8) + n + n/2 + n/4
     ...
     = T(1) + n·(1 + 1/2 + 1/4 + ... ) < T(1) + 2n = Θ(n)   ✔
```

La suma geométrica decreciente se acota por 2n: **el primer término manda**
(eso ES el Caso 3). Peor caso: igual que Quicksort, T(n) = T(n−1) + n →
sustracción con a = 1, k = 1 → **O(n²)**.

## Difícil e03 — `MergeSortIterativo` · **Θ(n log n)**

Sin recursión, pero la estructura del árbol está en los bucles:

- Bucle externo: `ancho` se **duplica** (1, 2, 4, ...) hasta n →
  **log₂ n** vueltas (regla del bucle que multiplica).
- Por cada `ancho`, el bucle interno recorre todos los bloques y cada
  elemento se fusiona **una vez** → **Θ(n) por vuelta del externo**.

Bucles anidados: (log₂ n vueltas) × (Θ(n) por vuelta) = **Θ(n log n)**.
Es literalmente el árbol de recursión de Mergesort recorrido de abajo
hacia arriba: log n niveles, n trabajo por nivel.

## Difícil e04 — `ContarInversiones` · **Θ(n log n)**

El código es Mergesort con un contador extra en la fusión (`inv +=
medio - i + 1`, que es O(1) por elemento fusionado). La ecuación **no
cambia**:

```
T(n) = 2·T(n/2) + Θ(n)
```

División, a = 2, b = 2, f = n = n^(log₂ 2) → **Caso 2** → **Θ(n log n)**.
(La versión ingenua de contar pares desordenados con dos bucles es Θ(n²):
mismo problema, algoritmo mejor gracias a la estructura de Mergesort.)

## Difícil e05 — `Kadane` · **Θ(n)**

Un solo bucle, cuerpo O(1) (dos `Math.max`). **Θ(n)**. Lo notable es lo
que reemplaza: la solución por fuerza bruta prueba todos los subarreglos
(dos o tres bucles anidados → Θ(n²) o Θ(n³)). Mismo problema, tres
complejidades: el algoritmo importa.

## Difícil e06 — `BusquedaEnRotado` · **Θ(log n)** peor caso

Cada vuelta del `while` hace O(1) comparaciones y descarta **una mitad**
(decide cuál mirando cuál de las dos está ordenada). Es la ecuación de la
búsqueda binaria:

```
T(n) = T(n/2) + O(1)
```

División: a = 1, b = 2, n^(log₂ 1) = 1 = f(n) → **Caso 2** → **Θ(log n)**.
Expansión: T(n) = T(n/2^k) + k → k = log₂ n → Θ(log n). ✔

## Difícil e07 — `PotenciaModular` · **Θ(log exp)**

Mismo esqueleto que `PotenciaRapida` (medio e02): el exponente se divide
por 2 en cada vuelta (`e >>= 1`), cuerpo O(1) (multiplicaciones módulo m).

T(e) = T(e/2) + O(1) → división, Caso 2 → **Θ(log e)**. La gracia del
módulo es que los números **no crecen** (siempre < m), así que cada
multiplicación sigue siendo O(1) de verdad.

## Difícil e08 — `Hanoi` · movimientos **Θ(2ⁿ)**, calcularlos **Θ(n)** o **Θ(1)**

Acá hay que separar **dos preguntas distintas** (es la trampa del ejercicio):

**(1) ¿Cuántos movimientos de discos hace la Torre de Hanói?** La regla
"mover n discos = mover n−1, mover el grande, mover n−1 de nuevo" da la
recurrencia **del valor**:

```
M(n) = 2·M(n-1) + 1,        M(0) = 0
```

**Teorema por sustracción, paso a paso:**

1. ¿Resta o divide? n−1: **resta** → sustracción.
2. **a = 2** (dos "llamadas"), **b = 1**, **k = 0** (el +1 es O(n⁰)).
3. Mirar a: **a = 2 > 1** → tercer renglón → O(n^k · a^(n/b)) = O(n⁰·2ⁿ)
   = **O(2ⁿ)**. Exponencial: restar de a 1 y duplicar llamadas explota.

**Verificación por expansión (y de paso el valor exacto):**

```
M(n) = 2·M(n-1) + 1
     = 2·[2·M(n-2) + 1] + 1     = 4·M(n-2) + 2 + 1
     = 8·M(n-3) + 4 + 2 + 1
     ...
     = 2^k·M(n-k) + (2^(k-1) + ... + 2 + 1) = 2^k·M(n-k) + 2^k - 1
```

Caso base: k = n → M(n) = 2ⁿ·M(0) + 2ⁿ − 1 = **2ⁿ − 1 exactamente**. ✔

**(2) ¿Cuánto cuesta CALCULAR ese número?** `movimientosRecursivo` hace
**una** sola llamada por nivel: T(n) = T(n−1) + O(1) → sustracción, a = 1,
k = 0 → **Θ(n)**. Y `movimientos` usa la fórmula cerrada `(1L << n) - 1`
→ **Θ(1)**. Tres complejidades distintas para el mismo problema, según
qué se esté midiendo: **leé siempre qué cuenta T(n)**.

## Difícil e09 — `FibonacciRapido` · **Θ(log n)**

El `for` recorre los **bits** de n (de `highestOneBit` hacia abajo,
`bit >>= 1`): un número n tiene ⌊log₂ n⌋ + 1 bits, y el cuerpo es O(1)
(unas pocas multiplicaciones). → **Θ(log n)**.

Contraste histórico de la materia: el Fibonacci recursivo ingenuo es
T(n) = T(n−1) + T(n−2) + O(1) ≤ 2·T(n−1) + O(1) → sustracción con a = 2 →
**O(2ⁿ)**. Del exponencial al logarítmico: el mismo F(n), dos mundos.

## Difícil e10 — `MergeKArreglos` · **Θ(N log k)** (N = total de elementos, k = cantidad de arreglos)

**La ecuación, leída de `fusionarRango`:** el rango de **k arreglos** se
parte en dos mitades (dos llamadas sobre k/2 arreglos) y después
`fusionarDos` fusiona los dos resultados, recorriendo **todos los
elementos involucrados** (Θ de la suma de sus longitudes):

```
T(k) = 2·T(k/2) + Θ(N del rango)
```

Ojo: acá conviven **dos variables** (k arreglos, N elementos totales), así
que lo más claro es el **árbol de recursión**:

- Nivel 0: una fusión final que junta los N elementos → Θ(N).
- Nivel 1: dos fusiones que, entre las dos, juntan los N elementos → Θ(N).
- Nivel i: 2^i fusiones que en total mueven N elementos → **Θ(N) por nivel**.
- Altura: se divide k por 2 hasta llegar a 1 → **log₂ k niveles**.

Total: (Θ(N) por nivel) × (log₂ k niveles) = **Θ(N log k)**.

**Chequeo con el teorema** (caso particular: k arreglos de igual longitud
m, N = k·m): T(k) = 2·T(k/2) + Θ(k·m). Con m fijo: a = 2, b = 2,
f(k) = k → empata con k^(log₂ 2) = k → **Caso 2** → Θ(k·m·... ) =
Θ(N log k). ✔ La fusión ingenua (ir fusionando de a uno contra un
acumulado) es Θ(N·k): otra vez, la forma del árbol importa.

---

# Cuadro final de repaso

| Ejercicio | Ecuación (si aplica) | Teorema y caso | Resultado |
|-----------|----------------------|----------------|-----------|
| F-e01 Suma | — | reglas iterativas | Θ(n) |
| F-e02 ParesIguales | — | suma aritmética | Θ(n²) |
| F-e04 Duplicados | — | corte temprano | O(n²), Ω(1) |
| F-e06 Digitos | T(n)=T(n/10)+O(1) | división, Caso 2 | Θ(log n) |
| M-e01 BúsqBinaria | T(n)=T(n/2)+O(1) | división, Caso 2 | Θ(log n) |
| M-e05 MergeSort | T(n)=2T(n/2)+n | división, Caso 2 | Θ(n log n) |
| M-e06/e07 SumaRec/Factorial | T(n)=T(n−1)+O(1) | sustracción, a=1, k=0 | Θ(n) |
| M-e08 MaximoDyV | T(n)=2T(n/2)+O(1) | división, **Caso 1** | Θ(n) |
| D-e01 QuickSort prom. | T(n)=2T(n/2)+n | división, Caso 2 | Θ(n log n) |
| D-e01 QuickSort peor | T(n)=T(n−1)+n | sustracción, a=1, k=1 | O(n²) |
| D-e02 QuickSelect prom. | T(n)=T(n/2)+n | división, **Caso 3** | Θ(n) |
| D-e04 Inversiones | T(n)=2T(n/2)+n | división, Caso 2 | Θ(n log n) |
| D-e08 Hanói (movs.) | M(n)=2M(n−1)+1 | sustracción, **a=2** | Θ(2ⁿ) |
| D-e10 MergeK | T(k)=2T(k/2)+Θ(N) | árbol / Caso 2 | Θ(N log k) |

Fijate que en la guía aparecen **los tres casos** del teorema por división
(1: MaximoDyV, 2: casi todos, 3: QuickSelect) y **los dos renglones útiles**
del de sustracción (a = 1: factorial y peor Quicksort; a > 1: Hanói).
Si sabés reconocer estos moldes, tenés cubierto el parcial — y si un
ejercicio no calza en ningún molde, siempre queda el método universal:
**expandir y mirar el patrón**.

