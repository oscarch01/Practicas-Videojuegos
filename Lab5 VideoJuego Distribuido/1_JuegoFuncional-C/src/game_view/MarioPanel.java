package game_view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author oscar
 */
public class MarioPanel extends javax.swing.JPanel{
    private int mx = 10;
    private int my = 375;
    private int imgPos = 0;
    private String[] files = new String[4];
    private String[] filesr = new String[4];
    private BufferedImage sample;
    private int conf = 0;
    private int r = 0;
    
    public MarioPanel(int conf) {
        switch(conf){
            case 0:
                files[0] = "__imagenes/Player_1/idle/stand000.png";
                files[1] = "__imagenes/Player_1/Walk/walkFront000.png";
                files[2] = "__imagenes/Player_1/Walk/walkFront001.png";
                files[3] = "__imagenes/Player_1/Walk/walkFront002.png";
                
                filesr[0] = "__imagenes/Player_1/idle/stand000.png";
                filesr[1] = "__imagenes/Player_1/Walk_r/walkFront000.png";
                filesr[2] = "__imagenes/Player_1/Walk_r/walkFront001.png";
                filesr[3] = "__imagenes/Player_1/Walk_r/walkFront002.png";
                break;
            case 1:
                files[0] = "__imagenes/Player_2/idle/stand000.png";
                files[1] = "__imagenes/Player_2/Walk/walkFront000.png";
                files[2] = "__imagenes/Player_2/Walk/walkFront001.png";
                files[3] = "__imagenes/Player_2/Walk/walkFront002.png";
                
                filesr[0] = "__imagenes/Player_2/idle/stand000.png";
                filesr[1] = "__imagenes/Player_2/Walk_r/walkFront000.png";
                filesr[2] = "__imagenes/Player_2/Walk_r/walkFront001.png";
                filesr[3] = "__imagenes/Player_2/Walk_r/walkFront002.png";
                break;
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(800, 500));
        setSize(new java.awt.Dimension(800, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g){       
        super.paintComponent(g);
        // Put the mario in Panel
        sample = cargarImagen(imgPos);
        g.drawImage(sample, mx, my, 32, 60, this);
    }
    
    private BufferedImage cargarImagen(int pos){
        BufferedImage img = null;
        try{
            File image = new File(files[pos]);
            switch(this.r){
                case 0:
                    image = new File(files[pos]);
                    break;
                case 1:
                    image = new File(filesr[pos]);
                    break;
            }
            img = ImageIO.read(image);
        }catch(Exception e){
        }
         return img;
    }
    
    public int getMX() {
        return mx;
    }

    public void setMX(int mx) {
        this.mx = mx;
    }
    
    public int getImgPos() {
        return imgPos;
    }

    public void setImgPos(int imgPos,int r) {
        this.imgPos = imgPos;
        this.r = r;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
