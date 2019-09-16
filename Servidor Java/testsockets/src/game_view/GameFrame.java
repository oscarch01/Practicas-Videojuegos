package game_view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author oscar
 */
public class GameFrame extends javax.swing.JFrame  implements KeyListener  {
    MarioPanel gm;
    private int moveX = 5; //Move n pixels


    public GameFrame(MarioPanel gm) {
        this.gm = gm;
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(800, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void keyTyped(KeyEvent e) {
        //Empty method but needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("ALGO PUCHASTE");
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(moveX>0){
                moveX = moveX * -1;
            }
            if(gm.getImgPos() == 0 || gm.getImgPos() == 3){
                gm.setImgPos(2); 
            }else{
                gm.setImgPos(3); 
            }
            if(gm.getMX()>=5){
             gm.setMX(gm.getMX()+moveX); //Change position of mario 
            }
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(moveX<0){
                moveX = moveX * -1;
            }  
            if(gm.getImgPos() == 0){
                gm.setImgPos(1); 
            }else{
                gm.setImgPos(0); 
            }
            if(gm.getMX()<=750){
                gm.setMX(gm.getMX()+moveX); //Change position of mario
            }
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("ALGO SOLTASTE");
        gm.setImgPos(0); 
        this.repaint();
    }
    
    public void startMario(){
        add(this.gm);
        addKeyListener(this);
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GamePanel gp = new GamePanel();
                MarioPanel mp = new MarioPanel();
                GameFrame gf = new GameFrame(mp);
                gf.setContentPane(gp);
                gf.startMario();
                gf.setVisible(true);
            }
        });
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
