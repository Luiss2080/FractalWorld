package Capa_Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Polygon;

/**
 *
 * @author LuissxD
 */
public class Clase_Graficos7 {
    private JPanel objVentana;
    private Graphics objGrafico;

    public Clase_Graficos7(JPanel obj) {
        this.objVentana = obj;
        this.objGrafico = this.objVentana.getGraphics();
    }

    // Método para graficar el fractal de pentágono
    public void graficarFractal(int n) {
        // Obtenemos el objeto gráfico actualizado del JPanel
        this.objGrafico = this.objVentana.getGraphics();

        // Limpiamos el área de dibujo antes de graficar
        this.objGrafico.clearRect(0, 0, this.objVentana.getWidth(), this.objVentana.getHeight());

        // Calcular el centro del JPanel para centrar el fractal
        int centerX = objVentana.getWidth() / 2;
        int centerY = objVentana.getHeight() / 2;

        // Definir el tamaño inicial del fractal (ajustable)
        int size = 200;  // Tamaño inicial del pentágono

        // Iniciar el proceso recursivo para dibujar el fractal de pentágono
        dibujarPentagono(centerX, centerY, size, n);
    }

    // Método recursivo para dibujar el fractal de pentágono
    private void dibujarPentagono(int x, int y, int size, int n) {
        if (n == 0) {
            return; // Terminar la recursión cuando n llegue a 0
        }

        // Dibujar el pentágono principal en la posición actual
        graficarPentagono(x, y, size);

        // Reducir el tamaño para los siguientes pentágonos
        int newSize = size / 3;

        // Calcular las posiciones para los nuevos pentágonos en cada vértice
        double angle = Math.toRadians(72);
        for (int i = 0; i < 5; i++) {
            int newX = (int) (x + (size * Math.cos(i * angle - Math.PI / 2)));
            int newY = (int) (y + (size * Math.sin(i * angle - Math.PI / 2)));
            dibujarPentagono(newX, newY, newSize, n - 1);
        }
    }

    // Método para dibujar un pentágono centrado en (x, y)
    private void graficarPentagono(int x, int y, int size) {
        Polygon pentagono = new Polygon();

        // Calcular los vértices del pentágono
        double angle = Math.toRadians(72);
        for (int i = 0; i < 5; i++) {
            int newX = (int) (x + (size * Math.cos(i * angle - Math.PI / 2)));
            int newY = (int) (y + (size * Math.sin(i * angle - Math.PI / 2)));
            pentagono.addPoint(newX, newY);
        }

        // Dibujar el pentágono
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawPolygon(pentagono);
    }
}


