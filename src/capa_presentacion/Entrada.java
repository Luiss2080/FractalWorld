package capa_presentacion;

import Capa_Logica.geometria.Fractal;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/** Lectura validada de los campos de la interfaz. */
final class Entrada {

    private Entrada() {
    }

    /**
     * Lee el nivel del campo. Si el texto no es un entero dentro del rango del
     * fractal, muestra un aviso y devuelve {@code null}.
     */
    static Integer leerNivel(Component padre, JTextField campo, Fractal fractal) {
        try {
            return fractal.parsearNivel(campo.getText());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(padre, e.getMessage(), "Valor N no válido", JOptionPane.WARNING_MESSAGE);
            campo.requestFocusInWindow();
            campo.selectAll();
            return null;
        }
    }
}
