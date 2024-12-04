package Capa_Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author LuissxD
 */
public class Clase_Graficos3 {
    private JPanel objVentana;
    private Graphics objGrafico;

    public Clase_Graficos3(JPanel obj) {
        this.objVentana = obj;
        this.objGrafico = this.objVentana.getGraphics();
    }

    // Método para graficar el Fractal de Sierpinski (Triángulo)
    public void graficarFractalSierpinski(int n) {
        // Obtenemos el objeto gráfico actualizado del JPanel
        this.objGrafico = this.objVentana.getGraphics();
        
        // Limpiamos el área de dibujo antes de graficar
        this.objGrafico.clearRect(0, 0, this.objVentana.getWidth(), this.objVentana.getHeight());

        
        // Define las coordenadas del triángulo inicial
        int[] x = { 250, 50, 450 };
        int[] y = { 50, 450, 450 };

        // Iniciar el proceso recursivo
        graficarTrianguloSierpinski(x[0], y[0], x[1], y[1], x[2], y[2], n);
    }

    // Método recursivo para dibujar el fractal de Sierpinski
    private void graficarTrianguloSierpinski(int x1, int y1, int x2, int y2, int x3, int y3, int n) {
        if (n == 1) {
            // Dibuja el triángulo cuando se alcanza el nivel más bajo
            graficarTriangulo(x1, y1, x2, y2, x3, y3);
        } else {
            // Calcula los puntos medios de cada lado del triángulo
            int midX1 = (x1 + x2) / 2;
            int midY1 = (y1 + y2) / 2;
            int midX2 = (x2 + x3) / 2;
            int midY2 = (y2 + y3) / 2;
            int midX3 = (x1 + x3) / 2;
            int midY3 = (y1 + y3) / 2;

            // Llamada recursiva para los 3 triángulos más pequeños
            graficarTrianguloSierpinski(x1, y1, midX1, midY1, midX3, midY3, n - 1);
            graficarTrianguloSierpinski(midX1, midY1, x2, y2, midX2, midY2, n - 1);
            graficarTrianguloSierpinski(midX3, midY3, midX2, midY2, x3, y3, n - 1);
        }
    }

    // Método para dibujar un triángulo utilizando líneas
    private void graficarTriangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawLine(x1, y1, x2, y2);
        this.objGrafico.drawLine(x2, y2, x3, y3);
        this.objGrafico.drawLine(x3, y3, x1, y1);
    }
}
