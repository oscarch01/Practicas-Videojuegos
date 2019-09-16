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
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import principalvj.Sprites_Player;

/**
 *
 * @author Jorge
 */
public class Panel_VJ extends JPanel {
    // Variables
    private int pixelInt = 3; // Velocidad
    private int player_pX = 0; // Posición en X
    private int player_pY = 0; // Posición en Y
    private String player_ActionS = "IDLE";
    private String player_ActionD = "LEFT";
    private String enLimite = "";
    
    // Variables Players
    private int positionActPY = 0;
    private Sprites_Player spPlayer = null;
    private Sprites_Player[] playersSPs = null;
    
    // Variables BGs
    private Image[] imgsToBG;
    private BufferedImage[] imgsBufer;
    private int positionActBG = 0;
    private Image imgToBG_Actual = null;
    private String nickName = "";
    
    // Constructor
    public Panel_VJ(String nk, int w, int h, String[] escenarios, Sprites_Player[] players) {
        super();
        // Ajuste de canvas
        this.nickName = nk;
        this.setSize(w, h);
        // ----------------------------------------------
        // Cargar componentes
        // Cargar componente inicial
        this.positionActBG = 0;
        // Armar arreglos de imagenes
        this.imgsToBG = new Image[escenarios.length];
        this.imgsBufer = new BufferedImage[escenarios.length];
        // Recorrer escenarios
        Dimension dim = this.getSize();
        for (int i = 0; i < escenarios.length; i++) {
            String nomBG = escenarios[i];
            BufferedImage imgB = cargarImagenB(nomBG);
            if (imgB != null) {
                this.imgsBufer[i] = imgB;
                this.imgsToBG[i] = cargarImagenM(imgB, dim);
            }
        }
        this.imgToBG_Actual = imgsToBG[0];
        // ----------------------------------------------
        this.playersSPs = players;
        // Cargar componentes
        this.positionActPY = 0;
        this.spPlayer = players[0];
        // ----------------------------------------------
        // Establecer secuencia de sprite inicial
        this.spPlayer.setSpritesRow(0);
        // Calcular posición inicial
        Dimension dimension = this.getSize();
        this.player_pX = dimension.width/2 - this.spPlayer.getW()/2;
        this.player_pY = dimension.height - (this.spPlayer.getH() + (this.spPlayer.getH()/6));
    }
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // Función para cargar imagen
    // Cargar Buffer
    private BufferedImage cargarImagenB(String file) {
        BufferedImage imgB = null;
        try{
            imgB = ImageIO.read(new File(file));
        }catch(Exception e){
            e.printStackTrace();
        }
        return imgB;
    }
    // Convertir Buffer en Imagen
    private Image cargarImagenM(BufferedImage bfImg, Dimension dim) {
        Image imgM = null;
        try{
            //  = this.getSize();
            if (dim == null) {
                imgM = new ImageIcon(bfImg).getImage();
            } else {
                imgM = bfImg.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return imgM;
    }
    
    // Funcion para cambiar a la siguiente imagen disponible como fondo
    public void setImgToBG(int nPi) {
        // Validar cambio
        if (this.positionActBG != nPi) {
            // Recuperar el número maximo de elementos
            int tam = this.imgsToBG.length;
            // Validar cual es la posición siguiente
            if ((nPi > 0) && (nPi < tam)) {
                this.positionActBG = nPi;
            } else {
                this.positionActBG = 0;
            }
            this.imgToBG_Actual = this.imgsToBG[this.positionActBG];
        }
    }
    // Funcion para cambiar al siguiente Personaje
    public void setImgToPY(int nPi) {
        // Validar cambio
        if (this.positionActPY != nPi) {
            // Recuperar el número maximo de elementos
            int tam = this.playersSPs.length;
            // Validar cual es la posición siguiente
            if ((nPi > 0) && (nPi < tam)) {
                this.positionActPY = nPi;
            } else {
                this.positionActPY = 0;
            }
            this.spPlayer = this.playersSPs[this.positionActPY];
        }
    }
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // Función que pinta
    @Override
    public void paint(Graphics g) {
        // Validar
        if (isClientOK) {
            // Pintar fondo
            g.drawImage(this.imgToBG_Actual, 0, 0, this);
            // Validar
            if (isMyTurn) {
                // Seleccionar acción del player
                switch (this.player_ActionS) {
                    case "IDLE":
                        this.spPlayer.setSpritesRow(0);
                        break;
                    case "RUN":
                        this.spPlayer.setSpritesRow(1);
                        break;
                }
                // Recuperar sprite del player y hacerlo transparente
                BufferedImage playerSprite = this.spPlayer.getActualSprite();
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
        }
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
        // Validar
        if (isMyTurn) {
            this.player_ActionS = "RUN";
            this.player_ActionD = "RIGHT";
            // Recuperar dimensiones
            Dimension dimension = this.getSize();
            int maxInX = dimension.width - this.spPlayer.getW();
            if (maxInX > (this.player_pX + pixelInt)) {
                this.player_pX = this.player_pX + pixelInt;
                if (this.enLimite == "LEFT") {
                    this.enLimite = "";
                }
            } else {
                this.player_pX = maxInX;
                this.enLimite = "RIGHT";
            }
        } else {
            this.player_cancelMov();
        }
    }    
    // Función para mover player hacia la izquierda
    public void player_movToLeft(){
        // Validar
        if (isMyTurn) {
            this.player_ActionS = "RUN";
            this.player_ActionD = "LEFT";
            // Recuperar dimensiones
            if (0 < (this.player_pX - pixelInt)) {
                this.player_pX = this.player_pX - pixelInt;
                if (this.enLimite == "RIGHT") {
                    this.enLimite = "";
                }
            } else {
                this.player_pX = 0;
                this.enLimite = "LEFT";
            }
        } else {
            this.player_cancelMov();
        }
    }
    
    // Función para cancelar movimiento del player
    public void player_cancelMov(){
        this.player_ActionS = "IDLE";
        this.enLimite = "";
    }
    
    public String getInLimit() {
        return this.enLimite;
    }
    
    public String getActionD() {
        return this.player_ActionD;
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    // Invocar desde hilo de servidorCliente
    
    // Establecer posición
    public void setNuevaPos(String orient, String actualNK) {
        if ((this.nickName == actualNK) || this.nickName.equals(actualNK)) {
            if (isChange) {
                isChange = false;
                // Validar
                if ((orient == "LEFT") || orient.equals("LEFT")) {
                    this.player_pX = (this.spPlayer.getW()*2);
                } else {
                    Dimension dimension = this.getSize();
                    int maxInX = dimension.width - (this.spPlayer.getW()*2);
                    this.player_pX = maxInX;
                }
            }
        } else {
            isChange = true;
        }
    }
    
    // Indicar estado
    public void setEstatus(Boolean isCOK, Boolean isMTurn, String orient) {
        this.isClientOK = isCOK;
        this.isMyTurn = isMTurn;
        // Validar
        if ((orient == "LEFT") || orient.equals("LEFT")) {
            //this.player_movToRight();
        } else {
            //this.player_movToLeft();
        }
    }
    
    private Boolean isClientOK = false;
    private Boolean isMyTurn = false;
    private Boolean isChange = false;
}