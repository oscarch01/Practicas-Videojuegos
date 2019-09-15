/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principalvj;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import vistasvj.VideoJuego;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import vistasvj.Principal;
import vistasvj.Cliente;
import vistasvj.Servidor;

/**
 *
 * @author Jorge
 */
public class MiVentana extends JFrame{
    // Paneles
    private Principal jpPrincipal = null;
    private Cliente jpCliente = null;
    private Servidor jpServidor = null;
    private VideoJuego jpJuego = null;
    
    // Constructor
    public MiVentana(int w, int h) {
        // Ajustes de ventana
        this.pack();
        this.setSize(w, h);
        // Crear panel principal
        this.jpPrincipal = new Principal(this);
        this.getContentPane().add(this.jpPrincipal);
        // Definir evento Key
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Movimiento
                if (jpJuego != null) {
                    int keyC = e.getKeyCode();
                    if ((keyC == 37) || (keyC == 39)) {
                        // Actualizar codigo de tecla precionado
                        jpJuego.setKeyCodePressed(keyC);
                        // Actualizar canvas
                        jpJuego.windowsPanelUpdate();
                    }
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                // Movimiento
                if (jpJuego != null) {
                    int keyC = e.getKeyCode();
                    if ((keyC == 37) || (keyC == 39)) {
                        // Actualizar codigo de tecla precionado
                        jpJuego.setKeyCodePressed(0);
                        // Actualizar canvas
                        jpJuego.windowsPanelUpdate();
                    }
                }
            }
        });
    }
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // Funciones para cambiar de panel
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // Muestra panel Cliente
    public void setClientView() { 
        // Remueve todos los componentes
        this.getContentPane().removeAll();
        // Crear panel cliente si no existe
        if (this.jpCliente == null) {
            this.jpCliente = new Cliente(this);
        }
        // Agregar panel a la ventana y exponerlo
        this.getContentPane().add(this.jpCliente);
        this.revalidate();
        this.pack();
    }
    // Muestra panel Servidor
    public void setServerView() {
        // Remueve todos los componentes
        this.getContentPane().removeAll();
        // Crear panel servidor si no existe
        if (this.jpServidor == null) {
            this.jpServidor = new Servidor(this);
        }
        // Agregar panel a la ventana y exponerlo
        this.getContentPane().add(this.jpServidor);
        this.revalidate();
        this.pack();
    }
    // Muestra panel Principal
    public void setPrincipalView() {
        // Remueve todos los componentes
        this.getContentPane().removeAll();
        // Instala una vista principal
        this.getContentPane().add(this.jpPrincipal);
        this.revalidate();
        this.pack();
    }
    // Muestra panel de Juego
    public void startGameView(String host, String nick, int c_port, int h_port) {
        // Prepara parametros
        String nickName = "";
        try {
            nickName = "conn:"+nick+"|"+this.getIP()+"|"+h_port;
        } catch (UnknownHostException ex) {
            // Logger.getLogger(client_view.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Remueve todos los componentes
        this.getContentPane().removeAll();
        // Crear panel servidor si no existe
        if (this.jpJuego == null) {
            this.jpJuego = new VideoJuego(this);
        }
        // Agregar panel a la ventana y exponerlo
        this.getContentPane().add(this.jpJuego);
        this.setLocationRelativeTo(null);
        this.revalidate();
        this.pack(); 
   }
    // Términar con el panel de Juego
    public void endGameView() {
        // Remueve todos los componentes
        this.setClientView();
    }
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // Función para recuperar dirección IP
    private String getIP() throws UnknownHostException{
        InetAddress ip=InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}