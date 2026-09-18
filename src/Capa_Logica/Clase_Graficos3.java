package Capa_Logica;

import Capa_Logica.geometria.Fractal;
import Capa_Logica.geometria.GeneradorFractales;
import capa_presentacion.PanelFractal;
import java.util.List;

/**
 * Triangulo de Sierpinski. Calcula la geometria con {@link GeneradorFractales} y se la entrega al
 * panel, que la pinta en su paintComponent (persiste al redibujar la ventana).
 */
public class Clase_Graficos3 {
    private final PanelFractal panel;

    public Clase_Graficos3(PanelFractal panel) {
        this.panel = panel;
    }

    /** @throws IllegalArgumentException si el nivel esta fuera del rango de Fractal.SIERPINSKI. */
    public void graficarFractalSierpinski(int n) {
        panel.mostrar(GeneradorFractales.sierpinski(n), List.of());
    }
}
