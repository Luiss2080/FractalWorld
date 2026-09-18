package Capa_Logica;

import capa_presentacion.pantalla_principal;
import javax.swing.SwingUtilities;

public class Prueba_Logica {

    /** Punto de entrada: crea la pantalla principal en el hilo de eventos de Swing. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            pantalla_principal forms = new pantalla_principal();
            forms.setLocationRelativeTo(null);
            forms.setVisible(true);
        });
    }
}
