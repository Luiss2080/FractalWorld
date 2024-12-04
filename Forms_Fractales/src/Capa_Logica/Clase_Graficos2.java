package Capa_Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Clase para graficar el fractal del árbol.
 * 
 */
public class Clase_Graficos2 {
    private JPanel objVentana;
    private Graphics objGrafico;

    public Clase_Graficos2(JPanel obj) {
        this.objVentana = obj;
        this.objVentana.setBackground(Color.WHITE); // Establecer fondo blanco
        this.objGrafico = this.objVentana.getGraphics();
    }

    public void graficarFractalArbol(int n) {
        // Limpiar la ventana antes de dibujar
        this.objGrafico.clearRect(0, 0, objVentana.getWidth(), objVentana.getHeight());
        // Llamar al método recursivo para dibujar el árbol
        dibujarArbol(objVentana.getWidth() / 2, objVentana.getHeight(), -90, n);
    }

    private void dibujarArbol(int x, int y, double angulo, int n) {
        if (n == 1) return;

        // Calcular las coordenadas del siguiente punto
        int x2 = (int) (x + Math.cos(Math.toRadians(angulo)) * n * 10);
        int y2 = (int) (y + Math.sin(Math.toRadians(angulo)) * n * 10);

        // Dibujar la línea
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawLine(x, y, x2, y2);

        // Llamar recursivamente para dibujar las ramas
        dibujarArbol(x2, y2, angulo - 20, n - 1); // Rama izquierda
        dibujarArbol(x2, y2, angulo + 20, n - 1); // Rama derecha
    }
}
