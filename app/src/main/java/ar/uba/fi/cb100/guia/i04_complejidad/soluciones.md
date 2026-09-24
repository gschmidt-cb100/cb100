# Complejidad — soluciones detalladas de los 30 ejercicios

Cada ejercicio se resuelve mostrando **todo el proceso**, no sólo el
resultado. Los iterativos, **contando** las operaciones. Los recursivos, de
**dos formas** siempre: por **expansión** de la ecuación de recurrencia y por
el **teorema maestro**, con el paso a paso de cómo se elige el caso. Cuando el
teorema no aplica, se dice por qué.

Las fórmulas están en texto plano y monoespaciado: `n^2` es n al cuadrado,
`log2 n` es logaritmo en base 2, `2^k` es 2 a la k.

---

## 0. Las dos herramientas

### 0.1 Expansión (o "desenrollado") de la recurrencia

Se reemplaza `T(...)` por su definición una y otra vez hasta que aparece un
patrón en función de `k`, la cantidad de veces que se expandió. Después se
busca el `k` que llega al caso base y se reemplaza.

```
T(n) = T(n/2) + c
     = [T(n/4) + c] + c            = T(n/4)  + 2c
     = [T(n/8) + c] + 2c           = T(n/8)  + 3c
     ...
     = T(n/2^k) + k*c              <- el patron

Caso base: n/2^k = 1   =>   2^k = n   =>   k = log2 n

T(n) = T(1) + c*log2 n = Theta(log n)
```

Los tres pasos, siempre los mismos: **(1)** expandir hasta ver el patrón en
`k`, **(2)** resolver `k` con el caso base, **(3)** reemplazar y simplificar.

### 0.2 Teorema maestro

Sirve para recurrencias de la forma

```
T(n) = a * T(n/b) + f(n)        con a >= 1, b > 1
```

donde `a` es cuántas llamadas recursivas se hacen, `b` por cuánto se divide
el problema, y `f(n)` el trabajo que se hace **fuera** de la recursión
(dividir y combinar).

**Cómo elegir el caso, paso a paso:**

```
Paso 1. Identificar a, b y f(n) en la recurrencia.

Paso 2. Calcular el EXPONENTE CRITICO:  e = log_b a
        y escribir la funcion critica:  n^e

Paso 3. Comparar f(n) contra n^e. Hay tres posibilidades:

   f(n) crece POLINOMICAMENTE MENOS que n^e
        (f(n) = O(n^(e - eps)) para algun eps > 0)
        => CASO 1:  T(n) = Theta(n^e)
           "manda la recursion": las hojas del arbol dominan

   f(n) crece IGUAL que n^e
        (f(n) = Theta(n^e))
        => CASO 2:  T(n) = Theta(n^e * log n)
           "empate": cada nivel del arbol cuesta lo mismo, y hay log n niveles

   f(n) crece POLINOMICAMENTE MAS que n^e
        (f(n) = Omega(n^(e + eps)) para algun eps > 0,
         y ademas a*f(n/b) <= c*f(n) para algun c < 1: la "condicion de regularidad")
        => CASO 3:  T(n) = Theta(f(n))
           "manda el trabajo de afuera": la raiz del arbol domina
```

**La palabra clave es "polinómicamente".** Para estar en el caso 1 o 3 la
diferencia tiene que ser al menos un `n^eps`, con `eps > 0`. Si `f(n) = n log n`
y `n^e = n`, `f` es más grande pero **no** polinómicamente más grande (un
`log n` no es ningún `n^eps`): no es caso 3, ni caso 2, ni caso 1. Es un
**hueco** del teorema y hay que resolver por expansión. Ese ejemplo no está en
la guía, pero es la pregunta de parcial clásica.

**Cuándo NO aplica el teorema maestro:**

- Cuando el problema se **resta** en vez de dividirse: `T(n) = T(n-1) + ...`
  o `T(n) = 2T(n-1) + ...`. No hay `b`. Se resuelve por expansión.
- Cuando hay dos llamadas de distinto tamaño: `T(n) = T(n-1) + T(n-2) + c`.
- Cuando `a < 1` o `f(n)` no es positiva.

---

## 1. Tabla resumen

| Ejercicio | Recurrencia | Método | Resultado |
|---|---|---|---|
| F-e01 SumaDeArreglo | — (iterativo) | conteo | Θ(n) |
| F-e02 ParesIguales | — | conteo | Θ(n²) |
| F-e03 MaximoDeArreglo | — | conteo | Θ(n) |
| F-e04 DeteccionDuplicados | — | conteo | O(n²), mejor O(1) |
| F-e05 PotenciaLenta | — | conteo | Θ(exp) |
| F-e06 CantidadDigitos | — | conteo | Θ(log n) |
| F-e07 BusquedaLineal | — | conteo | O(n), mejor O(1) |
| F-e08 InvertirArreglo | — | conteo | Θ(n) |
| F-e09 PotenciaDeDos | — | conteo | O(log n), mejor O(1) |
| F-e10 ConteoIteraciones | — | conteo | Θ(log n) |
| M-e01 BusquedaBinaria | T(n) = T(n/2) + c | expansión + maestro caso 2 | Θ(log n) |
| M-e02 PotenciaRapida | T(e) = T(e/2) + c | expansión + maestro caso 2 | Θ(log exp) |
| M-e03 BubbleSort | — | conteo | O(n²), mejor Θ(n) |
| M-e04 InsertionSort | — | conteo | O(n²), mejor Θ(n) |
| M-e05 MergeSort | T(n) = 2T(n/2) + cn | expansión + maestro caso 2 | Θ(n log n) |
| M-e06 SumaRecursiva | T(n) = T(n-1) + c | expansión; maestro **no aplica** | Θ(n) |
| M-e07 Factorial | T(n) = T(n-1) + c | expansión; maestro **no aplica** | Θ(n) |
| M-e08 MaximoDyV | T(n) = 2T(n/2) + c | expansión + maestro caso 1 | Θ(n) |
| M-e09 ContarApariciones | 2 × [T(n) = T(n/2) + c] | expansión + maestro caso 2 | Θ(log n) |
| M-e10 SelectionSort | — | conteo | Θ(n²) siempre |
| D-e01 QuickSort | prom. 2T(n/2) + cn; peor T(n-1) + cn | maestro caso 2; expansión | Θ(n log n); Θ(n²) |
| D-e02 QuickSelect | prom. T(n/2) + cn; peor T(n-1) + cn | maestro caso 3; expansión | Θ(n); Θ(n²) |
| D-e03 MergeSortIterativo | — (iterativo) | conteo por pasadas | Θ(n log n) |
| D-e04 ContarInversiones | T(n) = 2T(n/2) + cn | expansión + maestro caso 2 | Θ(n log n) |
| D-e05 Kadane | — | conteo | Θ(n) |
| D-e06 BusquedaEnRotado | T(n) = T(n/2) + c | expansión + maestro caso 2 | Θ(log n) |
| D-e07 PotenciaModular | T(e) = T(e/2) + c | expansión + maestro caso 2 | Θ(log exp) |
| D-e08 Hanoi | T(n) = 2T(n-1) + 1 | expansión; maestro **no aplica** | Θ(2^n) |
| D-e09 FibonacciRapido | T(n) = T(n/2) + c | expansión + maestro caso 2 | Θ(log n) |
| D-e10 MergeKArreglos | T(k) = 2T(k/2) + m·k | expansión + maestro caso 2 | Θ(N log k) |

---

## 2. Fácil: contar operaciones

### F-e01 · SumaDeArreglo

```java
for (int i = 0; i < a.length; i++) {   // n vueltas
    acumulador += a[i];                // O(1) cada una
}
```

```
Operaciones = n vueltas * 1 suma = n     =>  Theta(n)
```

No hay mejor ni peor caso: siempre recorre todo.

### F-e02 · ParesIguales

```java
for (int i = 0; i < n; i++)            // i = 0 .. n-1
    for (int j = i + 1; j < n; j++)    // n-1-i vueltas para cada i
        if (a[i] == a[j]) contador++;
```

El interno **no** hace `n` vueltas: hace `n-1-i`. Hay que sumar:

```
i = 0    ->  n-1 comparaciones
i = 1    ->  n-2
...
i = n-2  ->  1
i = n-1  ->  0
-----------------------------
Total = (n-1) + (n-2) + ... + 1 + 0 = n(n-1)/2 = n^2/2 - n/2   =>  Theta(n^2)
```

El `/2` y el `-n/2` no cambian el orden: lo que manda es `n^2`.

### F-e03 · MaximoDeArreglo

```
1 asignacion + (n-1) comparaciones   =>  Theta(n)
```

### F-e04 · DeteccionDuplicados

Mismo doble bucle que F-e02, pero con `return true` apenas encuentra un par.

```
Peor caso (sin duplicados, o el unico par al final): n(n-1)/2   =>  O(n^2)
Mejor caso (a[0] == a[1]):                            1          =>  O(1)
```

Se escribe **O(n²)** y no Θ(n²): la cota superior es cuadrática pero no todos
los casos la alcanzan.

### F-e05 · PotenciaLenta

```
exp vueltas * 1 multiplicacion   =>  Theta(exp)
```

Ojo con qué es "n": acá el tamaño de la entrada es el **exponente**, no la
base. Compará con M-e02, que hace lo mismo en Θ(log exp).

### F-e06 · CantidadDigitos

```
while (valor > 0) { valor /= 10; digitos++; }
```

Cada vuelta divide por 10, o sea saca un dígito. Un número `n` tiene
`floor(log10 n) + 1` dígitos:

```
vueltas = floor(log10 n) + 1   =>  Theta(log n)
```

La base del logaritmo no importa para el orden: `log10 n = log2 n / log2 10`,
y `1/log2 10` es una constante.

### F-e07 · BusquedaLineal

```
Peor caso (no esta, o esta ultimo): n comparaciones   =>  O(n)
Mejor caso (esta primero):          1                 =>  O(1)
Promedio (esta, uniforme):          n/2               =>  O(n)
```

### F-e08 · InvertirArreglo

```
Tiempo:  n copias                =>  Theta(n)
Memoria: un arreglo nuevo de n   =>  Theta(n) adicional
```

Es el único de los fáciles donde vale la pena decir la complejidad
**espacial**: la versión que invierte "en el lugar" intercambiando extremos
usa Θ(1) adicional.

### F-e09 · PotenciaDeDos

```
while (n % 2 == 0) n /= 2;
```

```
Peor caso (n = 2^k):  k = log2 n vueltas   =>  O(log n)
Mejor caso (n impar): 0 vueltas             =>  O(1)
```

### F-e10 · ConteoIteraciones

```
for (int i = 1; i < n; i *= 2)     // i = 1, 2, 4, 8, ..., < n
```

```
i toma los valores 2^0, 2^1, 2^2, ..., 2^(k-1) mientras 2^(k-1) < n
=>  k = ceil(log2 n) vueltas   =>  Theta(log n)
```

Verificación con el `main`: `iteraciones(8) = 3` (i = 1, 2, 4) y
`iteraciones(16) = 4` (i = 1, 2, 4, 8). Y efectivamente `log2 8 = 3`,
`log2 16 = 4`.

---

## 3. Medio

### M-e01 · BusquedaBinaria

```java
while (inicio <= fin) {
    int medio = inicio + (fin - inicio) / 2;
    if (a[medio] == x) return medio;
    else if (a[medio] < x) inicio = medio + 1;    // se queda con la mitad derecha
    else fin = medio - 1;                          // o con la izquierda
}
```

Aunque está escrita con un `while`, la lógica es recursiva: cada vuelta
resuelve **el mismo problema sobre la mitad**. La recurrencia:

```
T(n) = T(n/2) + c        (una llamada sobre la mitad, mas una comparacion)
T(1) = c
```

**Expansión:**

```
T(n) = T(n/2) + c
     = T(n/4) + c + c       = T(n/4)   + 2c
     = T(n/8) + c + 2c      = T(n/8)   + 3c
     = ...
     = T(n/2^k) + k*c

Caso base:  n/2^k = 1  =>  k = log2 n

T(n) = T(1) + c*log2 n = c + c*log2 n   =>  Theta(log n)
```

**Teorema maestro:**

```
Paso 1.  a = 1  (una llamada)     b = 2  (mitad)     f(n) = c = Theta(1) = Theta(n^0)
Paso 2.  e = log_b a = log2 1 = 0      =>  n^e = n^0 = 1
Paso 3.  f(n) = Theta(1) = Theta(n^0)  y  n^e = n^0   ->  crecen IGUAL
         =>  CASO 2
Resultado: T(n) = Theta(n^0 * log n) = Theta(log n)
```

Los dos métodos coinciden. Mejor caso: `a[medio] == x` en la primera vuelta,
O(1).

### M-e02 · PotenciaRapida

```java
while (e > 0) {
    if ((e & 1) == 1) resultado *= b;
    b *= b;
    e >>= 1;                   // e = e / 2
}
```

El exponente se divide por 2 en cada vuelta y se hace trabajo constante:

```
T(e) = T(e/2) + c
```

Es **exactamente la misma recurrencia** que la búsqueda binaria, con `e` en
lugar de `n`. Expansión y teorema maestro idénticos: `a = 1, b = 2,
f = Θ(1)`, exponente crítico 0, caso 2:

```
T(exp) = Theta(log exp)
```

Compará con F-e05: de Θ(exp) a Θ(log exp). Para `2^1000000`, de un millón de
multiplicaciones a veinte.

### M-e03 · BubbleSort

```java
for (int i = 0; i < n - 1; i++)                 // n-1 pasadas
    for (int j = 0; j < n - 1 - i; j++)         // n-1-i comparaciones
        if (a[j] > a[j+1]) intercambiar
    if (!huboIntercambio) break;
```

```
Peor caso (orden inverso):  (n-1) + (n-2) + ... + 1 = n(n-1)/2   =>  O(n^2)
Mejor caso (ya ordenado):   una pasada de n-1 comparaciones,
                            sin intercambios, y la bandera corta   =>  Theta(n)
```

Sin la bandera, el mejor caso también sería n(n-1)/2: la bandera es lo que
baja el mejor caso a lineal.

### M-e04 · InsertionSort

```java
for (int i = 1; i < n; i++) {
    clave = a[i]; j = i - 1;
    while (j >= 0 && a[j] > clave) { a[j+1] = a[j]; j--; }    // hasta i corridas
    a[j+1] = clave;
}
```

```
Peor caso (orden inverso):  el while corre i veces para cada i
                            1 + 2 + ... + (n-1) = n(n-1)/2         =>  O(n^2)
Mejor caso (ya ordenado):   el while no entra nunca: n-1 comparaciones  =>  Theta(n)
```

### M-e05 · MergeSort

```java
ordenarRango(a, aux, inicio, medio);       // T(n/2)
ordenarRango(a, aux, medio + 1, fin);      // T(n/2)
mezclar(a, aux, inicio, medio, fin);       // c*n: recorre todo el rango
```

```
T(n) = 2*T(n/2) + c*n
T(1) = c
```

**Expansión:**

```
T(n) = 2T(n/2) + cn
     = 2[2T(n/4) + c(n/2)] + cn      = 4T(n/4)  + cn + cn      = 4T(n/4)  + 2cn
     = 4[2T(n/8) + c(n/4)] + 2cn     = 8T(n/8)  + cn + 2cn     = 8T(n/8)  + 3cn
     = ...
     = 2^k * T(n/2^k) + k*c*n

Caso base:  n/2^k = 1  =>  k = log2 n,  y  2^k = n

T(n) = n*T(1) + c*n*log2 n = cn + cn*log2 n   =>  Theta(n log n)
```

Fijate el detalle en la segunda línea: las dos llamadas de tamaño `n/2`
cuestan `c(n/2)` cada una **de mezcla**, y `2 * c(n/2) = cn`. Por eso cada
nivel del árbol suma exactamente `cn`, y hay `log2 n` niveles.

**Teorema maestro:**

```
Paso 1.  a = 2     b = 2     f(n) = c*n = Theta(n) = Theta(n^1)
Paso 2.  e = log2 2 = 1      =>  n^e = n^1 = n
Paso 3.  f(n) = Theta(n)  y  n^e = n   ->  crecen IGUAL   =>  CASO 2
Resultado: T(n) = Theta(n^1 * log n) = Theta(n log n)
```

### M-e06 · SumaRecursiva

```java
private static long sumarDesde(int[] a, int i) {
    if (i == a.length) return 0;
    return a[i] + sumarDesde(a, i + 1);      // T(n-1) + c
}
```

```
T(n) = T(n-1) + c
T(0) = c
```

**Expansión:**

```
T(n) = T(n-1) + c
     = T(n-2) + c + c      = T(n-2) + 2c
     = T(n-3) + 3c
     = ...
     = T(n-k) + k*c

Caso base:  n - k = 0  =>  k = n

T(n) = T(0) + n*c = c + cn   =>  Theta(n)
```

**Teorema maestro: NO APLICA.** La recurrencia es `T(n-1)`, el problema se
**resta** de a uno, no se divide por ningún `b`. No hay forma de escribirla
como `a*T(n/b) + f(n)`. Es el error más común: intentar forzarla con `b = 1`
o algo parecido. Se resuelve sólo por expansión.

Complejidad espacial: Θ(n), porque hay `n` llamadas apiladas a la vez. La
versión iterativa (F-e01) hace lo mismo en Θ(1) de pila.

### M-e07 · Factorial

```java
return (long) n * factorial(n - 1);          // T(n-1) + c
```

Misma recurrencia que M-e06: `T(n) = T(n-1) + c`, expansión idéntica,
**Θ(n)**. Teorema maestro: **no aplica**, por la misma razón.

### M-e08 · MaximoDyV

```java
int maxIzq = maximoRango(a, inicio, medio);   // T(n/2)
int maxDer = maximoRango(a, medio + 1, fin);  // T(n/2)
return Math.max(maxIzq, maxDer);              // c
```

```
T(n) = 2*T(n/2) + c
T(1) = c
```

**Expansión.** Ahora el trabajo de afuera es constante, así que se acumula
distinto que en MergeSort:

```
T(n) = 2T(n/2) + c
     = 2[2T(n/4) + c] + c         = 4T(n/4) + 2c + c     = 4T(n/4) + 3c
     = 4[2T(n/8) + c] + 3c        = 8T(n/8) + 4c + 3c    = 8T(n/8) + 7c
     = ...
     = 2^k * T(n/2^k) + (2^k - 1)*c        <- 1 + 2 + 4 + ... + 2^(k-1) = 2^k - 1

Caso base:  n/2^k = 1  =>  k = log2 n,  2^k = n

T(n) = n*T(1) + (n-1)*c = cn + cn - c   =>  Theta(n)
```

Compará con MergeSort: allá cada nivel costaba `cn` (constante por nivel);
acá el nivel `j` cuesta `2^j * c` (se duplica por nivel), así que la suma es
una geométrica y **las hojas dominan**.

**Teorema maestro:**

```
Paso 1.  a = 2     b = 2     f(n) = c = Theta(1) = Theta(n^0)
Paso 2.  e = log2 2 = 1      =>  n^e = n
Paso 3.  f(n) = Theta(n^0)  vs  n^1:  f crece POLINOMICAMENTE MENOS
         (n^0 = O(n^(1 - eps)) con eps = 1)             =>  CASO 1
Resultado: T(n) = Theta(n^e) = Theta(n)
```

Es el primer ejercicio de la guía que cae en el caso 1. La razón intuitiva:
hay `n` hojas en el árbol de recursión, cada una cuesta `c`, y eso ya es
Θ(n); el trabajo de combinar es despreciable.

### M-e09 · ContarApariciones

Dos búsquedas binarias independientes, una que se corre a la izquierda al
encontrar y otra a la derecha. Cada una es M-e01:

```
T(n) = 2 * Theta(log n) + c = Theta(log n)
```

Cada búsqueda: `T(n) = T(n/2) + c`, `a = 1, b = 2, f = Θ(1)`, exponente
crítico 0, **caso 2**, Θ(log n). El `2 *` es una constante y desaparece.

Fijate que estas búsquedas **no cortan** al encontrar: siguen hasta agotar
el rango para asegurarse de que es la primera (o última) posición. Así que
mejor y peor caso son los mismos: Θ(log n) siempre.

### M-e10 · SelectionSort

```java
for (int i = 0; i < n - 1; i++)
    for (int j = i + 1; j < n; j++)      // n-1-i comparaciones, SIEMPRE
        if (a[j] < a[indiceMin]) ...
```

```
(n-1) + (n-2) + ... + 1 = n(n-1)/2   =>  Theta(n^2)  en TODOS los casos
```

A diferencia de Bubble e Insertion, no hay forma de cortar antes: para
saber cuál es el mínimo hay que mirar todos. Por eso es Θ y no O: mejor y
peor caso coinciden.

---

## 4. Difícil

### D-e01 · QuickSort

```java
int p = particionarLomuto(a, desde, hasta);   // c*n: recorre el rango
quicksort(a, desde, p - 1);                   // T(izquierda)
quicksort(a, p + 1, hasta);                   // T(derecha)
```

El costo depende de **dónde cae el pivote**. Hay que analizar dos casos.

**Caso promedio (pivote cerca del medio):**

```
T(n) = 2*T(n/2) + c*n
```

Es la recurrencia de MergeSort. Maestro: `a = 2, b = 2, f = Θ(n)`, exponente
crítico 1, `f = Θ(n^1)`, **caso 2**, **Θ(n log n)**. (La recurrencia exacta
del promedio es `T(n) = (2/n) * sum T(i) + cn`, más fea; su solución también
es Θ(n log n). La aproximación con mitades da el mismo orden.)

**Peor caso (pivote siempre en un extremo, por ejemplo arreglo ya ordenado
con pivote al final):**

```
T(n) = T(n-1) + T(0) + c*n = T(n-1) + c*n
```

**Teorema maestro: no aplica** (resta). Expansión:

```
T(n) = T(n-1) + cn
     = T(n-2) + c(n-1) + cn
     = T(n-3) + c(n-2) + c(n-1) + cn
     = ...
     = T(0) + c*(1 + 2 + ... + n)
     = c + c*n(n+1)/2                    =>  Theta(n^2)
```

Un arreglo ya ordenado es el peor caso de esta implementación. Por eso las
versiones reales eligen el pivote al azar o "mediana de tres".

### D-e02 · QuickSelect

Igual que QuickSort pero **recurre sobre un solo lado**, el que contiene la
posición buscada.

**Caso promedio (el pivote parte al medio):**

```
T(n) = T(n/2) + c*n
```

**Expansión:**

```
T(n) = T(n/2) + cn
     = T(n/4) + c(n/2) + cn
     = T(n/8) + c(n/4) + c(n/2) + cn
     = ...
     = T(n/2^k) + cn*(1 + 1/2 + 1/4 + ... + 1/2^(k-1))

La serie 1 + 1/2 + 1/4 + ... tiende a 2 y nunca lo supera.

T(n) <= T(1) + 2cn   =>  Theta(n)
```

**Teorema maestro:**

```
Paso 1.  a = 1     b = 2     f(n) = cn = Theta(n^1)
Paso 2.  e = log2 1 = 0      =>  n^e = n^0 = 1
Paso 3.  f(n) = Theta(n^1)  vs  n^0:  f crece POLINOMICAMENTE MAS
         (n^1 = Omega(n^(0 + eps)) con eps = 1)
         Condicion de regularidad:  a*f(n/b) = 1*c*(n/2) = cn/2 <= (1/2)*cn  ✓  (c' = 1/2 < 1)
         =>  CASO 3
Resultado: T(n) = Theta(f(n)) = Theta(n)
```

Es el único ejercicio de la guía que cae en el **caso 3**, y por eso es el
único donde hay que verificar la condición de regularidad: que el trabajo
de afuera en el nivel siguiente sea una fracción fija del trabajo actual.
Acá es la mitad, así que se cumple.

**Peor caso:** `T(n) = T(n-1) + cn`, igual que QuickSort: **Θ(n²)** por
expansión, maestro no aplica.

### D-e03 · MergeSortIterativo

```java
for (int ancho = 1; ancho < n; ancho *= 2)        // pasadas
    for (int desde = 0; desde < n; desde += 2*ancho)
        fusionar(...)                                // en total, n elementos por pasada
```

No hay recursión, así que no hay recurrencia: se cuenta directamente.

```
Pasadas: ancho = 1, 2, 4, ..., < n   =>  ceil(log2 n) pasadas
Cada pasada: fusiona bloques que juntos cubren los n elementos  =>  c*n

Total = c*n * log2 n   =>  Theta(n log n)
```

Es **el mismo árbol** que el MergeSort recursivo, recorrido de abajo hacia
arriba: la pasada con `ancho = 1` es el nivel de las hojas, la de `ancho =
n/2` es la raíz. Por eso da lo mismo: `log n` niveles por `n` de trabajo cada
uno.

### D-e04 · ContarInversiones

```java
inv += ordenarYContar(a, aux, desde, medio);       // T(n/2)
inv += ordenarYContar(a, aux, medio + 1, hasta);   // T(n/2)
inv += fusionarYContar(a, aux, desde, medio, hasta);   // c*n
```

```
T(n) = 2*T(n/2) + c*n
```

Es MergeSort con un contador adentro de la fusión. Sumar `medio - i + 1` es
O(1), así que la fusión sigue siendo `cn`. Expansión y maestro idénticos a
M-e05: `a = 2, b = 2, f = Θ(n)`, exponente crítico 1, **caso 2**,
**Θ(n log n)**.

La alternativa ingenua (dos bucles anidados contando pares) es F-e02:
Θ(n²). Esta es la primera vez en la guía que divide y vencerás gana contra
el doble bucle en un problema de conteo.

### D-e05 · Kadane

```java
for (int i = 1; i < n; i++) {
    mejorAqui = Math.max(a[i], mejorAqui + a[i]);
    mejorGlobal = Math.max(mejorGlobal, mejorAqui);
}
```

```
n-1 vueltas * 2 max   =>  Theta(n)
```

Lo interesante es lo que **no** hace: la solución de fuerza bruta prueba
cada par `(i, j)` y suma el subarreglo, que es Θ(n³); con sumas
acumuladas, Θ(n²). Kadane lo baja a Θ(n) con una sola pasada.

### D-e06 · BusquedaEnRotado

```java
if (a[desde] <= a[medio]) {                  // la izquierda esta ordenada
    if (a[desde] <= x && x < a[medio]) hasta = medio - 1;
    else desde = medio + 1;
} else { ... simetrico ... }
```

Cada vuelta hace **una cantidad constante** de comparaciones (a lo sumo 4)
y se queda con **una mitad**. Es búsqueda binaria con más `if`:

```
T(n) = T(n/2) + c
```

`a = 1, b = 2, f = Θ(1)`, exponente crítico 0, **caso 2**, **Θ(log n)**.
Que haya más comparaciones por vuelta cambia la constante `c`, no el orden.

### D-e07 · PotenciaModular

```java
while (e > 0) {
    if ((e & 1L) == 1L) resultado = (resultado * b) % mod;
    b = (b * b) % mod;
    e >>= 1;
}
```

Es M-e02 con un `% mod` en cada multiplicación (O(1) más). El exponente se
divide por 2 en cada vuelta:

```
T(e) = T(e/2) + c   =>  caso 2  =>  Theta(log exp)
```

Dicho de otra forma: una vuelta por cada **bit** del exponente, y un número
`e` tiene `floor(log2 e) + 1` bits.

### D-e08 · Hanoi

```java
return 2 * movimientosRecursivo(n - 1) + 1;     // 2*T(n-1) + 1
```

```
T(n) = 2*T(n-1) + 1
T(0) = 0
```

**Teorema maestro: NO APLICA.** El problema se resta (`n-1`), no se divide.
Y es un caso instructivo: hay **dos** llamadas, así que uno tiende a pensar
en `a = 2`; pero sin un `b` no hay teorema.

**Expansión:**

```
T(n) = 2T(n-1) + 1
     = 2[2T(n-2) + 1] + 1          = 4T(n-2) + 2 + 1       = 4T(n-2) + 3
     = 4[2T(n-3) + 1] + 3          = 8T(n-3) + 4 + 3       = 8T(n-3) + 7
     = ...
     = 2^k * T(n-k) + (2^k - 1)         <- 1 + 2 + 4 + ... + 2^(k-1) = 2^k - 1

Caso base:  n - k = 0  =>  k = n

T(n) = 2^n * T(0) + 2^n - 1 = 0 + 2^n - 1   =>  Theta(2^n)
```

La expansión da la **fórmula cerrada exacta**: `2^n - 1` movimientos. Es lo
que usa `movimientos(n)` para responder en O(1). Verificá con el `main`:
n = 5 da 31 = 2^5 - 1.

Hay que distinguir dos cosas: **calcular cuántos movimientos hay** es O(1)
con la fórmula; **hacerlos** (o listarlos) es Θ(2^n), porque son 2^n - 1
movimientos y no hay forma de hacerlos en menos.

### D-e09 · FibonacciRapido

```java
for (int bit = highestOneBit(n); bit != 0; bit >>= 1) {   // un paso por bit de n
    ... F(2k), F(2k+1) a partir de F(k), F(k+1) ...        // O(1)
}
```

Iterativo: `floor(log2 n) + 1` vueltas de trabajo constante, **Θ(log n)**.

Escrito como recurrencia (la versión recursiva de *fast doubling* calcula
`F(n)` a partir de `F(n/2)` y `F(n/2 + 1)`, que salen de la misma llamada):

```
T(n) = T(n/2) + c   =>  a = 1, b = 2, f = Theta(1), e = 0, caso 2  =>  Theta(log n)
```

**Para contrastar**, el Fibonacci recursivo ingenuo:

```
T(n) = T(n-1) + T(n-2) + c
```

Teorema maestro: **no aplica** (dos llamadas de distinto tamaño, y por
resta). Por expansión, acotando `T(n-2) <= T(n-1)`:

```
T(n) <= 2T(n-1) + c   =>  como Hanoi  =>  O(2^n)
```

La cota ajustada es Θ(φ^n) con φ ≈ 1,618, el número de oro. Los tres
Fibonacci de la materia: ingenuo Θ(1,618^n), iterativo Θ(n), fast doubling
Θ(log n).

### D-e10 · MergeKArreglos

```java
int[] izq = fusionarRango(arreglos, desde, medio);     // T(k/2)
int[] der = fusionarRango(arreglos, medio + 1, hasta); // T(k/2)
return fusionarDos(izq, der);                          // |izq| + |der|
```

Acá hay que tener cuidado con **qué es el tamaño del problema**. Hay dos
números: `k`, la cantidad de arreglos, y `N`, la cantidad total de
elementos. La recursión divide sobre `k`; el trabajo de fusionar depende de
`N`.

Si los `k` arreglos tienen tamaño parecido `m` (así `N = k*m`), fusionar dos
grupos de `k/2` arreglos cuesta `(k/2)*m + (k/2)*m = k*m`:

```
T(k) = 2*T(k/2) + m*k
```

**Expansión:**

```
T(k) = 2T(k/2) + mk
     = 4T(k/4) + mk + mk          = 4T(k/4) + 2mk
     = 8T(k/8) + 3mk
     = ...
     = 2^j * T(k/2^j) + j*m*k

Caso base: k/2^j = 1  =>  j = log2 k

T(k) = k*T(1) + m*k*log2 k = k*m + N*log2 k   =>  Theta(N log k)
```

(`T(1)` es copiar un arreglo de `m` elementos: `m`. Entonces `k*T(1) = km = N`.)

**Teorema maestro** sobre `k`, tratando a `m` como constante:

```
Paso 1.  a = 2     b = 2     f(k) = m*k = Theta(k^1)
Paso 2.  e = log2 2 = 1      =>  k^e = k
Paso 3.  f(k) = Theta(k)  y  k^e = k   ->  IGUAL   =>  CASO 2
Resultado: T(k) = Theta(k * log k) * m = Theta(m*k*log k) = Theta(N log k)
```

El argumento sin fórmulas: el árbol tiene `log2 k` niveles y en **cada
nivel** se fusionan, en total, los `N` elementos una vez. `N` por `log k`.
Fusionar de a uno secuencialmente (el primero con el segundo, el resultado
con el tercero, ...) cuesta `N * k`: por eso se hace por pares.

---

## 5. Los errores más comunes, para no repetirlos

1. **Aplicar el teorema maestro a `T(n-1)`.** No hay `b`. Expansión.
2. **Confundir "más grande" con "polinómicamente más grande".** `n log n`
   contra `n` no es caso 3. Hay que ver un `n^eps` de diferencia.
3. **Olvidar la condición de regularidad en el caso 3.** Sin ella el
   teorema no garantiza nada. En D-e02 se verifica explícitamente.
4. **Contar el bucle interno como `n` vueltas siempre.** En los dobles
   bucles triangulares hace `n-1-i`; la suma da `n²/2`, que sigue siendo
   Θ(n²), pero hay que mostrar la suma.
5. **Decir Θ cuando es O.** Si mejor y peor caso difieren (F-e04, F-e07,
   M-e03, M-e04), el peor caso es O, no Θ. Si coinciden (M-e10), es Θ.
6. **Confundir el tamaño de la entrada.** En las potencias el tamaño es el
   exponente; en MergeKArreglos hay dos tamaños, `k` y `N`.
7. **Olvidar el espacio.** La recursión usa pila: SumaRecursiva es Θ(n) en
   tiempo **y** en memoria; su versión iterativa es Θ(1) en memoria.
