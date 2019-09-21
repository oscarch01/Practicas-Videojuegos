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
    private BufferedImage sample;
    
    public MarioPanel() {
        files[0] = "res/mario1.png";
        files[1] = "res/mario2.png";
        files[2] = "res/mario3.png";
        files[3] = "res/mario4.png";
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
        g.drawImage(sample, mx, my, 50, 50, this);
    }
    
    private BufferedImage cargarImagen(int pos){
        BufferedImage img = null;
        try{
            File image = new File(files[pos]);
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

    public void setImgPos(int imgPos) {
        this.imgPos = imgPos;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
