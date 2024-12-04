package Capa_Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author LuissxD
 */
public class Clase_Graficos4 {
    private JPanel objVentana;
    private Graphics objGrafico;

    public Clase_Graficos4(JPanel obj) {
        this.objVentana = obj;
        this.objGrafico = this.objVentana.getGraphics();
    }

    // Método para graficar la Curva del Dragón de Heighway
    public void graficarCurvaDragon(int n) {
        // Obtenemos el objeto gráfico actualizado del JPanel
        this.objGrafico = this.objVentana.getGraphics();

        // Limpiamos el área de dibujo antes de graficar
        this.objGrafico.clearRect(0, 0, this.objVentana.getWidth(), this.objVentana.getHeight());

        // Calcular el centro del JPanel para centrar la curva
        int centerX = objVentana.getWidth() / 2;
        int centerY = objVentana.getHeight() / 2;

        // Define el tamaño inicial de la curva
        int startX = centerX - 100;
        int startY = centerY;
        int endX = centerX + 100;
        int endY = centerY;

        // Iniciar el proceso recursivo
        dibujarCurvaDragon(startX, startY, endX, endY, n);
    }

    // Método recursivo para dibujar la Curva del Dragón de Heighway
    private void dibujarCurvaDragon(int x1, int y1, int x2, int y2, int n) {
        if (n == 1) {
            // Dibuja una línea simple si el nivel de recursión es 0
            dibujarLinea(x1, y1, x2, y2);
        } else {
            // Calcula el punto medio con una rotación de 45 grados
            int midX = (x1 + x2) / 2 + (y1 - y2) / 2;
            int midY = (y1 + y2) / 2 + (x2 - x1) / 2;

            // Llamadas recursivas para dividir y rotar
            dibujarCurvaDragon(x1, y1, midX, midY, n - 1);
            dibujarCurvaDragon(midX, midY, x2, y2, n - 1);
        }
    }

    // Método para dibujar una línea simple
    private void dibujarLinea(int x1, int y1, int x2, int y2) {
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawLine(x1, y1, x2, y2);
    }
}

