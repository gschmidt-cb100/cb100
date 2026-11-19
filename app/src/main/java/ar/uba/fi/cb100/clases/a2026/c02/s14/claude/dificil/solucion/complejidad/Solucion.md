# Punto 1 — Complejidad

> Determinar el orden de las siguientes relaciones de recurrencia, ambas con `T(1) = 1`,
> justificando el método utilizado:
>
> a) `T(n) = 2 T(n/2) + n log n`
> b) `T(n) = T(n/3) + T(2n/3) + n`

**Respuestas: a) `T(n) = Θ(n log² n)`   b) `T(n) = Θ(n log n)`**

Lo difícil de este punto no es la cuenta: es darse cuenta de que **ninguna de las dos entra
en el Teorema Maestro tal como lo vimos**, decir por qué, y elegir otro método. Por eso el
enunciado pide justificar el método.

---

## a) `T(n) = 2 T(n/2) + n log n`

Los logaritmos son en base 2 (cualquier otra base cambia sólo una constante).

### Por qué NO sirve el Teorema Maestro por división

**Paso 1: `a`, `b` y `f(n)`.** `a = 2`, `b = 2`, `f(n) = n log n`. Divide, no resta: el molde calza.

**Paso 2: el exponente crítico.** `log_b a = log2 2 = 1`, así que se compara contra `n^1 = n`.

**Paso 3: comparar `f(n) = n log n` con `n`.**

- **No es caso 2** (empate): `n log n` no es `Θ(n)`, crece más.
- **No es caso 1**: `f` no es menor que `n`.
- **Tampoco es caso 3**: el caso 3 pide que `f` sea *polinómicamente* mayor, es decir
  `f(n) = Ω(n^(1+ε))` para algún `ε > 0`. Pero `f(n) / n = log n`, y `log n` crece más
  lento que `n^ε` para **cualquier** `ε > 0`, por chico que sea. `f` le gana a `n`, pero no
  por un factor polinómico.

La recurrencia cae en el **hueco entre el caso 2 y el caso 3**: los tres casos del teorema
no la cubren. Hay que expandir.

> El error típico es decir "`n log n` es mayor que `n`, entonces caso 3, `Θ(n log n)`".
> Está mal, y la expansión de abajo muestra que el resultado real es mayor.

### Método de expansión

**Paso 1: el molde.** Reemplazando `n` por `n/2` en los tres lugares:

```
T(n/2) = 2 T(n/4) + (n/2) log(n/2)
```

**Paso 2: primer reemplazo.** Distribuyo el 2:

```
T(n) = 2 [ 2 T(n/4) + (n/2) log(n/2) ] + n log n
     = 4 T(n/4) + n log(n/2) + n log n
```

**Paso 3: segundo reemplazo.** El molde con `n/4`: `T(n/4) = 2 T(n/8) + (n/4) log(n/4)`. Distribuyo el 4:

```
T(n) = 4 [ 2 T(n/8) + (n/4) log(n/4) ] + n log(n/2) + n log n
     = 8 T(n/8) + n log(n/4) + n log(n/2) + n log n
```

**Paso 4: el patrón.** A diferencia de Mergesort, acá cada nivel **no** aporta lo mismo:
el nivel `j` aporta `n log(n/2^j)`.

| `k`                      | Coeficiente | Argumento de `T` | Trabajo que agrega ese nivel |
|:------------------------:|:-----------:|:----------------:|:-----------------------------|
| 1 (la ecuación original) | 2¹          | n/2¹             | n log(n/2⁰) = n log n        |
| 2 (primer reemplazo)     | 2²          | n/2²             | n log(n/2¹)                  |
| 3 (segundo reemplazo)    | 2³          | n/2³             | n log(n/2²)                  |
| k                        | 2ᵏ          | n/2ᵏ             | n log(n/2ᵏ⁻¹)                |

En general:

```
T(n) = 2^k T(n / 2^k) + n · Σ (j = 0 .. k-1) log(n / 2^j)
```

**Paso 5: simplificar la suma.** Uso `log(n / 2^j) = log n − j` (logaritmo de un cociente, y `log2 2^j = j`):

```
Σ (j = 0 .. k-1) (log n − j) = k log n − (0 + 1 + ... + (k−1))
                             = k log n − k (k−1) / 2
```

(la suma de los primeros `k−1` naturales, Gauss). Queda:

```
T(n) = 2^k T(n / 2^k) + n [ k log n − k (k−1) / 2 ]
```

**Paso 6: el caso base.** `n / 2^k = 1`.

**Paso 7: despejar `k`.** `2^k = n  ⟹  k = log2 n`.

**Paso 8: sustituir.** Llamo `L = log2 n` para no arrastrar el logaritmo. Con `k = L`, `2^L = n` y `T(1) = 1`:

```
T(n) = n · 1 + n [ L · L − L (L−1) / 2 ]
     = n + n [ (2L² − L² + L) / 2 ]
     = n + n (L² + L) / 2
     = n + (n log² n) / 2 + (n log n) / 2
```

**Paso 9: la cota.** De los tres términos manda `(n log² n) / 2`:

```
T(n) = Θ(n log² n)
```

### Chequeo con números

Con la fórmula exacta `T(n) = n + n (L² + L) / 2`, donde `L = log2 n`:

| n  | L | Por la recurrencia                | Por la fórmula              |
|:--:|:-:|:----------------------------------|:----------------------------|
| 1  | 0 | 1                                 | 1 + 1·0/2 = 1               |
| 2  | 1 | 2·T(1) + 2·1 = 4                  | 2 + 2·2/2 = 4               |
| 4  | 2 | 2·T(2) + 4·2 = 16                 | 4 + 4·6/2 = 16              |
| 8  | 3 | 2·T(4) + 8·3 = 56                 | 8 + 8·12/2 = 56             |
| 16 | 4 | 2·T(8) + 16·4 = 176               | 16 + 16·20/2 = 176          |

### La misma idea en el árbol de recursión

Hay `log2 n` niveles. En Mergesort todos trabajan `n` (caso 2, empate). Acá el nivel `j`
trabaja `n (log n − j)`: arranca en `n log n` y baja de a `n` por nivel. No es un empate,
pero tampoco se achica geométricamente como en el caso 3 (donde manda la raíz): es una suma
aritmética de `log n` términos del orden de `n log n`, y por eso aparece el **`log` al cuadrado**.

> Para quien quiera ir más lejos: existe una versión extendida del caso 2 que cubre
> justamente esto. Si `f(n) = Θ(n^(log_b a) · log^k n)` con `k ≥ 0`, entonces
> `T(n) = Θ(n^(log_b a) · log^(k+1) n)`. Con `k = 1` da `Θ(n log² n)`. No está en el apunte:
> en el parcial hay que expandir.

---

## b) `T(n) = T(n/3) + T(2n/3) + n`

### Por qué NO sirve el Teorema Maestro por división

El molde es `T(n) = a T(n/b) + f(n)`: `a` llamadas, **todas sobre el mismo tamaño** `n/b`.
Acá hay dos llamadas sobre tamaños **distintos** (`n/3` y `2n/3`): no existe un único `b`. No calza.

Tampoco conviene la expansión algebraica: al reemplazar aparecen `T(n/9)`, `T(2n/9)`,
`T(4n/9)`... y los términos no se agrupan en una sola fila con `k`.

**¿Y acotar, como hicimos con Fibonacci?** Como `T(n/3) ≤ T(2n/3)`, vale
`T(n) ≤ 2 T(2n/3) + n`, que sí calza: `a = 2`, `b = 3/2`, `log_1.5 2 ≈ 1,71`, caso 1,
`T(n) = O(n^1,71)`. Es una cota **válida pero muy floja**: sirve como techo, no como orden.
Para el orden exacto hay que mirar el árbol.

### Método: árbol de recursión

**Paso 1: cuánto trabaja cada nivel.**

```
nivel 0:                        n                               suma: n
nivel 1:              n/3              2n/3                     suma: n
nivel 2:        n/9      2n/9     2n/9      4n/9                suma: n
nivel 3:   n/27 2n/27  2n/27 4n/27  2n/27 4n/27  4n/27 8n/27    suma: n
```

Cada nodo de tamaño `m` reparte su tamaño entre sus dos hijos, `m/3 + 2m/3 = m`: nada se
pierde ni se duplica. Entonces **todo nivel completo suma exactamente `n`**, y un nivel al
que ya le faltan ramas (porque algunas llegaron al caso base) suma **a lo sumo `n`**.

**Paso 2: cuántos niveles hay.** El árbol no es parejo: las ramas no terminan todas a la misma altura.

- La rama **más corta** es la que siempre va por `n/3`. Termina cuando `n / 3^k = 1`:

  ```
  3^k = n   ⟹   k = log3 n
  ```

- La rama **más larga** es la que siempre va por `2n/3`. Termina cuando `n · (2/3)^k = 1`:

  ```
  (3/2)^k = n   ⟹   k = log_1.5 n
  ```

**Paso 3: cota inferior.** Hasta la profundidad `log3 n` ninguna rama terminó: son `log3 n`
niveles completos, y cada uno suma `n`.

```
T(n) ≥ n · log3 n          ⟹   T(n) = Ω(n log n)
```

**Paso 4: cota superior.** El árbol tiene a lo sumo `log_1.5 n` niveles, y cada uno suma a lo
sumo `n`. Falta el costo de las hojas: cada hoja cuesta `T(1) = 1` y hay a lo sumo `n` (la
cantidad de hojas cumple `H(n) = H(n/3) + H(2n/3)` con `H(1) = 1`, que da `H(n) = n`).

```
T(n) ≤ n · log_1.5 n + n   ⟹   T(n) = O(n log n)
```

**Paso 5: la cota.** `log3 n` y `log_1.5 n` difieren sólo en una constante
(`log_b n = log2 n / log2 b`), así que las dos cotas son del mismo orden:

```
n log3 n  ≤  T(n)  ≤  n log_1.5 n + n        ⟹        T(n) = Θ(n log n)
```

### Verificación por sustitución

Para confirmar el techo sin apoyarse en el dibujo: propongo `T(m) ≤ c · m · log2 m` para
todo `m < n` y lo meto en la recurrencia.

```
T(n) ≤ c (n/3) log(n/3) + c (2n/3) log(2n/3) + n
     = c (n/3) (log n − log 3) + c (2n/3) (log n − log 1,5) + n
     = c n log n − c n [ (1/3) log 3 + (2/3) log 1,5 ] + n
     = c n log n − n (0,918 c − 1)
```

(`(1/3) · 1,585 + (2/3) · 0,585 = 0,918`). Lo que se resta es no negativo si `c ≥ 1 / 0,918 ≈ 1,09`.
Con ese `c`, `T(n) ≤ c n log n`: la propuesta se sostiene, `T(n) = O(n log n)`.

### Qué muestra este ejercicio

Partir en dos pedazos **desparejos** (un tercio y dos tercios) cuesta lo mismo, en orden,
que partir por la mitad: `Θ(n log n)`, igual que Mergesort. Lo que cambia es la constante
(la altura pasa de `log2 n` a, como mucho, `log_1.5 n ≈ 1,71 · log2 n`). Es la razón por la
que Quicksort sigue siendo `O(n log n)` aunque el pivote no caiga justo en el medio,
mientras cada parte se quede con una fracción fija del total.

---

## Sugerencia para corregir

- **Justificar el método**: decir explícitamente por qué el Teorema Maestro no aplica en
  cada inciso. Aplicarlo igual (caso 3 en el a, o inventar un `b` en el b) invalida el inciso.
- En el a): la expansión con la suma `Σ (log n − j)` bien resuelta. El resultado
  `Θ(n log n)` es el error esperable.
- En el b): las **dos** cotas. Con sólo el techo se tiene `O(n log n)`, no `Θ`.
