package Capa_Logica.geometria;

/** Segmento de recta en coordenadas de panel (píxeles). */
public record Segmento(double x1, double y1, double x2, double y2) {

    public double longitud() {
        return Math.hypot(x2 - x1, y2 - y1);
    }
}
