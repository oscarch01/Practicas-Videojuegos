/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subpanelesvj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import javax.swing.JPanel;
import principalvj.Sprites_Player;
/**
 *
 * @author Jorge
 */
public class Panel_PY extends JPanel{
    // Variables
    int x = 0, y = 0;
    private Image imgToBG;
    private int positionActBG = 0;
    private String[] strPlayers = null;
    private Sprites_Player spPlayer = null;
    private Sprites_Player[] playersBG = null;
    
    // Constructor
    public Panel_PY(int w, int h, Sprites_Player[] players) {
        super();
        // Ajuste de canvas
        this.setSize(w, h);
        this.playersBG = players;
        // Cargar componentes
        this.positionActBG = 0;
        this.spPlayer = players[0];
        strPlayers = new String[players.length];
        // Recorrer players
        for (int i = 0; i < players.length; i++) {
            Sprites_Player sppp = players[i];
            strPlayers[i] = sppp.getAllSpritesToString();
        }
        // Calcular X y Y
        Dimension dim = this.getSize();
        this.x = dim.width/2;
        this.y = dim.height/2;
        // Establecer secuencia de sprite inicial
        this.imgToBG = cargarImagen(this.spPlayer);
        this.x = this.x - this.imgToBG.getWidth(null)/2;
        this.y = this.y - this.imgToBG.getHeight(null)/2;
    }
    
    // Función para cargar imagen
    private Image cargarImagen(Sprites_Player playerSS) {
        Image myImg = null;
        try{
            // Recuperar sprite del player y hacerlo transparente
            BufferedImage buferImg = playerSS.getActualSprite();
            int playerSpriteIntColor = buferImg.getRGB(0, 0);
            myImg = this.makeBGTransparentImg(
                    buferImg,
                    new Color(playerSpriteIntColor)
            );
            // Dimension dim = this.getSize();
            // myImg = myImg.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH);
        }catch(Exception e){
            e.printStackTrace();
        }
         return myImg;
    }
    
    // Función para hacer fondo de imagen transparente
    public Image makeBGTransparentImg(BufferedImage img, Color color) {
        ImageFilter filtroRGB = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer imgP = new FilteredImageSource(img.getSource(), filtroRGB);
        return Toolkit.getDefaultToolkit().createImage(imgP);
    }
    
    // Funcion para el obtener el objeto Sprites_Player actualmente seleccionado
    public String getActualImgAsString() {
        return this.strPlayers[this.positionActBG];
    }
    
    // Funcion para cambiar al siguiente objeto Sprites_Player
    public void nextImgsPlayer() {
        // Tomar número maximo de elementos
        int tam = this.playersBG.length;
        // Validar cual es el siguiente
        if ((this.positionActBG + 1) < tam) {
            this.positionActBG += 1;
        } else {
            this.positionActBG = 0;
        }
        this.spPlayer = this.playersBG[this.positionActBG];
    }
    
    // Funcion para cambiar al anterior objeto Sprites_Player
    public void prevImgsPlayer() {
        // Tomar número maximo de elementos
        int tam = this.playersBG.length - 1;
        // Validar cual es el siguiente
        if ((this.positionActBG - 1) >= 0) {
            this.positionActBG -= 1;
        } else {
            this.positionActBG = tam;
        }
        this.spPlayer = this.playersBG[this.positionActBG];
    }
    
    // Función que pinta
    @Override
    public void paint(Graphics g) {
        // Cargar Sprite actual
        this.imgToBG = cargarImagen(this.spPlayer);
        // Pintar imagen
        g.drawImage(this.imgToBG, this.x, this.y, this);
    }
    
    // Función que actualiza
    @Override
    public void update(Graphics g) {
        super.update(g);
        this.paint(g);
    }
}
