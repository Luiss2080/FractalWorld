package Capa_Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Clase_Graficos2 {
    private JPanel objVentana;
    private Graphics objGrafico;
    private static final int LONGITUD_BASE = 350;
    private static final int ALTURA_TRIANGULO = 173; // Altura reducida para mejor proporción

    public Clase_Graficos2(JPanel obj) {
        this.objVentana = obj;
        this.objVentana.setBackground(Color.WHITE);
    }

    public void graficarFractal(int n) {
        this.objGrafico = this.objVentana.getGraphics();
        this.objGrafico.clearRect(0, 0, objVentana.getWidth(), objVentana.getHeight());
        
        int centroX = objVentana.getWidth() / 2;
        int centroY = objVentana.getHeight() / 2;
        
        // Dibuja la línea base horizontal
        dibujarFractalRecursivo(
            centroX - LONGITUD_BASE/2, 
            centroY, 
            centroX + LONGITUD_BASE/2, 
            centroY, 
            n
        );
    }

    private void dibujarFractalRecursivo(int x1, int y1, int x2, int y2, int n) {
        // Dibuja la línea horizontal actual
        objGrafico.setColor(Color.BLACK);
        objGrafico.drawLine(x1, y1, x2, y2);

        if (n >= 1) {
            int alturaActual = ALTURA_TRIANGULO;
            
            // Triángulo izquierdo
            dibujarTriangulo(x1, y1, alturaActual, true);
            
            // Triángulo derecho
            dibujarTriangulo(x2, y2, alturaActual, true);
            
            // Para N>=2, añadir triángulos recursivos
            if (n > 2) {
                // Calcula los puntos para los triángulos recursivos
                int distancia = alturaActual / 2;
                
                // Triángulos recursivos del lado izquierdo
                dibujarTriangulo(x1 - distancia, y1, alturaActual / 2, false);
                dibujarTriangulo(x1 + distancia, y1, alturaActual / 2, false);
                
                // Triángulos recursivos del lado derecho
                dibujarTriangulo(x2 - distancia, y2, alturaActual / 2, false);
                dibujarTriangulo(x2 + distancia, y2, alturaActual / 2, false);
                
                if (n > 3) {
                    int distPequeña = alturaActual / 4;
                    
                    // Triángulos más pequeños del lado izquierdo
                    dibujarTriangulo(x1 - distancia - distPequeña, y1, alturaActual / 4, false);
                    dibujarTriangulo(x1 - distancia + distPequeña, y1, alturaActual / 4, false);
                    dibujarTriangulo(x1 + distancia - distPequeña, y1, alturaActual / 4, false);
                    dibujarTriangulo(x1 + distancia + distPequeña, y1, alturaActual / 4, false);
                    
                    // Triángulos más pequeños del lado derecho
                    dibujarTriangulo(x2 - distancia - distPequeña, y2, alturaActual / 4, false);
                    dibujarTriangulo(x2 - distancia + distPequeña, y2, alturaActual / 4, false);
                    dibujarTriangulo(x2 + distancia - distPequeña, y2, alturaActual / 4, false);
                    dibujarTriangulo(x2 + distancia + distPequeña, y2, alturaActual / 4, false);
                }
            }
        }
    }
    
    private void dibujarTriangulo(int x, int y, int altura, boolean completo) {
        // Dibuja el triángulo con la punta en el medio
        objGrafico.setColor(Color.BLACK);
        
        // Triángulo superior
        objGrafico.drawLine(x, y, x, y - altura);
        objGrafico.drawLine(x, y - altura, x + altura / 2, y);
        objGrafico.drawLine(x, y - altura, x - altura / 2, y);
        
       
        }
    }

