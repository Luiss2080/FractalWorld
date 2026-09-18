# <<NOMBRE>>

Aplicación de escritorio en **Java Swing** que dibuja siete fractales recursivos.
El usuario elige un fractal en el menú principal, escribe el nivel de recursión
(**Valor N**) y pulsa *Dibujar Fractal*.

> No es una simulación en tiempo real ni una animación: cada pulsación calcula
> la figura completa y la muestra de una vez.

## Fractales implementados

| # | Fractal | Qué dibuja | Niveles válidos |
|---|---------|------------|-----------------|
| 1 | Copo de nieve de Koch | Triángulo cuyos lados se subdividen en 4 tramos | 1 - 7 |
| 2 | Árbol binario | Tronco con ramas que se bifurcan ±20°, de longitud `nivel * 10` px | 1 - 12 |
| 3 | Triángulo de Sierpinski | Subdivisión en 3 triángulos por nivel | 1 - 9 |
| 4 | Curva de Lévy C | Cada segmento se reemplaza por dos catetos de un triángulo rectángulo isósceles | 1 - 16 |
| 5 | Curva de Hilbert | Polilínea única de 4^N puntos (algoritmo estándar índice a celda) | 1 - 7 |
| 6 | Anillos | Circunferencia con cuatro copias a mitad de tamaño (izq., der., arriba, abajo) | 1 - 7 |
| 7 | Pentágonos | Pentágono con otro a 1/3 del radio en cada vértice | 1 - 6 |

El nivel 1 es siempre la figura base. Un valor vacío, no numérico, decimal o
fuera de rango muestra un aviso en lugar de dibujar. Los máximos existen porque
el número de figuras crece de forma exponencial (por ejemplo 4^N en Koch) y más
allá de ellos el detalle es menor que un píxel.

## Arquitectura

```
src/
  Capa_Logica/
    Prueba_Logica.java            Punto de entrada; abre la ventana principal en el EDT
    Clase_Graficos*.java          Adaptadores: piden la geometria al generador y se la pasan al panel
    geometria/
      GeneradorFractales.java     Geometria pura de cada fractal (sin Swing), probada con JUnit
      Fractal.java                Enum con el rango de niveles de cada fractal y su validacion
      Segmento.java, Circulo.java Figuras resultantes
  capa_presentacion/
    pantalla_principal.java       Menu con los siete fractales
    fractal1..7.java              Una ventana por fractal (generadas con el editor de NetBeans, con .form)
    PanelFractal.java             JPanel que guarda las figuras y las pinta en paintComponent
    Entrada.java                  Lectura validada del campo "Valor N"
test/Capa_Logica/geometria/       Pruebas JUnit 5 de los generadores
```

Las figuras se guardan en `PanelFractal` y se pintan en `paintComponent`, por lo
que el dibujo se conserva al redimensionar, minimizar o tapar la ventana. El
cálculo se hace en el hilo de eventos: los niveles máximos generan a lo sumo
unas decenas de miles de figuras, por eso no se usa un hilo aparte.

## Requisitos

- JDK 21 (el proyecto NetBeans y el `pom.xml` apuntan a 21).
- Maven 3.9+ para compilar y probar desde la línea de comandos, **o** Apache
  NetBeans (el proyecto conserva `build.xml` y `nbproject/`).

## Compilar, ejecutar y probar

```
mvn test                                     # 25 pruebas JUnit
mvn package                                  # genera target/fractalworld-1.0-SNAPSHOT.jar
java -jar target/fractalworld-1.0-SNAPSHOT.jar
```

Con NetBeans: abrir la carpeta como proyecto y ejecutar `Capa_Logica.Prueba_Logica`.
El workflow `.github/workflows/ci.yml` ejecuta `mvn verify` en cada push y pull request.

## Qué está probado y qué no

- **Probado automáticamente:** la geometría (conteo exacto de figuras por nivel,
  continuidad de las polilíneas, Hilbert sin celdas repetidas ni ramificaciones,
  pentágono regular) y la validación de niveles.
- **Verificado manualmente una vez:** que las siete ventanas dibujan el nivel 3 y
  conservan el dibujo al redimensionar (prueba de humo en un escritorio Windows).
- **No probado:** la apariencia visual final, el comportamiento en otros sistemas
  operativos ni la interfaz con lectores de pantalla.

## Limitaciones conocidas

- Los botones *Cuadrado Relleno* y las flechas `<----` / `---->` de las ventanas
  de fractal son restos de un ejercicio: solo `---->` de la ventana 1 mueve un
  cuadrado; el resto no hace nada.
- En el menú principal, los campos *Comentario* y los botones *Enviar* no
  tienen función.
- Los parámetros geométricos (tamaños base, ángulos, posición del copo de Koch
  y de Sierpinski, fija en 500 x 500 px) están escritos en el código y no son
  configurables.
- La curva 4 es la de **Lévy C**, no la del dragón de Heighway.
- Cerrar cualquier ventana con la X termina toda la aplicación.
- No se puede guardar ni exportar la imagen.

## Licencia

Este repositorio **no incluye archivo de licencia**. Sin una licencia explícita
todos los derechos quedan reservados por el autor; hay que añadir un archivo
`LICENSE` para permitir su reutilización.
