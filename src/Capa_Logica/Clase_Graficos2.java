package Capa_Logica;

import Capa_Logica.geometria.Fractal;
import Capa_Logica.geometria.GeneradorFractales;
import capa_presentacion.PanelFractal;
import java.util.List;

/**
 * Arbol binario. Calcula la geometria con {@link GeneradorFractales} y se la entrega al
 * panel, que la pinta en su paintComponent (persiste al redibujar la ventana).
 */
public class Clase_Graficos2 {
    private final PanelFractal panel;

    public Clase_Graficos2(PanelFractal panel) {
        this.panel = panel;
    }

    /** @throws IllegalArgumentException si el nivel esta fuera del rango de Fractal.ARBOL. */
    public void graficarFractal(int n) {
        panel.mostrar(GeneradorFractales.arbol(n, panel.getWidth(), panel.getHeight()), List.of());
    }
}
