# Punto 1 — Complejidad

> Determinar el orden de la siguiente relación de recurrencia
> `T(n) = 2 T(n/2) + 3n` con `T(1) = 1`

**Respuesta: `T(n) = Θ(n log n)`**

Se resuelve por los dos caminos del apunte (sección 4.10). Tienen que dar lo mismo.

**Qué dice la ecuación, antes de resolverla:** para un problema de tamaño `n` se hacen
**dos** llamadas recursivas, cada una sobre **la mitad** del tamaño, y además un trabajo
lineal `3n`. El caso base `T(1) = 1` dice que un problema de un elemento cuesta una constante.

---

## Camino A: método de expansión

**Paso 1: el molde.** La ecuación vale para cualquier tamaño. Reemplazando `n` por `n/2`
en los tres lugares:

```
T(n/2) = 2 T(n/4) + 3n/2
```

**Paso 2: primer reemplazo.** Meto el molde en la original y distribuyo el 2:

```
T(n) = 2 [ 2 T(n/4) + 3n/2 ] + 3n
     = 4 T(n/4) + 3n + 3n
     = 4 T(n/4) + 6n
```

**Paso 3: segundo reemplazo.** El molde con `n/4`: `T(n/4) = 2 T(n/8) + 3n/4`. Distribuyo el 4:

```
T(n) = 4 [ 2 T(n/8) + 3n/4 ] + 6n
     = 8 T(n/8) + 3n + 6n
     = 8 T(n/8) + 9n
```

**Paso 4: tercer reemplazo**, para estar seguros del patrón. El molde con `n/8`:
`T(n/8) = 2 T(n/16) + 3n/8`. Distribuyo el 8:

```
T(n) = 8 [ 2 T(n/16) + 3n/8 ] + 9n
     = 16 T(n/16) + 3n + 9n
     = 16 T(n/16) + 12n
```

**Paso 5: el patrón.**

| Reemplazos `k` | Coeficiente | Argumento de `T` | Trabajo acumulado |
|:--------------:|:-----------:|:----------------:|:-----------------:|
| 0              | 1 = 2⁰      | n = n/2⁰         | 0 · 3n            |
| 1              | 2 = 2¹      | n/2 = n/2¹       | 1 · 3n            |
| 2              | 4 = 2²      | n/4 = n/2²       | 2 · 3n            |
| 3              | 8 = 2³      | n/8 = n/2³       | 3 · 3n            |
| 4              | 16 = 2⁴     | n/16 = n/2⁴      | 4 · 3n            |
| k              | 2ᵏ          | n/2ᵏ             | k · 3n            |

Después de `k` reemplazos:

```
T(n) = 2^k T(n / 2^k) + 3 k n
```

Por qué cada columna: el coeficiente se multiplica por 2 en cada reemplazo porque hay 2
llamadas; el argumento se divide por 2 porque cada llamada es sobre la mitad; y el trabajo
suma un `3n` por reemplazo porque el coeficiente `2^j` multiplica al trabajo `3n/2^j` del
nivel `j` y da exactamente `3n`.

**Paso 6: el caso base.** La expansión termina cuando el argumento de `T` llega a 1:

```
n / 2^k = 1
```

**Paso 7: despejar `k`.** Multiplico por `2^k` y uso la definición de logaritmo
(`b^k = n  ⟺  k = log_b n`):

```
2^k = n   ⟹   k = log2 n
```

**Paso 8: sustituir en el patrón.** Reemplazo `k` por `log2 n` y uso `2^(log2 n) = n` y `T(1) = 1`:

```
T(n) = 2^(log2 n) · T(1) + 3 · (log2 n) · n
     = n · 1 + 3 n log2 n
     = n + 3 n log2 n
```

**Paso 9: la cota.** De los dos términos manda el mayor, `3 n log2 n`; el `n` suelto es de
orden menor. La constante 3 y la base del logaritmo no importan dentro de Θ:

```
T(n) = Θ(n log n)
```

---

## Camino B: Teorema Maestro por división

**Paso 1: identificar `a`, `b` y `f(n)`.** Comparo con el molde `T(n) = a T(n/b) + f(n)`:

```
a = 2  (dos llamadas)      b = 2  (cada una sobre n/2)      f(n) = 3n
```

Chequeo que se pueda aplicar: `a = 2 ≥ 1`, `b = 2 > 1`, y la recurrencia divide, no resta. Se puede.

**Paso 2: el exponente crítico.** `log_b a = log2 2 = 1` (base y argumento iguales: `2¹ = 2`).
La función contra la que hay que comparar es:

```
n^(log_b a) = n^1 = n
```

**Paso 3: comparar `f(n)` con `n^(log_b a)`.** Tengo `f(n) = 3n` y `n^(log_b a) = n`.
Difieren sólo en la constante 3: `f(n) = Θ(n)`. No hay un `ε > 0` que haga a `f`
polinómicamente menor (sería caso 1) ni polinómicamente mayor (sería caso 3): es un **empate**.

**Paso 4: elegir el caso.** Empate es el **caso 2**: `T(n) = Θ(n^(log_b a) · log n)`.

**Paso 5: la respuesta.**

```
T(n) = Θ(n^1 · log n) = Θ(n log n)
```

**Paso 6: verificar.** Coincide con el camino A. Y coincide con el árbol de recursión:
`log2 n` niveles que trabajan `3n` cada uno.

---

## Chequeo con números

Con la fórmula exacta del paso 8, `T(n) = n + 3 n log2 n`:

| n  | Por la recurrencia              | Por la fórmula          |
|:--:|:--------------------------------|:------------------------|
| 1  | 1                               | 1 + 3·1·0 = 1           |
| 2  | 2·T(1) + 3·2 = 2 + 6 = 8        | 2 + 3·2·1 = 8           |
| 4  | 2·T(2) + 3·4 = 16 + 12 = 28     | 4 + 3·4·2 = 28          |
| 8  | 2·T(4) + 3·8 = 56 + 24 = 80     | 8 + 3·8·3 = 80          |

## Sugerencia para corregir

- Que estén **todos los pasos**, no sólo el resultado (la ecuación, el molde, dos o tres
  reemplazos con la distribución explícita, la tabla, la fórmula con `k`, el caso base, el
  despeje, la sustitución y la cota).
- El error típico acá es olvidarse de distribuir el coeficiente sobre el `3n/2` y terminar
  con un trabajo acumulado que no es `3kn`.
- Con **uno** de los dos caminos bien hecho el orden queda determinado; el segundo sirve para verificar.
