package Capa_Logica;

import Capa_Logica.geometria.Fractal;
import Capa_Logica.geometria.GeneradorFractales;
import capa_presentacion.PanelFractal;
import java.util.List;

/**
 * Curva de Levy C. Calcula la geometria con {@link GeneradorFractales} y se la entrega al
 * panel, que la pinta en su paintComponent (persiste al redibujar la ventana).
 */
public class Clase_Graficos4 {
    private final PanelFractal panel;

    public Clase_Graficos4(PanelFractal panel) {
        this.panel = panel;
    }

    /** @throws IllegalArgumentException si el nivel esta fuera del rango de Fractal.LEVY_C. */
    public void graficarCurvaLevyC(int n) {
        panel.mostrar(GeneradorFractales.curvaLevyC(n, panel.getWidth(), panel.getHeight()), List.of());
    }
}
