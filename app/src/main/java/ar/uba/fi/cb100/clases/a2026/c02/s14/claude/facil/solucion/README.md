# Ejemplo de parcial 26/11 (versión fácil) — Solución

Enunciado: [2026-2C-3-oportunidad-26-11-facil.pdf](../2026-2C-3-oportunidad-26-11-facil.pdf)

| Punto | Tema | Dónde está |
|---|---|---|
| 1 | Complejidad: `T(n) = 2 T(n/2) + 3n` da `Θ(n log n)` | [complejidad/Solucion.md](complejidad/Solucion.md) |
| 2 | TDA `Paquete` y `Camion`, sobre un vector | `tda/` |
| 3 | `ServicioDeStreaming.buscarSeriesMejorPuntuadas`, con la API de List | `listas/` |

## Punto 2 — TDA

- `Paquete`: inmutable, igual por código. Responde lo suyo (`vaA`, `esFragil`, `esMasPesadoQue`).
- `Camion`: un `Paquete[]` del tamaño de la cantidad de lugares; `null` es lugar libre.
  El peso total y la ocupación son métodos, no atributos.
- `PrincipalCamion`: el caso de uso de punta a punta.

## Punto 3 — Listas

La estrategia es la misma del parcial del 28/10 (amplitud térmica): agrupar en una
estadística por serie, filtrar, ordenar y proyectar. Cada paso es un método.

- `Serie`, `Opinion`, `EstadoOpinion`: los datos del enunciado.
- `EstadisticaDeSerie`: acumula cantidad y suma de puntajes de una serie; el promedio se calcula.
- `ServicioDeStreaming`: el método pedido.
- `PrincipalServicioDeStreaming`: un ejemplo con el resultado esperado.

## Tests

En `app/src/test/java`, en el mismo paquete:

- `tda/CamionTest`
- `listas/ServicioDeStreamingTest`

```bash
./gradlew test --tests "*claude.facil*"
```
