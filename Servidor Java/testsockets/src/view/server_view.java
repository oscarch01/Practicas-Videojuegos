package view;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import misc.maps_lists;
import server.server_game;

public class server_view extends javax.swing.JFrame implements Observer {
    public server_game server;
    public Thread t_server;
    
    public server_view() {
        initComponents();
        
        try {
            this.server_ip.setText(this.getIP());
        } catch (UnknownHostException ex) {
            Logger.getLogger(server_view.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //  Get Server IP
    private String getIP() throws UnknownHostException{
        InetAddress ip=InetAddress.getLocalHost();

        return ip.getHostAddress();
    }
    
    
    
    //  Start socket server
    public void start_server(int port){
        server = new server_game(port);
        server.addObserver(this);
        this.t_server = new Thread(server);
        this.t_server.start();
    }
    //  Stop socket action
    public void stop_server() throws IOException{
        this.server.server.close();
    }
    
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        server_ip = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        n_clientes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_clients = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_port = new javax.swing.JTextField();
        btn_port = new javax.swing.JButton();
        btn_server = new javax.swing.JButton();
        btn_main = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Host Game");

        jLabel2.setText("IP del servidor: ");

        server_ip.setText("0.0.0.0");

        jLabel3.setText("Clientes conectados:");

        n_clientes.setText("0");

        tbl_clients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nick", "IP/Address", "Port"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_clients);

        jLabel4.setText("IP del servidor: ");

        btn_port.setText("Asignar");
        btn_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_portActionPerformed(evt);
            }
        });

        btn_server.setText("Iniciar");
        btn_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_serverActionPerformed(evt);
            }
        });

        btn_main.setText("Menú principal");
        btn_main.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mainActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(server_ip, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                .addComponent(n_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_port)))
                        .addGap(0, 62, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_main)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_server)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(server_ip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_port))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(n_clientes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_server)
                    .addComponent(btn_main))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_portActionPerformed
        if(btn_port.getText()=="Asignar"){
            btn_port.setText("Cambiar");
            txt_port.disable();
        }
        else if(btn_port.getText()=="Cambiar"){
            btn_port.setText("Asignar");
            txt_port.enable();
            txt_port.requestFocus();
        }
    }//GEN-LAST:event_btn_portActionPerformed

    private void btn_serverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_serverActionPerformed
        if(btn_server.getText()=="Iniciar"){
            if(txt_port.getText()!=null){
                btn_server.setText("Detener");
                txt_port.disable();
                this.start_server(Integer.parseInt(txt_port.getText()));
            }
            txt_port.requestFocus();
        }
        else if(btn_server.getText()=="Detener"){
            btn_server.setText("Iniciar");
            try {
                this.stop_server();
            } catch (IOException ex) {
                Logger.getLogger(server_view.class.getName()).log(Level.SEVERE, null, ex);
            }
            txt_port.enable();            
        }
    }//GEN-LAST:event_btn_serverActionPerformed

    private void btn_mainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mainActionPerformed
        main_view mv = new main_view();
        mv.setVisible(true);
//        this.setVisible(false);
//        this.dispose();
    }//GEN-LAST:event_btn_mainActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(server_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server_view().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_main;
    private javax.swing.JButton btn_port;
    private javax.swing.JButton btn_server;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel n_clientes;
    private javax.swing.JLabel server_ip;
    private javax.swing.JTable tbl_clients;
    private javax.swing.JTextField txt_port;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        switch((String)arg){
            case "reload_clients":{
                maps_lists ml = new maps_lists();
                System.out.println("PINTAR LA TABLA CON LOS USUARIOS");
                ArrayList  arr_clients = ml.getArr_clients();
                Map<String, String> arr_map = ml.getArr_map();
                
                this.repaintTable(arr_clients, arr_map);
                break;
            }
        }
        
    }
    
    
    private void repaintTable(ArrayList  cl,Map<String, String> mp){
        DefaultTableModel model = (DefaultTableModel)this.tbl_clients.getModel();
        //        Limpiar tabla
        int r_c = model.getRowCount();
        for (int i = r_c - 1; i >= 0; i--) {model.removeRow(i);}
        
        
        for(int i=0;i<cl.size();i++){
            Object[] obj = new Object[3];
            obj[0] = cl.get(i);
            
            String[] data = mp.get(cl.get(i)).split(",");
            obj[1] = data[0];
            obj[2] = data[1];
            
            model.addRow(obj);
        }
    }
}
