/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principalvj;

/**
 *
 * @author Jorge
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MiVentana ventana = new MiVentana(800, 600);
        ventana.setTitle("VideoJuego");
        ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setVisible(true);
        ventana.setFocusable(true);
    }
}
