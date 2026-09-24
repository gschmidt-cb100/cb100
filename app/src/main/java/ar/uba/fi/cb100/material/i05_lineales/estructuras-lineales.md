# Estructuras lineales — el capítulo ampliado

Este texto acompaña el código de `material/i05_lineales`. Cada ejemplo tiene
su programa al lado, compilado y corrido: nada de lo que sigue está "en el
aire". Los programas son `DemoRecorridosArrayList`, `DemoOrdenarYBuscar`,
`ListaIterable`, `DemoRecorridosLinkedList` y `DemoPilaYCola`.

El orden del capítulo es el orden en que conviene aprenderlo: primero el
**arreglo** que ya conocen; después el **TDA propio** que lo hace crecer;
después la **clase de la API** que hace lo mismo, mirando su código de
verdad; y recién entonces la **elección** de cuál usar y cómo recorrerla,
ordenarla y buscar en ella. Lo mismo, más corto, para lista, conjunto, pila y
cola. Al final, un cuadro con lo bueno y lo malo de cada una.

Streams no aparece: es de la Unidad 12.

---

## 1. Vector

### 1.1 De dónde venimos: el arreglo

```java
int[] notas = new int[5];      // 5 celdas, contiguas, y NUNCA más de 5
```

Un arreglo es memoria **contigua** y de **tamaño fijo**. Contigua es lo que
lo hace rápido: para llegar a `notas[3]` la máquina calcula
`dirección_base + 3 × tamaño_de_int` y va directo, sin recorrer nada. Eso es
el **acceso directo**, O(1), y es la propiedad que todo lo que sigue quiere
conservar.

Fijo es el problema: si hay que guardar un sexto elemento, no hay lugar. Y
no hay forma de "agrandar" un arreglo: la memoria que sigue puede estar
ocupada por otra cosa.

### 1.2 El TDA Vector propio: un arreglo que crece

La idea es simple: adentro hay un arreglo, y cuando se llena se **crea otro
más grande y se copia**. Quien usa el vector nunca se entera.

Esta es la parte privada completa, la que importa entender:

```java
public class Vector<T> {

    private T[] valores;      // el arreglo interno: capacidad = valores.length
    private int tamanio;      // cuántas celdas están usadas: 0 .. valores.length
```

Dos números que **no son lo mismo**:

```
   valores:  [ a ][ b ][ c ][   ][   ][   ][   ][   ]
              0    1    2    3    4    5    6    7
                             ^
             tamanio = 3     |    capacidad = valores.length = 8
                             +--- desde acá, celdas libres (null)
```

- **tamaño** es cuántos elementos hay. Es lo que ve el usuario.
- **capacidad** es cuántas celdas tiene el arreglo. Es un detalle interno.

Las operaciones, y lo que cuesta cada una:

```java
    public void agregar(T valor) {                 // O(1) amortizado
        asegurarCapacidad();
        valores[tamanio] = valor;
        tamanio++;
    }

    public T obtener(int i) {                      // O(1): acceso directo
        verificar(i);
        return valores[i];
    }

    public void insertar(int i, T valor) {         // O(n): corre lo que sigue
        asegurarCapacidad();
        for (int k = tamanio; k > i; k--) {
            valores[k] = valores[k - 1];           // uno a la derecha
        }
        valores[i] = valor;
        tamanio++;
    }

    public T eliminar(int i) {                     // O(n): corre lo que sigue
        T eliminado = valores[i];
        for (int k = i; k < tamanio - 1; k++) {
            valores[k] = valores[k + 1];           // uno a la izquierda
        }
        tamanio--;
        valores[tamanio] = null;                   // no retener basura
        return eliminado;
    }

    private void asegurarCapacidad() {
        if (tamanio == valores.length) {
            valores = Arrays.copyOf(valores, valores.length * 2);   // el DOBLE
        }
    }
```

El crecimiento, paso a paso, con capacidad inicial 4:

```
agregar a, b, c, d:     [ a ][ b ][ c ][ d ]                  tamaño 4, capacidad 4: LLENO

agregar e:              1. se crea un arreglo de 8
                        2. se copian los 4               <- este es el costo: O(n)
                        3. se agrega la e
                        [ a ][ b ][ c ][ d ][ e ][   ][   ][   ]   tamaño 5, capacidad 8

agregar f, g, h:        [ a ][ b ][ c ][ d ][ e ][ f ][ g ][ h ]   sin copiar nada: O(1) cada uno

agregar i:              copia de 8 -> arreglo de 16
```

**Por qué duplicar y no agrandar de a uno.** Si agrandáramos de a una celda,
*cada* `agregar` copiaría todo: O(n) siempre, y n agregados costarían
O(n²). Duplicando, las copias suman `4 + 8 + 16 + ... + n ≈ 2n` a lo largo
de n agregados: O(n) en total, o sea **O(1) por agregado en promedio**. A
eso se le dice *amortizado*: un agregado puntual puede costar O(n), pero
repartido entre todos, cada uno sale O(1).

**El detalle del `T[]`.** Java no permite `new T[n]`: los genéricos se
borran al compilar y en ejecución no sabría qué arreglo crear. Por eso el
constructor hace `(T[]) new Object[CAPACIDAD_INICIAL]` con
`@SuppressWarnings("unchecked")`. Es seguro porque el arreglo nunca sale de
la clase.

> Este código existe **sólo acá, en la teoría**, para entender qué hace la API
> por dentro. No está en el material ni en la guía como archivo: en los
> ejercicios, los TPs y los parciales se usa `ArrayList`, y punto.

Si se lo corre, el crecimiento se ve así:

```
vacío:        []  (tamaño 0, capacidad 4)
4 agregados:  [a, b, c, d]  (tamaño 4, capacidad 4)
el quinto:    [a, b, c, d, e]  (tamaño 5, capacidad 8)      <- se duplicó
insertar(1):  [a, X, b, c, d, e]  (tamaño 6, capacidad 8)
eliminar(0):  [X, b, c, d, e]  (tamaño 5, capacidad 8)
```

### 1.3 Lo mismo, en la API: `ArrayList` y `Vector`

Java trae dos clases que hacen exactamente esto. Miremos sus declaraciones
**reales**, del código fuente del JDK, porque cada palabra dice algo.

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    transient Object[] elementData;     // el arreglo interno
    private int size;                   // el tamaño
```

```java
public class Vector<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    protected Object[] elementData;     // el arreglo interno
    protected int elementCount;         // el tamaño
    protected int capacityIncrement;    // cuánto crece (0 = duplica)
```

Es **nuestro `Vector<T>`** con otros nombres: `elementData` es `valores`,
`size` es `tamanio`. Lo que agregan las palabras de la declaración:

| Pieza | Qué dice |
|---|---|
| `<E>` | Genérica: `ArrayList<String>`, `ArrayList<Alumno>`. Como nuestro `<T>`. |
| `extends AbstractList<E>` | Hereda las operaciones que se pueden escribir en función de `get` y `size` (`indexOf`, `contains`, `iterator`...). Sólo implementa lo que depende del arreglo. |
| `implements List<E>` | **Cumple el contrato de lista**: acceso por posición, admite duplicados, conserva el orden. Sección 1.8. |
| `RandomAccess` | Interfaz **sin métodos**, una marca: "acá `get(i)` es O(1)". Los algoritmos de `Collections` la miran para elegir cómo recorrer. |
| `Cloneable`, `Serializable` | Se puede copiar con `clone()` y guardar en un archivo tal cual. |

Y una diferencia que no se ve en la firma pero sí en el código: `ArrayList`
crece un 50 % (`nueva = vieja + vieja/2`); `Vector` duplica. Y **todos los
métodos de `Vector` son `synchronized`**: cada `get` toma un candado, por
si dos hilos lo usan a la vez. Nosotros no tenemos hilos, así que es costo
sin beneficio.

### 1.4 Comparación y elección

| | `Vector<T>` propio | `java.util.Vector` | `java.util.ArrayList` |
|---|---|---|---|
| Existe desde | esta clase | Java 1.0 (1996) | Java 1.2 (1998) |
| Crece | ×2 | ×2 | ×1,5 |
| Sincronizado | no | **sí, siempre** | no |
| Implementa `List` | no | sí | sí |
| Recorridos, `sort`, `binarySearch` | los que escribimos | todos los de `List` | todos los de `List` |
| Probado por | nosotros | 30 años de usuarios | 30 años de usuarios |

**La elección es `ArrayList`.** Hace lo mismo que el propio, mejor probado;
hace lo mismo que `Vector` sin pagar candados que no usamos. `Vector` quedó
en la API por compatibilidad, y su propio javadoc lo dice: si no hace falta
sincronización, usar `ArrayList`.

De acá en adelante, cuando el capítulo dice "vector", en el código dice
`ArrayList`.

### 1.5 Cómo se recorre un `ArrayList`

Programa: `DemoRecorridosArrayList`. Lista de trabajo: `[10, 20, 30, 40, 50]`.

**1. `for` con índice.** Cuando hace falta **la posición**. Es el único que
la da.

```java
for (int i = 0; i < lista.size(); i++) {
    System.out.print("[" + i + "]=" + lista.get(i) + " ");
}
// [0]=10 [1]=20 [2]=30 [3]=40 [4]=50
```

**2. `for-each`.** Cuando sólo importan **los valores**. El más legible.
No deja modificar la lista mientras se recorre.

```java
for (Integer valor : lista) {
    System.out.print(valor + " ");
}
// 10 20 30 40 50
```

**3. `Iterator`.** Lo que el `for-each` usa por dentro. Un **cursor** que
sabe dos cosas: si hay siguiente, y cuál es. Vale la pena verlo explícito
una vez, porque es lo que permite **eliminar mientras se recorre**.

```java
Iterator<Integer> it = lista.iterator();
while (it.hasNext()) {
    Integer valor = it.next();
    System.out.print(valor + " ");
}
```

```
   cursor antes de empezar:     | 10   20   30   40   50
   despues de next() -> 10:       10 | 20   30   40   50
   despues de next() -> 20:       10   20 | 30   40   50
   ...
   hasNext() == false:            10   20   30   40   50 |
```

**4. Inverso con índice.** De atrás para adelante.

```java
for (int i = lista.size() - 1; i >= 0; i--) { ... }
// 50 40 30 20 10
```

**5. `ListIterator`.** Un cursor que va **para los dos lados**, y que además
puede **reemplazar** (`set`) y **agregar** (`add`) donde está parado.

```java
ListIterator<Integer> li = lista.listIterator(lista.size());   // parado al final
while (li.hasPrevious()) {
    System.out.print(li.previous() + " ");
}
// 50 40 30 20 10
```

**6. `while` con índice.** Cuando la condición de corte no es "hasta el
final".

```java
int i = 0;
while (i < lista.size() && lista.get(i) <= 30) {
    System.out.print(lista.get(i) + " ");
    i++;
}
// 10 20 30
```

#### Modificar mientras se recorre

Esto es lo que más error produce. Las reglas:

**Eliminar dentro de un `for-each` rompe.** La lista detecta que la
modificaron por afuera del cursor y lanza `ConcurrentModificationException`
en la vuelta siguiente.

```java
for (Integer valor : lista) {
    if (valor == 30) {
        lista.remove(valor);      // <- explota en la próxima vuelta
    }
}
```

**Con el `Iterator` sí se puede:** `it.remove()` borra el último elemento que
devolvió `next()`, y el cursor sigue bien.

```java
Iterator<Integer> it = lista.iterator();
while (it.hasNext()) {
    if (it.next() == 30) {
        it.remove();
    }
}
// [10, 20, 40, 50]
```

**Con el `ListIterator`, también reemplazar y agregar:**

```java
ListIterator<Integer> editor = lista.listIterator();
while (editor.hasNext()) {
    int valor = editor.next();
    if (valor == 20) editor.set(25);     // reemplaza el 20
    if (valor == 40) editor.add(45);     // inserta después del 40
}
// [10, 25, 40, 45, 50]
```

**Con índice, de atrás para adelante, también:** al ir hacia atrás, borrar
una posición no desplaza las que faltan visitar.

```java
for (int k = lista.size() - 1; k >= 0; k--) {
    if (lista.get(k) % 2 == 1) {
        lista.remove(k);
    }
}
// [10, 40, 50]
```

#### La trampa de `remove` con `Integer`

`remove` está **sobrecargado**: `remove(int)` borra por **posición**,
`remove(Object)` borra por **valor**. Con una `List<Integer>`, el `1` es un
`int`, y gana la versión por posición:

```java
List<Integer> trampa = new ArrayList<>(List.of(5, 10, 1));
trampa.remove(1);                    // posición 1 -> borra el 10:   [5, 1]
trampa.remove(Integer.valueOf(1));   // valor 1   -> borra el 1:    [5]
```

### 1.6 Cómo se ordena

Programa: `DemoOrdenarYBuscar`.

`ArrayList` no ordena solo: se le pide. Java usa **TimSort**, un MergeSort
mejorado: O(n log n), y **estable** (dos elementos iguales conservan el
orden que tenían).

```java
List<Integer> numeros = new ArrayList<>(List.of(42, 7, 19, 3, 25));

Collections.sort(numeros);                 // orden natural: el compareTo del tipo
// [3, 7, 19, 25, 42]

numeros.sort(null);                        // lo mismo, como método de la lista
numeros.sort(Comparator.reverseOrder());   // [42, 25, 19, 7, 3]
```

Para ordenar objetos propios hace falta decir **por qué atributo**. Eso es un
`Comparator`: un objeto que sabe decidir quién va antes.

```java
record Alumno(String nombre, int padron, double promedio) { }

alumnos.sort(Comparator.comparing(Alumno::nombre));
// [Ana 9.0, Bruno 7.5, Carla 7.5, Diego 8.2]

alumnos.sort(Comparator.comparingDouble(Alumno::promedio).reversed());
// [Ana 9.0, Diego 8.2, Bruno 7.5, Carla 7.5]
```

Fijate en el último: Bruno y Carla tienen el mismo promedio, y quedaron
**en el orden en que estaban** antes de ordenar. Eso es la estabilidad. Si
se quiere desempatar a propósito, se encadena un segundo criterio:

```java
alumnos.sort(Comparator.comparingDouble(Alumno::promedio).reversed()
                       .thenComparing(Alumno::nombre));
```

```
   antes:      Bruno 7.5   Ana 9.0   Carla 7.5   Diego 8.2
                  |           |         |           |
   por promedio   |    +------+         |     +-----+
   descendente:   |    |                |     |
               Ana 9.0   Diego 8.2   Bruno 7.5   Carla 7.5
                                        ^-- empate: conservan su orden relativo
```

### 1.7 Cómo se busca

Dos familias, con costos muy distintos.

**Búsqueda lineal: `contains`, `indexOf`.** Recorren desde el principio
hasta encontrar. O(n). No necesitan que la lista esté ordenada.

```java
datos.contains(19);      // true
datos.indexOf(25);       // 3
datos.indexOf(99);       // -1: no está
```

**Búsqueda binaria: `Collections.binarySearch`.** O(log n). Pero **sólo
sobre una lista ordenada**; si no lo está, devuelve cualquier cosa sin
avisar.

```java
List<Integer> datos = new ArrayList<>(List.of(3, 7, 19, 25, 42));   // ordenada

Collections.binarySearch(datos, 25);   //  3: está, en la posición 3
Collections.binarySearch(datos, 20);   // -4: no está
```

El negativo no es sólo "no está": es `-(posición donde iría) - 1`. Con `-4`,
iría en la posición 3. Sirve para insertar manteniendo el orden.

```
   buscar 25 en [3, 7, 19, 25, 42]:

   paso 1:  [ 3 ][ 7 ][ 19 ][ 25 ][ 42 ]     medio = 19 < 25  -> derecha
              0    1     2     3     4
   paso 2:                  [ 25 ][ 42 ]     medio = 25 == 25 -> posición 3
                               3     4
   dos comparaciones. La lineal habría hecho cuatro.
```

Con objetos, hay que buscar con **el mismo `Comparator`** con que se ordenó:

```java
alumnos.sort(Comparator.comparingInt(Alumno::padron));
int pos = Collections.binarySearch(alumnos, new Alumno("?", 41234, 0),
                                   Comparator.comparingInt(Alumno::padron));
// posición 2 -> Ana
```

Y para el máximo y el mínimo no hace falta ordenar: `Collections.max` y
`Collections.min` recorren una vez, O(n).

| Operación | Costo | Necesita orden |
|---|---|---|
| `contains`, `indexOf` | O(n) | no |
| `Collections.max`, `min` | O(n) | no |
| `Collections.binarySearch` | O(log n) | **sí** |
| ordenar primero y después buscar k veces | O(n log n) + k·O(log n) | — |

La última fila es la cuenta que hay que hacer: ordenar cuesta, pero si
después se busca muchas veces, se amortiza.

### 1.8 Por qué `ArrayList` implementa `List`

`List` es una **interfaz**: un contrato que dice qué se puede hacer con una
lista, sin decir cómo está guardada.

```java
public interface List<E> extends SequencedCollection<E> {
    E get(int index);
    E set(int index, E element);
    void add(int index, E element);
    E remove(int index);
    int indexOf(Object o);
    ListIterator<E> listIterator();
    ...
}
```

Lo que promete `List` y no promete `Collection`: **posiciones** (hay un
primero, un segundo...), **duplicados** (dos veces el mismo valor son dos
elementos), y **orden de inserción** (los elementos quedan donde se los
puso).

`ArrayList` la implementa con un arreglo. `LinkedList` la implementa con
nodos. Las dos cumplen el mismo contrato, y por eso esto funciona:

```java
List<String> nombres = new ArrayList<>();     // hoy
List<String> nombres = new LinkedList<>();    // mañana, si hace falta
```

Todo el código que usa `nombres` **no cambia**, porque sólo depende de lo
que promete `List`. Es la misma idea que `Tablero` y `TableroMatriz` en el
tateti, o `Lista` y `ListaSimplementeEnlazada` en este material: **se programa contra
la interfaz, no contra la implementación**. Declarar `ArrayList<String>
nombres` en vez de `List<String> nombres` funciona, pero ata el resto del
programa a un detalle que no le importa.

### 1.9 Trampas que se repiten

- **Eliminar en un `for-each`.** Sección 1.5. Usar `Iterator.remove()` o
  recorrer al revés.
- **`remove(1)` con `List<Integer>`.** Borra la posición, no el valor.
- **`binarySearch` sobre lista desordenada.** No avisa, devuelve basura.
- **`List.of(...)` es inmutable.** `List.of(1, 2).add(3)` lanza
  `UnsupportedOperationException`. Para una lista modificable a partir de
  valores: `new ArrayList<>(List.of(1, 2))`.
- **Capacidad inicial.** Si se sabe que van a entrar 10.000 elementos,
  `new ArrayList<>(10000)` evita 14 redimensiones. No cambia el orden de
  complejidad; cambia la constante.

---

## 2. Conjunto

### 2.1 El TDA

Un conjunto es una colección **sin duplicados** y **sin posición**: sólo
importa si un elemento está o no. La interfaz, del material:

```java
public interface Conjunto<T> {
    boolean agregar(T x);            // false si ya estaba
    boolean contiene(T x);
    boolean eliminar(T x);           // false si no estaba
    void agregarTodos(Conjunto<T> otro);
    Conjunto<T> union(Conjunto<T> otro);
    Conjunto<T> interseccion(Conjunto<T> otro);
    Conjunto<T> diferencia(Conjunto<T> otro);
    int tamanio();
    Object[] aArreglo();
}
```

Fijate lo que **no** tiene: `obtener(i)`, `insertar(i, x)`. No hay
posiciones. Y lo que devuelve `agregar`: un `boolean`, porque agregar algo
que ya está no es un error, es un "no hice nada".

### 2.2 La implementación sobre lista: la parte privada

```java
public class ConjuntoLista<T> implements Conjunto<T> {

    private final ListaSimplementeEnlazada<T> elementos = new ListaSimplementeEnlazada<>();

    @Override
    public boolean agregar(T x) {
        if (elementos.contiene(x)) {       // <- ESTE recorrido es el costo: O(n)
            return false;
        }
        elementos.agregar(x);
        return true;
    }
```

Toda la parte privada es **una lista**. El conjunto no guarda nada distinto:
lo que cambia es la **regla** de `agregar`, que antes de agregar recorre.
Por eso `agregar` y `contiene` son O(n) acá, y por eso en la unidad de
hashing vamos a ver cómo bajarlos a O(1) promedio.

`union`, `interseccion` y `diferencia` devuelven un conjunto **nuevo**; no
modifican los operandos. Cada una es un recorrido con `contiene` adentro:
O(n × m).

### 2.3 En la API: `Set` y sus tres implementaciones

```java
public interface Set<E> extends Collection<E> { ... }

public class HashSet<E> extends AbstractSet<E>
        implements Set<E>, Cloneable, java.io.Serializable {
    private transient HashMap<E, Object> map;      // una tabla hash por dentro
```

| Clase | Orden al recorrer | `add` / `contains` | Cuándo |
|---|---|---|---|
| `HashSet` | ninguno garantizado | O(1) promedio | por defecto |
| `LinkedHashSet` | el de inserción | O(1) promedio | cuando el orden de llegada importa |
| `TreeSet` | ordenado (natural o `Comparator`) | O(log n) | cuando hace falta recorrer en orden |

Se recorren con `for-each` o `Iterator`, **nunca con índice**: no lo hay.
Las operaciones de teoría de conjuntos son `addAll` (unión), `retainAll`
(intersección) y `removeAll` (diferencia), y modifican el conjunto sobre el
que se llaman.

---

## 3. Lista enlazada

### 3.1 La parte privada: el nodo

```java
public class ListaSimplementeEnlazada<T> implements Lista<T> {

    private static final class Nodo<T> {     // nadie de afuera lo ve
        T valor;
        Nodo<T> siguiente;                   // la flecha; null en el último
    }

    private Nodo<T> primero;                 // null si está vacía
    private Nodo<T> ultimo;                  // para agregar al final en O(1)
    private int tamanio;
```

```
   primero                                              ultimo
      |                                                    |
      v                                                    v
   +-----+-----+     +-----+-----+     +-----+-----+     +-----+------+
   |  a  |  o--|---->|  b  |  o--|---->|  c  |  o--|---->|  d  | null |
   +-----+-----+     +-----+-----+     +-----+-----+     +-----+------+
```

Cada elemento vive en su propio bloque del heap, donde haya lugar. No hay
memoria contigua: por eso **no hay acceso directo**. Para llegar al tercero
hay que pasar por el primero y el segundo.

### 3.2 Las tres variantes

| | Simple | Doble | Circular |
|---|---|---|---|
| Cada nodo conoce | al siguiente | al siguiente **y al anterior** | al siguiente; el último apunta al primero |
| Recorrer hacia atrás | no se puede | sí | no (simple) / sí (circular doble) |
| Eliminar un nodo dado | hay que buscar al anterior: O(n) | O(1) | como la simple |
| Memoria por nodo | dato + 1 flecha | dato + 2 flechas | dato + 1 flecha |
| Cuidado especial | — | mantener las dos flechas coherentes | no hay `null` al final: hay que **contar** para no dar vueltas infinitas |
| Sirve para | lo básico | recorridos en los dos sentidos, borrado en el lugar | turnos, round-robin, Josephus |

Las tres están en el material: `ListaSimplementeEnlazada`,
`ListaDoblementeEnlazada`, `ListaCircular`, `ListaCircularDoble`.

### 3.3 Cómo se recorre una lista enlazada

Programa: `ListaIterable`. Es una lista simple que **implementa
`Iterable<T>`**, y muestra qué hay detrás del `for-each`.

**Siguiendo las flechas**, que es lo único que la lista sabe hacer. Así lo
hace por dentro:

```java
for (Nodo<T> n = primero; n != null; n = n.siguiente) {
    ... n.valor ...
}
```

**Con `Iterator`**, que es esa misma caminata empaquetada en un objeto:

```java
private final class Cursor implements Iterator<T> {
    private Nodo<T> proximo = primero;

    public boolean hasNext() { return proximo != null; }

    public T next() {
        T valor = proximo.valor;
        proximo = proximo.siguiente;     // avanzar UNA flecha: O(1)
        return valor;
    }
}

@Override
public Iterator<T> iterator() { return new Cursor(); }
```

Con eso, la lista propia se recorre con `for-each` igual que un
`ArrayList`:

```java
for (String x : lista) { ... }         // a b c
```

**Con índice: funciona, pero es O(n²).** Cada `obtener(i)` arranca desde el
primero y camina `i` nodos. Para n elementos son `0 + 1 + 2 + ... + (n-1)`
pasos.

```java
for (int i = 0; i < lista.tamanio(); i++) {
    lista.obtener(i);                  // O(i) cada uno -> O(n^2) en total
}
```

Esta es la razón de ser del iterador: **el cursor recuerda dónde está**, y
por eso el recorrido entero es O(n) y no O(n²). En un `ArrayList` el índice
es gratis; en una lista enlazada, no.

### 3.4 En la API: `LinkedList`

```java
public class LinkedList<E> extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

    transient int size = 0;
    transient Node<E> first;
    transient Node<E> last;
```

Es una lista **doblemente** enlazada. Y la declaración dice dos cosas
importantes: `implements List<E>`, así que se recorre y se ordena igual que
`ArrayList`; e `implements Deque<E>`, así que también sirve de pila y de
cola. `AbstractSequentialList` en vez de `AbstractList` es la forma de la API
de decir "acá el acceso es secuencial, no directo".

Programa: `DemoRecorridosLinkedList`.

```java
LinkedList<String> lista = new LinkedList<>(List.of("b", "c", "d"));
lista.addFirst("a");                   // O(1): ArrayList no tiene esto
lista.addLast("e");                    // O(1)
// [a, b, c, d, e]

ListIterator<String> li = lista.listIterator(lista.size());
while (li.hasPrevious()) { ... li.previous() ... }     // e d c b a: hacia atrás

ListIterator<String> editor = lista.listIterator();
while (editor.hasNext()) {
    if (editor.next().equals("b")) {
        editor.add("b2");              // O(1) acá; en ArrayList correría el resto
    }
}
// [a, b, b2, d, e]
```

**Lo que no hay en la API:** una lista **circular**. Para round-robin se usa
un `ArrayDeque` sacando del frente y poniendo al fondo, o
`Collections.rotate`. Si hace falta una circular de verdad, es de las del
material.

### 3.5 Qué cuesta cada operación, según dónde

Esta tabla es la que hay que tener en la cabeza para elegir:

| Operación | `ArrayList` | Lista simple | Lista doble | `LinkedList` |
|---|---|---|---|---|
| Acceso por posición `get(i)` | **O(1)** | O(n) | O(n) | O(n) |
| Agregar al final | O(1) amort. | O(1) con `ultimo` | O(1) | **O(1)** |
| Agregar al principio | O(n) | **O(1)** | **O(1)** | **O(1)** |
| Insertar en el medio, **ya ubicado** | O(n): corre | **O(1)** | **O(1)** | **O(1)** |
| Insertar en el medio, desde cero | O(n) | O(n) ubicar + O(1) | O(n) + O(1) | O(n) + O(1) |
| Eliminar al final | O(1) | O(n): buscar al anterior | **O(1)** | **O(1)** |
| Eliminar al principio | O(n): corre | **O(1)** | **O(1)** | **O(1)** |
| Eliminar un nodo dado | O(n) | O(n) | **O(1)** | **O(1)** vía iterador |
| Buscar por valor | O(n) | O(n) | O(n) | O(n) |
| Búsqueda binaria | **O(log n)** | no tiene sentido | no | O(n): sin acceso directo |
| Recorrer entera con iterador | O(n) | O(n) | O(n) | O(n) |
| Recorrer entera con índice | O(n) | **O(n²)** | **O(n²)** | **O(n²)** |
| Recorrer hacia atrás | O(n) | no | O(n) | O(n) |
| Memoria por elemento | el dato | dato + 1 referencia + cabecera de nodo | dato + 2 referencias + cabecera | dato + 2 referencias + cabecera |
| Memoria sin usar | capacidad − tamaño | ninguna | ninguna | ninguna |

Dos lecturas de la tabla. La lista enlazada gana cuando se **agrega y saca
por los extremos** o se **inserta mientras se recorre**. El `ArrayList` gana
en todo lo demás, y sobre todo en lo que más se hace: acceder y recorrer.
Por eso en la práctica `ArrayList` es la elección por defecto, y
`LinkedList` la excepción justificada.

---

## 4. Pila

El material tiene la `Pila<T>` construida **sobre un `ArrayList`**: apilar
es agregar al final, desapilar es eliminar el último. Las dos O(1). Es el
ejemplo de construir una estructura sobre otra: la pila no guarda nada
propio, sólo **restringe** lo que se puede hacer con la lista a un extremo.

```java
public class Pila<T> {
    private final List<T> datos = new ArrayList<>();

    public void apilar(T x)   { datos.add(x); }
    public T desapilar()      { return datos.remove(datos.size() - 1); }
    public T tope()           { return datos.get(datos.size() - 1); }
```

En la API, la pila es un `Deque` con `push`, `pop` y `peek`. Programa:
`DemoPilaYCola`.

```java
Deque<String> pila = new ArrayDeque<>();
pila.push("a"); pila.push("b"); pila.push("c");    // tope = c
pila.peek();     // c, sin sacar
pila.pop();      // c
pila.pop();      // b   <- LIFO
```

**No se usa `java.util.Stack`.** Es de Java 1.0, hereda de `Vector` (así
que es sincronizada sin necesidad, y además deja hacer `get(i)`, que una
pila no debería permitir), y su propio javadoc dice que se use `Deque`.

---

## 5. Cola

### 5.1 El TDA: la parte privada

```java
public class Cola<T> {

    private static final class Nodo<T> {
        T valor;
        Nodo<T> siguiente;
    }

    private Nodo<T> frente;   // por acá SALEN
    private Nodo<T> fondo;    // por acá ENTRAN
    private int tamanio;
```

```
    frente                                    fondo
      |                                         |
      v                                         v
   +-----+-----+     +-----+-----+     +-----+------+
   |  a  |  o--|---->|  b  |  o--|---->|  c  | null |
   +-----+-----+     +-----+-----+     +-----+------+
   desencolar()                          encolar(x)
   saca la a                             engancha detrás de la c
```

Por qué nodos y no un vector: una cola saca **por el frente**. Sobre un
vector, sacar el primero obliga a correr todos los demás, O(n). Con nodos y
dos referencias, `encolar` engancha detrás del `fondo` y `desencolar` avanza
el `frente`: las dos O(1), y sin correr nada.

```java
    public void encolar(T x) {
        Nodo<T> n = new Nodo<>(x);
        if (fondo == null) {
            frente = fondo = n;            // estaba vacía
        } else {
            fondo.siguiente = n;
            fondo = n;
        }
        tamanio++;
    }

    public T desencolar() {
        T valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) {
            fondo = null;                  // quedó vacía: coherencia
        }
        tamanio--;
        return valor;
    }
```

El `if (frente == null) fondo = null` es el detalle que se olvida: si sale
el último, `fondo` seguiría apuntando a un nodo que ya no está en la cola, y
el próximo `encolar` engancharía detrás de un nodo fantasma.

### 5.2 En la API: `Queue` y `ArrayDeque`

```java
public interface Queue<E> extends Collection<E> {
    boolean offer(E e);      // encolar
    E poll();                // desencolar; null si está vacía
    E peek();                // el frente sin sacar; null si está vacía
    ...
}

public class ArrayDeque<E> extends AbstractCollection<E>
        implements Deque<E>, Cloneable, Serializable {
    transient Object[] elements;     // un arreglo CIRCULAR
    transient int head;
    transient int tail;
```

`ArrayDeque` resuelve el problema del vector de otra forma: el arreglo es
**circular**, con dos índices que dan vueltas. Sacar del frente es avanzar
`head`; nada se corre. Es la implementación recomendada para pila y para
cola.

```java
Queue<String> cola = new ArrayDeque<>();
cola.offer("a"); cola.offer("b"); cola.offer("c");   // frente = a
cola.peek();     // a, sin sacar
cola.poll();     // a
cola.poll();     // b   <- FIFO
```

`poll()` sobre una cola vacía devuelve `null`; `remove()` lanza excepción.
Elegir uno y ser consistente.

`Deque` tiene los dos extremos con nombre explícito: `addFirst`, `addLast`,
`pollFirst`, `pollLast`. `push`/`pop` y `offer`/`poll` son alias de esos.
Declarar `Deque<T> pila` o `Queue<T> cola` según el uso hace que el
compilador no deje mezclar.

Para la **cola con prioridad** (`PriorityQueue`) hay que esperar a la
unidad de heaps: por dentro no es lineal.

---

## 6. Cuadro resumen: lo bueno y lo malo de cada una

| Estructura | Fortalezas | Debilidades | La usás cuando |
|---|---|---|---|
| **Vector / `ArrayList`** | Acceso directo O(1). Recorrido rapidísimo (memoria contigua, cache). Agregar al final O(1) amortizado. Búsqueda binaria posible. La API más completa. | Insertar o eliminar en el medio o al principio corre todo: O(n). Capacidad sin usar. Redimensionar copia todo. | Casi siempre. Es la opción por defecto. |
| **Lista simple** | No usa memoria contigua: cada nodo donde haya lugar. No ocupa memoria sin datos. Agregar al principio y (con `ultimo`) al final O(1). Insertar ya ubicado O(1). | Sin acceso directo: `get(i)` O(n), recorrer con índice O(n²). No se recorre hacia atrás. Eliminar el último cuesta O(n). Una referencia extra por elemento. | Cuando se inserta y saca por el principio, o se inserta mientras se recorre, y nunca se accede por posición. |
| **Lista doble** | Todo lo de la simple, más: recorrido en los dos sentidos, eliminar un nodo dado O(1), llegar a una posición desde el extremo más cercano. | Dos referencias por elemento. Cada operación mantiene dos flechas: más lugar para errores. | Cuando hace falta ir y volver, o borrar en el lugar mientras se recorre. Es lo que es `LinkedList`. |
| **Lista circular** | Recorrido en ronda sin condición especial de "volver al principio". | No hay `null` que marque el fin: hay que contar. No está en la API. | Turnos, round-robin, Josephus. |
| **Pila** | Dos operaciones, las dos O(1). Imposible equivocarse de extremo. | Sólo se ve el tope. Para cualquier otra cosa, no sirve. | Deshacer, recursión a mano, backtracking, balanceo de paréntesis, evaluación de expresiones. |
| **Cola** | Dos operaciones, las dos O(1). Conserva el orden de llegada. | Sólo se ve el frente. | Turnos, BFS, procesamiento en orden de llegada, buffers. |
| **Conjunto sobre lista** | Sin duplicados garantizado. Operaciones de teoría de conjuntos. | `agregar` y `contiene` O(n): cada uno recorre. `union` e `interseccion` O(n·m). | Cuando lo único que importa es pertenencia y hay pocos elementos. Con muchos, `HashSet`. |

Y la versión de una línea, para el parcial:

> **Vector**: rápido para leer, lento para mover. **Lista**: rápido para
> mover, lento para leer. **Pila y cola**: sólo un extremo, y por eso nunca
> lentas. **Conjunto**: no importa dónde, importa si está.

---

## 7. Qué se usa en la práctica

| Necesito | Uso | No uso |
|---|---|---|
| Una secuencia de elementos | `List<T> x = new ArrayList<>()` | `Vector`, arreglos crudos salvo tamaño fijo conocido |
| Agregar y sacar por los extremos, insertar mientras recorro | `LinkedList<T>` | `ArrayList` con `add(0, x)` |
| Una pila | `Deque<T> p = new ArrayDeque<>()` con `push/pop/peek` | `Stack` |
| Una cola | `Queue<T> c = new ArrayDeque<>()` con `offer/poll/peek` | `LinkedList` como cola (anda, pero es más pesada) |
| Sin duplicados | `Set<T> s = new HashSet<>()` | una lista con `contains` antes de cada `add` |
| Sin duplicados y en orden | `TreeSet<T>` | ordenar una lista cada vez |

Y en el código: **la variable se declara con la interfaz** (`List`, `Deque`,
`Queue`, `Set`) y **el `new` con la implementación**. Así se puede cambiar
una por otra sin tocar nada más.
