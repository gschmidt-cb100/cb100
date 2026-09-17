# Sonidos de prueba

MP3 y WAV sintetizados por la cátedra para probar `ReproductorDeSonido` y
para que los grupos arranquen el TP 2 con algo que suene. Son libres: no
tienen derechos de nadie porque los fabrica `generar-sonidos.py` con
matemática.

Cada sonido está **dos veces, en los dos formatos**, con exactamente las
mismas muestras adentro. La única diferencia es cómo se guardan:

| Sonido | Para qué | Duración | MP3 | WAV | WAV / MP3 |
|---|---|---|---:|---:|---:|
| `paso` | cada movimiento del héroe | 0,12 s | 1,2 KB | 10,4 KB | 8× |
| `agua` | entrar a una celda de agua | 0,35 s | 3,1 KB | 30,2 KB | 10× |
| `tesoro` | levantar un objeto | 0,55 s | 4,7 KB | 47,4 KB | 10× |
| `musica` | fondo, en bucle (Am - F - C - G a 120 bpm) | 16 s | 188 KB | 1.378 KB | 7× |

## Por qué la diferencia

Un **WAV** guarda cada muestra tal cual: 44.100 muestras por segundo, 2 bytes
cada una, en mono. Eso son **88 KB por segundo**, siempre, suene lo que
suene. Un minuto de música son 5 MB; en estéreo, 10 MB. La cuenta es
`44100 × 2 × canales × segundos + 44 bytes de cabecera`.

Un **MP3** comprime tirando lo que el oído no distingue, y gasta una cantidad
fija de bits por segundo que elige quien lo codifica: acá 64 kbps para los
efectos y 96 kbps para la música, es decir **8 y 12 KB por segundo**. Por eso
la relación cambia según el sonido: la música, con más contenido que
conservar, se comprime un poco menos que un golpe seco.

Para el juego, la conclusión es simple: **efectos y música en MP3**. Los
cuatro sonidos completos pesan 197 KB en MP3 y 1,4 MB en WAV.

La contra del MP3: Java no lo decodifica solo, hace falta `mp3spi` en
`build.gradle`, que ya está declarada. El WAV funciona sin nada.

## Cómo se usan

Se cargan por ruta relativa desde la raíz del proyecto:

```java
Path sonidos = Path.of("app/src/main/java/ar/uba/fi/cb100/librerias/sonido/sonidos");
reproductor.cargarEfecto("paso", sonidos.resolve("paso.mp3"));   // o paso.wav: da lo mismo
```

Gradle ignora todo lo que no es `.java` dentro de `src/main/java`, así que
estos archivos **no están en el classpath**: se leen con `Path` como
cualquier archivo del disco, igual que el CSV del TP 1.

## Regenerarlos o cambiarlos

```bash
pip install lameenc
python generar-sonidos.py
```

El script tiene los generadores básicos (seno, triángulo, cuadrada, ruido,
barrido) y una función por sonido. Para un sonido nuevo, se escribe una
función más y se agrega una línea al final: sale en los dos formatos. La
semilla del ruido está fija, así que dos corridas dan exactamente los mismos
bytes.
