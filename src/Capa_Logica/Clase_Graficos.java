package Capa_Logica;

import Capa_Logica.geometria.Fractal;
import Capa_Logica.geometria.GeneradorFractales;
import capa_presentacion.PanelFractal;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Copo de nieve de Koch y un cuadrado que puede desplazarse. La geometria del copo
 * la calcula {@link GeneradorFractales}; el panel la pinta en su paintComponent.
 */
public class Clase_Graficos {
    private static final int LADO_CUADRADO = 50;
    private static final int PASO = 5;

    private final PanelFractal panel;
    private int x = 100;
    private final int y = 100;

    public Clase_Graficos(PanelFractal panel) {
        this.panel = panel;
    }

    private void dibujarCuadrado() {
        panel.mostrarCuadrado(new Rectangle2D.Double(x, y, LADO_CUADRADO, LADO_CUADRADO));
    }

    public void moverDerecha() {
        x += PASO;
        dibujarCuadrado();
    }

    public void moverIzquierda() {
        x -= PASO;
        dibujarCuadrado();
    }

    /** @throws IllegalArgumentException si el nivel esta fuera del rango de Fractal.COPO_DE_NIEVE. */
    public void graficarCopoNieve(int n) {
        panel.mostrar(GeneradorFractales.copoDeNieve(n), List.of());
    }
}
