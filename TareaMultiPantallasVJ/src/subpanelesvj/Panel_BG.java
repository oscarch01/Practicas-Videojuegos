/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subpanelesvj;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Jorge
 */
public class Panel_BG extends JPanel{
    // Variables
    private Image[] imgsToBG;
    private String[] strImBd64;
    private BufferedImage[] imgsBufer;
    private int positionActBG = 0;
    private Image imgToBG_Actual = null;
    
    // Constructor
    public Panel_BG(int w, int h, String[] escenarios) {
        super();
        // Ajuste de canvas
        this.setSize(w, h);
        // Cargar componente inicial
        this.positionActBG = 0;
        // Armar arreglos de imagenes
        this.imgsToBG = new Image[escenarios.length];
        this.strImBd64 = new String[escenarios.length];
        this.imgsBufer = new BufferedImage[escenarios.length];
        // Recorrer escenarios
        Dimension dim = this.getSize();
        for (int i = 0; i < escenarios.length; i++) {
            String nomBG = escenarios[i];
            BufferedImage imgB = cargarImagenB(nomBG);
            if (imgB != null) {
                this.imgsBufer[i] = imgB;
                this.strImBd64[i] = cargarImagenS(imgB);
                this.imgsToBG[i] = cargarImagenM(imgB, dim);
            }
        }
        this.imgToBG_Actual = imgsToBG[0];
    }
    
    // Recuperar el valor Base64 de la imagen actual
    public String getActualImgAsString() {
        return this.strImBd64[this.positionActBG];
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
    // Convertir Buffer en String
    private String cargarImagenS(BufferedImage bfImg) {
        String strImg = "";
        // Variables
        BufferedImage myBI = bfImg;
        BASE64Encoder encoder = new BASE64Encoder();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Seguro
        try {
            // Conversión
            ImageIO.write(myBI, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            strImg = encoder.encode(imageBytes);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strImg;
    }
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // ----------------------------------------------------------
    // Funcion para cambiar a la siguiente imagen disponible como fondo
    public void nextImgToBG() {
        // Recuperar el número maximo de elementos
        int tam = this.imgsToBG.length;
        // Validar cual es la posición siguiente
        if ((this.positionActBG + 1) < tam) {
            this.positionActBG += 1;
        } else {
            this.positionActBG = 0;
        }
        this.imgToBG_Actual = this.imgsToBG[this.positionActBG];
    }
    
    // Funcion para cambiar a la previa imagen disponible como fondo
    public void prevImgToBG() {
        // Recuperar el número maximo de elementos
        int tam = this.imgsToBG.length - 1;
        // Validar cual es la posición anterior
        if ((this.positionActBG - 1) >= 0) {
            this.positionActBG -= 1;
        } else {
            this.positionActBG = tam;
        }
        this.imgToBG_Actual = this.imgsToBG[this.positionActBG];
    }
    
    // Función que pinta
    @Override
    public void paint(Graphics g) {
        // Pintar fondo
        g.drawImage(this.imgToBG_Actual, 0, 0, this);
    }
    
    // Función que actualiza
    @Override
    public void update(Graphics g) {
        super.update(g);
        this.paint(g);
    }
}
