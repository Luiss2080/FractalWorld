package capa_presentacion;

import Capa_Logica.geometria.Circulo;
import Capa_Logica.geometria.Segmento;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JPanel;

/**
 * Panel que conserva las figuras a dibujar y las pinta en {@link #paintComponent},
 * de modo que el fractal sobrevive a redimensionar, minimizar o tapar la ventana.
 * Todos los métodos públicos deben llamarse desde el hilo de eventos (EDT).
 */
public class PanelFractal extends JPanel {

    private List<Segmento> segmentos = List.of();
    private List<Circulo> circulos = List.of();
    private Rectangle2D cuadrado;

    public void mostrar(List<Segmento> nuevosSegmentos, List<Circulo> nuevosCirculos) {
        this.segmentos = List.copyOf(nuevosSegmentos);
        this.circulos = List.copyOf(nuevosCirculos);
        this.cuadrado = null;
        repaint();
    }

    public void mostrarCuadrado(Rectangle2D nuevoCuadrado) {
        this.cuadrado = nuevoCuadrado;
        repaint();
    }

    public void limpiar() {
        this.segmentos = List.of();
        this.circulos = List.of();
        this.cuadrado = null;
        repaint();
    }

    public int cantidadFiguras() {
        return segmentos.size() + circulos.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            for (Segmento s : segmentos) {
                g2.draw(new Line2D.Double(s.x1(), s.y1(), s.x2(), s.y2()));
            }
            for (Circulo c : circulos) {
                double r = c.diametro() / 2;
                g2.draw(new Ellipse2D.Double(c.cx() - r, c.cy() - r, c.diametro(), c.diametro()));
            }
            if (cuadrado != null) {
                g2.draw(cuadrado);
            }
        } finally {
            g2.dispose();
        }
    }
}
