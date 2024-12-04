package Capa_Logica;

import capa_presentacion.pantalla_principal;


public class Prueba_Logica {

    //Conectar la pantalla con la logica
    public static void main(String[] args) {
        //Crear una nueva pantalla
        pantalla_principal forms = new pantalla_principal();
        //A la nueva pantalla hacerla visible
        forms.setVisible(true);
        //Dar la instruccion que la pantalla se abra en el centro 
        forms.setLocationRelativeTo(null);
        
        
    }
    
}

