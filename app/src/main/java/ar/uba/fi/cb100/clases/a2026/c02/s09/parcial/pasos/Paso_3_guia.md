# Guía de Orientación en el Diseño de Software

Esta guía reúne los indicadores para evaluar en qué dirección avanza un proyecto de desarrollo y determinar si la solución elegida es robusta o presenta problemas de arquitectura.

---

## 1. Camino Correcto: POO, Modularizado, Robusto y Extensible (P1 – P7)

Indicadores de que el diseño orientado a objetos y la modularización se están aplicando de forma efectiva:

* **P1. Métodos compactos:** Los métodos entran cómodamente en una sola pantalla sin necesidad de desplazarse verticalmente.
* **P2. Adaptabilidad a cambios:** Si aparece una condición o requerimiento nuevo, el código existente se adapta fácilmente sin requerir refactorizaciones masivas.
* **P3. Reutilización de TDAs:** Al abordar otro problema similar, se puede portar y reutilizar el 90% de los Tipos de Datos Abstractos (TDAs).
* **P4. Trabajo en equipo fluido:** Si el desarrollo se realiza entre varias personas, la división de tareas y módulos resulta clara y sencilla.
* **P5. Pilares de POO aplicables:** Se pueden aplicar de forma natural y transparente la herencia, el encapsulamiento y el polimorfismo.
* **P6. Separación de responsabilidades:** Existe una clara diferenciación entre clases de datos (estructuras de información) y clases funcionales (lógica de negocio/operación).
* **P7. Mantenibilidad sencilla:** Modificar cualquier parte puntual del sistema es una tarea simple y predecible.

---

## 2. Mala Arquitectura: Bolsa de Gatos (G1 – G7)

Síntomas de un código desordenado, acoplado y sin abstracción adecuada:

* **G1. TDAs extensos y mezclados:** Se observan TDAs muy largos donde el alcance de responsabilidades está totalmente confuso.
* **G2. Imposibilidad de portar TDAs:** Los TDAs están tan fuertemente acoplados entre sí o al contexto que resulta imposible portarlos a otros proyectos.
* **G3. Dificultad para dividir tareas:** El equipo encuentra bloqueos frecuentes al no poder repartirse el código de forma independiente.
* **G4. Acumulación de advertencias:** Aparece una gran cantidad de *warnings* en el código durante la compilación o análisis estático.
* **G5. Firma de métodos recargada:** Los métodos reciben una cantidad excesiva de parámetros.
* **G6. Acoplamiento con E/S:** Los TDAs interactúan o se relacionan directamente con la interfaz de pantalla o el teclado.
* **G7. Nula reutilización de métodos:** Los métodos son tan específicos o dependientes que no pueden reutilizarse en otras funciones.

---

## 3. Solución Frágil: Edificio con Hilos (F1 – F3)

Características de un desarrollo endeble o "solución barata":

* **F1. Efectos colaterales imprevistos:** Tocar una parte del código provoca fallos en zonas no relacionadas ("se toca algo y se rompe otra cosa").
* **F2. Dependencia del autor:** Únicamente el programador que escribió el código es capaz de mantenerlo o corregirlo.
* **F3. Reescritura obligada:** Para extender o agregar funcionalidad significativa, resulta más rápido y fácil hacer todo de nuevo desde cero.

---

## 4. Sin Salida: Bloqueo en el Precipicio (N1 – N2)

Indicadores de que el desarrollo ha llegado a un punto de parálisis:

* **N1. Métodos inconclusos:** Se intenta implementar un método pero resulta imposible completarlo debido a la complejidad desmedida.
* **N2. Imposibilidad de integración:** Las diferentes partes desarrolladas no pueden unirse o integrarse de manera funcional.

---

## Matriz Resumen de Indicadores

| Estado / Dirección | Puntos de Chequeo | Resultado Esperado |
| :--- | :--- | :--- |
| **POO / Robusto** | **P1 a P7** | Software extensible, fácil de mantener y de escalar en equipo. |
| **Bolsa de Gatos** | **G1 a G7** | Código funcional pero caótico, rígido y altamente acoplado. |
| **Solución Frágil** | **F1 a F3** | Estructura inestable que colapsa ante pequeños cambios. |
| **No se puede solucionar** | **N1 a N2** | Parálisis en la implementación e imposibilidad de avanzar. |