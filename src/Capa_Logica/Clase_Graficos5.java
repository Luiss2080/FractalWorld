package Capa_Logica; 

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author LuissxD
 */
public class Clase_Graficos5 {
    private JPanel objVentana;
    private Graphics objGrafico;

    public Clase_Graficos5(JPanel obj) {
        this.objVentana = obj;
        this.objGrafico = this.objVentana.getGraphics();
    }

    // Método para graficar la Curva de Hilbert
    public void graficarFractal(int n) {
        // Obtenemos el objeto gráfico actualizado del JPanel
        this.objGrafico = this.objVentana.getGraphics();

        // Limpiamos el área de dibujo antes de graficar
        this.objGrafico.clearRect(0, 0, this.objVentana.getWidth(), this.objVentana.getHeight());

        // Calcular el centro del JPanel para centrar la curva
        int centerX = objVentana.getWidth() / 2;
        int centerY = objVentana.getHeight() / 2;

        // Definir el tamaño inicial del fractal (ajustable)
        int size = 270;  // Tamaño inicial del lado del primer cuadrado

        // Calcular la posición inicial para centrar el fractal
        int startX = centerX - size / 2;
        int startY = centerY - size / 2;

        // Iniciar el proceso recursivo para dibujar la curva de Hilbert
        dibujarHilbert(startX, startY, size, 0, n);
    }

    // Método recursivo para dibujar la Curva de Hilbert
    private void dibujarHilbert(int x, int y, int size, int direction, int n) {
        if (n == 1) {
            return;
        }

        int newSize = size / 2;

        // Dirección: 0=arriba, 1=derecha, 2=abajo, 3=izquierda
        if (direction == 0) {
            // Recursivamente dibujar la Curva de Hilbert para la dirección de arriba
            dibujarHilbert(x, y, newSize, 1, n - 1);
            graficarLinea(x + newSize, y, x + newSize, y + newSize);
            dibujarHilbert(x + newSize, y, newSize, 0, n - 1);
            graficarLinea(x + newSize, y + newSize, x, y + newSize);
            dibujarHilbert(x, y + newSize, newSize, 0, n - 1);
            graficarLinea(x, y + newSize, x, y + 2 * newSize);
            dibujarHilbert(x, y + newSize, newSize, 3, n - 1);
        } else if (direction == 1) {
            dibujarHilbert(x, y, newSize, 0, n - 1);
            graficarLinea(x, y + newSize, x + newSize, y + newSize);
            dibujarHilbert(x + newSize, y, newSize, 1, n - 1);
            graficarLinea(x + newSize, y + newSize, x + newSize, y + 2 * newSize);
            dibujarHilbert(x + newSize, y + newSize, newSize, 2, n - 1);
            graficarLinea(x + newSize, y + 2 * newSize, x + 2 * newSize, y + 2 * newSize);
            dibujarHilbert(x + newSize, y + newSize, newSize, 1, n - 1);
        } else if (direction == 2) {
            dibujarHilbert(x, y, newSize, 1, n - 1);
            graficarLinea(x + newSize, y, x + newSize, y + newSize);
            dibujarHilbert(x, y + newSize, newSize, 2, n - 1);
            graficarLinea(x + newSize, y + newSize, x + newSize, y + 2 * newSize);
            dibujarHilbert(x + newSize, y + newSize, newSize, 2, n - 1);
            graficarLinea(x + newSize, y + 2 * newSize, x + 2 * newSize, y + 2 * newSize);
            dibujarHilbert(x + newSize, y, newSize, 3, n - 1);
        } else if (direction == 3) {
            dibujarHilbert(x, y, newSize, 2, n - 1);
            graficarLinea(x, y + newSize, x + newSize, y + newSize);
            dibujarHilbert(x + newSize, y, newSize, 3, n - 1);
            graficarLinea(x + newSize, y + newSize, x + newSize, y + 2 * newSize);
            dibujarHilbert(x + newSize, y + newSize, newSize, 1, n - 1);
            graficarLinea(x + newSize, y + 2 * newSize, x, y + 2 * newSize);
            dibujarHilbert(x, y + newSize, newSize, 0, n - 1);
        }
    }

    // Método para dibujar una línea simple
    private void graficarLinea(int x1, int y1, int x2, int y2) {
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawLine(x1, y1, x2, y2);
    }
}
