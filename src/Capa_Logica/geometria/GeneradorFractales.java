package Capa_Logica.geometria;

import java.util.ArrayList;
import java.util.List;

/**
 * Generadores puros de la geometría de cada fractal (sin Swing ni AWT): devuelven
 * las figuras a dibujar, lo que permite probarlos sin interfaz gráfica.
 *
 * <p>Todos validan el nivel con {@link Fractal#validar(int)}; la profundidad de
 * recursión nunca supera el nivel máximo del fractal.</p>
 */
public final class GeneradorFractales {

    private GeneradorFractales() {
    }

    /** Copo de nieve de Koch sobre un triángulo fijo de 400 px de lado. Nivel 1 = triángulo. */
    public static List<Segmento> copoDeNieve(int n) {
        Fractal.COPO_DE_NIEVE.validar(n);
        List<Segmento> out = new ArrayList<>();
        koch(out, 50, 400, 250, 54, 60, n);
        koch(out, 250, 54, 450, 400, 300, n);
        koch(out, 450, 400, 50, 400, 180, n);
        return out;
    }

    private static void koch(List<Segmento> out, double x1, double y1, double x2, double y2, int tita, int n) {
        if (n == 1) {
            out.add(new Segmento(x1, y1, x2, y2));
            return;
        }
        double xm1 = x1 + (x2 - x1) / 3;
        double ym1 = y1 + (y2 - y1) / 3;
        double xm3 = x1 + 2 * (x2 - x1) / 3;
        double ym3 = y1 + 2 * (y2 - y1) / 3;
        double dis = Math.hypot(xm1 - x1, ym1 - y1);
        double xm2 = xm1 + dis * Math.cos(Math.toRadians(tita + 60));
        double ym2 = ym1 - dis * Math.sin(Math.toRadians(tita + 60));
        koch(out, x1, y1, xm1, ym1, tita, n - 1);
        koch(out, xm1, ym1, xm2, ym2, tita + 60, n - 1);
        koch(out, xm2, ym2, xm3, ym3, tita + 300, n - 1);
        koch(out, xm3, ym3, x2, y2, tita, n - 1);
    }

    /**
     * Árbol binario: cada rama mide {@code nivel * 10} px y se bifurca +-20 grados.
     * Nivel 1 = solo el tronco; produce 2^n - 1 segmentos.
     */
    public static List<Segmento> arbol(int n, int ancho, int alto) {
        Fractal.ARBOL.validar(n);
        List<Segmento> out = new ArrayList<>();
        arbol(out, ancho / 2.0, alto, -90, n);
        return out;
    }

    private static void arbol(List<Segmento> out, double x, double y, double angulo, int n) {
        if (n < 1) {
            return;
        }
        double x2 = x + Math.cos(Math.toRadians(angulo)) * n * 10;
        double y2 = y + Math.sin(Math.toRadians(angulo)) * n * 10;
        out.add(new Segmento(x, y, x2, y2));
        arbol(out, x2, y2, angulo - 20, n - 1);
        arbol(out, x2, y2, angulo + 20, n - 1);
    }

    /** Triángulo de Sierpinski (triángulo fijo de 400 px de base). Nivel 1 = un triángulo. */
    public static List<Segmento> sierpinski(int n) {
        Fractal.SIERPINSKI.validar(n);
        List<Segmento> out = new ArrayList<>();
        sierpinski(out, 250, 50, 50, 450, 450, 450, n);
        return out;
    }

    private static void sierpinski(List<Segmento> out, double x1, double y1, double x2, double y2,
                                   double x3, double y3, int n) {
        if (n == 1) {
            out.add(new Segmento(x1, y1, x2, y2));
            out.add(new Segmento(x2, y2, x3, y3));
            out.add(new Segmento(x3, y3, x1, y1));
            return;
        }
        double mx1 = (x1 + x2) / 2, my1 = (y1 + y2) / 2;
        double mx2 = (x2 + x3) / 2, my2 = (y2 + y3) / 2;
        double mx3 = (x1 + x3) / 2, my3 = (y1 + y3) / 2;
        sierpinski(out, x1, y1, mx1, my1, mx3, my3, n - 1);
        sierpinski(out, mx1, my1, x2, y2, mx2, my2, n - 1);
        sierpinski(out, mx3, my3, mx2, my2, x3, y3, n - 1);
    }

    /**
     * Curva de Lévy C: cada segmento se reemplaza por dos catetos de un triángulo
     * rectángulo isósceles, ambos con el mismo giro. Nivel n = 2^(n-1) segmentos.
     * No es la curva del dragón de Heighway, que alterna el giro.
     */
    public static List<Segmento> curvaLevyC(int n, int ancho, int alto) {
        Fractal.LEVY_C.validar(n);
        List<Segmento> out = new ArrayList<>();
        double cx = ancho / 2.0, cy = alto / 2.0;
        levy(out, cx - 100, cy, cx + 100, cy, n);
        return out;
    }

    private static void levy(List<Segmento> out, double x1, double y1, double x2, double y2, int n) {
        if (n == 1) {
            out.add(new Segmento(x1, y1, x2, y2));
            return;
        }
        double mx = (x1 + x2) / 2 + (y1 - y2) / 2;
        double my = (y1 + y2) / 2 + (x2 - x1) / 2;
        levy(out, x1, y1, mx, my, n - 1);
        levy(out, mx, my, x2, y2, n - 1);
    }

    /**
     * Curva de Hilbert de orden n sobre un cuadrado de 270 px centrado: recorre
     * 4^n puntos en una sola polilínea (4^n - 1 segmentos, sin ramificaciones).
     */
    public static List<Segmento> curvaHilbert(int n, int ancho, int alto) {
        Fractal.HILBERT.validar(n);
        final double lado = 270;
        int celdas = 1 << n;
        double paso = lado / (celdas - 1);
        double x0 = ancho / 2.0 - lado / 2, y0 = alto / 2.0 - lado / 2;
        List<Segmento> out = new ArrayList<>();
        int total = celdas * celdas;
        int[] p = hilbertXY(celdas, 0);
        for (int d = 1; d < total; d++) {
            int[] q = hilbertXY(celdas, d);
            out.add(new Segmento(x0 + p[0] * paso, y0 + p[1] * paso, x0 + q[0] * paso, y0 + q[1] * paso));
            p = q;
        }
        return out;
    }

    /** Índice d de la curva de Hilbert a celda (x, y) en una malla lado x lado (lado potencia de 2). */
    static int[] hilbertXY(int lado, int d) {
        int x = 0, y = 0, t = d;
        for (int s = 1; s < lado; s *= 2) {
            int rx = 1 & (t / 2);
            int ry = 1 & (t ^ rx);
            if (ry == 0) {
                if (rx == 1) {
                    x = s - 1 - x;
                    y = s - 1 - y;
                }
                int tmp = x;
                x = y;
                y = tmp;
            }
            x += s * rx;
            y += s * ry;
            t /= 4;
        }
        return new int[]{x, y};
    }

    /** Anillos: una circunferencia de 200 px con cuatro copias a la mitad de tamaño (izq., der., arriba, abajo). */
    public static List<Circulo> anillos(int n, int ancho, int alto) {
        Fractal.ANILLOS.validar(n);
        List<Circulo> out = new ArrayList<>();
        anillo(out, ancho / 2.0, alto / 2.0, 200, n);
        return out;
    }

    private static void anillo(List<Circulo> out, double x, double y, double diametro, int n) {
        if (n < 1) {
            return;
        }
        out.add(new Circulo(x, y, diametro));
        double mitad = diametro / 2;
        anillo(out, x - mitad, y, mitad, n - 1);
        anillo(out, x + mitad, y, mitad, n - 1);
        anillo(out, x, y - mitad, mitad, n - 1);
        anillo(out, x, y + mitad, mitad, n - 1);
    }

    /** Pentágonos: uno de radio 200 px con otro a un tercio del radio en cada vértice. Devuelve sus lados. */
    public static List<Segmento> pentagonos(int n, int ancho, int alto) {
        Fractal.PENTAGONOS.validar(n);
        List<Segmento> out = new ArrayList<>();
        pentagono(out, ancho / 2.0, alto / 2.0, 200, n);
        return out;
    }

    private static void pentagono(List<Segmento> out, double x, double y, double radio, int n) {
        if (n < 1) {
            return;
        }
        double[] vx = new double[5], vy = new double[5];
        for (int i = 0; i < 5; i++) {
            double a = Math.toRadians(72) * i - Math.PI / 2;
            vx[i] = x + radio * Math.cos(a);
            vy[i] = y + radio * Math.sin(a);
        }
        for (int i = 0; i < 5; i++) {
            int j = (i + 1) % 5;
            out.add(new Segmento(vx[i], vy[i], vx[j], vy[j]));
        }
        for (int i = 0; i < 5; i++) {
            pentagono(out, vx[i], vy[i], radio / 3, n - 1);
        }
    }
}
