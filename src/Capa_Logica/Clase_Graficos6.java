package Capa_Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author LuissxD
 */
public class Clase_Graficos6 {
    private JPanel objVentana;
    private Graphics objGrafico;

    public Clase_Graficos6(JPanel obj) {
        this.objVentana = obj;
        this.objGrafico = this.objVentana.getGraphics();
    }

    // Método para graficar el fractal de anillos
    public void graficarFractal(int n) {
        // Obtenemos el objeto gráfico actualizado del JPanel
        this.objGrafico = this.objVentana.getGraphics();

        // Limpiamos el área de dibujo antes de graficar
        this.objGrafico.clearRect(0, 0, this.objVentana.getWidth(), this.objVentana.getHeight());

        // Calcular el centro del JPanel para centrar el fractal
        int centerX = objVentana.getWidth() / 2;
        int centerY = objVentana.getHeight() / 2;

        // Definir el tamaño inicial del fractal (ajustable)
        int size = 200;  // Diámetro inicial del primer círculo

        // Iniciar el proceso recursivo para dibujar el fractal de anillos
        dibujarAnillo(centerX, centerY, size, n);
    }

    // Método recursivo para dibujar el fractal de anillos
    private void dibujarAnillo(int x, int y, int size, int n) {
        if (n == 0) {
            return; 
        }

        // Dibujar el círculo principal en la posición actual
        graficarCirculo(x, y, size);

        // Reducir el tamaño para los siguientes anillos
        int newSize = size / 2;

        // Dibujar anillos alrededor del círculo actual en 4 direcciones
        dibujarAnillo(x - newSize, y, newSize, n - 1); // Izquierda
        dibujarAnillo(x + newSize, y, newSize, n - 1); // Derecha
        dibujarAnillo(x, y - newSize, newSize, n - 1); // Arriba
        dibujarAnillo(x, y + newSize, newSize, n - 1); // Abajo
    }

    // Método para dibujar un círculo simple centrado en (x, y)
    private void graficarCirculo(int x, int y, int size) {
        int radio = size / 2;
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawOval(x - radio, y - radio, size, size);
    }
}

