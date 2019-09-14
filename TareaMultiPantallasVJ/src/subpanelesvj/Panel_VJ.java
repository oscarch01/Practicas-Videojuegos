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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import principalvj.Sprites_String;

/**
 *
 * @author Jorge
 */
public class Panel_VJ extends JPanel {
    // Variables
    private Image imgToBG;
    private Sprites_String player;
    private int pixelInt = 3; // Velocidad
    private int player_pX = 0; // Posición en X
    private int player_pY = 0; // Posición en Y
    private String player_ActionS = "IDLE";
    private String player_ActionD = "LEFT";
    
    // Función para definir player
    public void setStringPlayer(String strSprites) {
        this.player = new Sprites_String(strSprites,
                150l, // Idle - Right
                100l // Walk - Right
            );
    }
    
    // Función para definir Background
    public void setStringBG(String strImgBG) {
        this.imgToBG = cargarImagen(strImgBG);
    }
    
    // Constructor
    public Panel_VJ(int w, int h, String strImgBG, String strSprites) {
        super();
        // Ajuste de canvas
        this.setSize(w, h);
        // Cargar componentes
        this.setStringBG(strImgBG);
        this.setStringPlayer(strSprites);
        // Establecer secuencia de sprite inicial
        this.player.setSpritesRow(0);
        // Calcular posición inicial
        Dimension dimension = this.getSize();
        this.player_pX = dimension.width/2 - this.player.getW()/2;
        this.player_pY = dimension.height - (this.player.getH() * 2);
    }
    
    // Función para cargar imagen
    private Image cargarImagen(String file) {
        Image myImg = null;
        try{
            Dimension dimension = this.getSize();
            BufferedImage buferImg = ImageIO.read(new File(file));
            myImg = buferImg.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
        }catch(Exception e){
            e.printStackTrace();
        }
         return myImg;
    }
    
    // Funcion para el obtener la imagen que se es utilizando de fondo
    public Image getImgToBG() {
        return this.imgToBG;
    }
    
    // Función que pinta
    @Override
    public void paint(Graphics g) {
        // Pintar fondo
        g.drawImage(this.imgToBG, 0, 0, this);
        // Seleccionar acción del player
        switch (this.player_ActionS) {
            case "IDLE":
                this.player.setSpritesRow(0);
                break;
            case "RUN":
                this.player.setSpritesRow(2);
                break;
        }
        // Recuperar sprite del player y hacerlo transparente
        BufferedImage playerSprite = this.player.getActualSprite();
        // Establecer dirección u orientación
        if (this.player_ActionD.equals("LEFT")) {
            AffineTransform transforX = AffineTransform.getScaleInstance(-1, 1);
            transforX.translate(-playerSprite.getWidth(null), 0);
            AffineTransformOp transforOP = new AffineTransformOp(transforX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            playerSprite = transforOP.filter(playerSprite, null);
        }
        // Ajuste de Sprite
        int playerSpriteIntColor = playerSprite.getRGB(0, 0);
        Image playerImg = this.makeBGTransparentImg(
                playerSprite,
                new Color(playerSpriteIntColor)
        );
        // Pintas sprite del player
        g.drawImage(playerImg, this.player_pX, this.player_pY, this);
    }
    
    // Función que actualiza
    @Override
    public void update(Graphics g) {
        super.update(g);
        this.paint(g);
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
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    // Función para mover player hacia la derecha
    public void player_movToRight(){
        this.player_ActionS = "RUN";
        this.player_ActionD = "RIGHT";
        // Recuperar dimensiones
        Dimension dimension = this.getSize();
        int maxInX = dimension.width - this.player.getW();
        if (maxInX > (this.player_pX + pixelInt)) {
            this.player_pX = this.player_pX + pixelInt;
        } else {
            this.player_pX = maxInX;
        }
    }    
    // Función para mover player hacia la izquierda
    public void player_movToLeft(){
        this.player_ActionS = "RUN";
        this.player_ActionD = "LEFT";
        // Recuperar dimensiones
        if (0 < (this.player_pX - pixelInt)) {
            this.player_pX = this.player_pX - pixelInt;
        } else {
            this.player_pX = 0;
        }
    }
    
    // Función para cancelar movimiento del player
    public void player_cancelMov(){
        this.player_ActionS = "IDLE";
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
}