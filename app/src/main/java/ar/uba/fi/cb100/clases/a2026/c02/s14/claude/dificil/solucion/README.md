# Ejemplo de parcial 26/11 (versión difícil) — Solución

Enunciado: [2026-2C-3-oportunidad-26-11-dificil.pdf](../2026-2C-3-oportunidad-26-11-dificil.pdf)

| Punto | Tema | Dónde está |
|---|---|---|
| 1 | Complejidad: a) `Θ(n log² n)`, b) `Θ(n log n)`; ninguna sale por Teorema Maestro | [complejidad/Solucion.md](complejidad/Solucion.md) |
| 2 | TDA `Turno` y `Agenda`, sobre un vector ordenado | `tda/` |
| 3 | `DepuradorDeSensores.depurar`, con iterador y ventana de mediana | `listas/` |

## Punto 2 — TDA

La decisión que resuelve todo el ejercicio es **mantener el vector ordenado por inicio**.
Con eso salen los tres órdenes que pide el enunciado:

| Operación | Orden | Por qué |
|---|---|---|
| `reservar` | O(n) | búsqueda binaria + corrimiento |
| `cancelar` | O(n) | búsqueda por código + corrimiento |
| `estaLibre` | **O(log n)** | una búsqueda binaria: sólo un turno puede ocupar el intervalo |
| `obtenerPrimerInicioLibre` | O(n) | una pasada por los huecos |
| `obtenerMayorIntervaloLibre` | O(n) | una pasada por los huecos |
| `obtenerPorcentajeDeOcupacion` | O(n) | suma de duraciones |
| `obtenerTurnosEnConflicto` | **O(n + m)** | pasada simultánea por las dos agendas |
| `fusionar` | **O(n + m)** | la mezcla de Mergesort |

- `Turno`: inmutable, igual por código. Toda la cuenta de intervalos vive acá
  (`estaEnConflictoCon`, `seSuperponeCon`, `terminaDespuesDe`).
- `Agenda`: invariantes, hipótesis y el orden de cada operación están en el javadoc de la clase.
- `PrincipalAgenda`: el caso de uso de punta a punta.

## Punto 3 — Listas

Tres subproblemas por lectura, cada uno con nombre propio:

1. corregirla si es anómala contra la mediana,
2. recordarla en la ventana,
3. comprimirla si repite a la anterior.

- `Lectura`, `Registro`: los datos del enunciado. `Registro` usa una `LinkedList`.
- `VentanaDeMediana`: dos vectores de tamaño `ventana` con los mismos valores, uno circular
  (para saber cuál sale) y uno ordenado (para tener la mediana en O(1)).
- `DepuradorDeSensores`: recorre con el `Iterator`; eliminar con `iterador.remove()` es O(1).
  Total O(n · ventana).
- `PrincipalDepuradorDeSensores`: un ejemplo seguido paso a paso.

## Tests

En `app/src/test/java`, en el mismo paquete:

- `tda/AgendaTest`
- `listas/DepuradorDeSensoresTest` (incluye una comparación contra una versión directa,
  sin restricciones, sobre 500 registros al azar)

```bash
./gradlew test --tests "*claude.dificil*"
```
