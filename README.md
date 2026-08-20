# CB100 — Código de clase

Repositorio con **todo el código que se explica en las clases** de la materia
Algoritmos y Estructuras de Datos (CB100, UBA). Proyecto en **Java 25** con
**Gradle** y **Git**, pensado para abrirse con **IntelliJ IDEA**.

## Requisitos

- JDK 25 (recomendado: Temurin / Eclipse Adoptium).
- IntelliJ IDEA (Community o Ultimate).
- Git.

No hace falta instalar Gradle: el proyecto trae el *Gradle Wrapper*
(`gradlew`), que descarga la versión correcta automáticamente.

## Cómo empezar

```bash
git clone <URL-del-repo>
cd cb100
./gradlew run        # en Windows:  gradlew.bat run
```

## Comandos útiles

| Qué querés hacer            | Comando                     |
|-----------------------------|-----------------------------|
| Ejecutar el programa main   | `./gradlew run`             |
| Compilar todo               | `./gradlew build`           |
| Correr los tests            | `./gradlew test`            |
| Limpiar los compilados      | `./gradlew clean`           |

## Estructura

```
cb100/
├── app/                                  ← el módulo con el código
│   ├── build.gradle                      ← instructivo de construcción del módulo
│   └── src/
│       ├── main/java/ar/uba/fi/cb100/    ← material/ y guia/ por unidad
│       └── test/java/ar/uba/fi/cb100/    ← tests JUnit 5
├── settings.gradle                       ← qué módulos componen el build
├── gradlew / gradlew.bat                 ← Gradle Wrapper
└── gradle/wrapper/                       ← versión de Gradle a usar
```

Cada unidad tiene su paquete acá: el código explicado en clase vive en
`ar.uba.fi.cb100.material.iNN_*` y los ejercicios en `ar.uba.fi.cb100.guia.iNN_*`.
