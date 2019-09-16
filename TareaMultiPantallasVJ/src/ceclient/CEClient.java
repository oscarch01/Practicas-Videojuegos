/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ceclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import vistasvj.VideoJuego;

/**
 *
 * @author CE
 */
public class CEClient implements Runnable {
    
    // Variables
    public Socket client;
    private int port;
    private String host;
    private String msg = "";
    private String msgnew = "";
    private String s_msgnew = "";
    private Boolean s_msjSend = false;
    private boolean status = true;
    private VideoJuego vtnVJ;
    
    public String myNickMon = "";
    public String actNickinS = "";
    
    public CEClient(String nk, String host,int port, String msg, VideoJuego cv){
        this.myNickMon = nk;
        this.host = host;
        this.port = port;
        this.msgnew = msg;
        this.s_msgnew = msg;
        this.vtnVJ = cv;
    }
    
    // Metodos
    
    public String getActualNKinS () {
        return actNickinS;
    }
    
    public void end_connection(){
        status = false;
    }
    
    public void send_message(String msg){
        if ((s_msgnew != "") && !s_msgnew.equals("")) {
            this.msgnew = this.s_msgnew;
        } else {
            this.msgnew = msg;
        }
        
    }

    // Hilo
    @Override
    public void run() {
        try {
            client = new Socket(host,port);
            DataInputStream input = null;
            DataOutputStream output = null;
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
            while(client.isConnected()){
                if (!s_msjSend && (s_msgnew != "") && !s_msgnew.equals("")) {
                    System.out.println("Cliente----" + s_msgnew);
                    output.writeUTF(s_msgnew); 
                    s_msjSend = true;
                    output.flush();
                } else {
                    if(input.available()>0){
                        String read = input.readUTF();
                        // Validar
                        if (((read == "NoNo") || read.equals("NoNo")) ||
                            ((read == "") || read.equals(""))) {
                            s_msjSend = false;
                            continue;
                        } else {
                            s_msgnew = "";
                            // Mensaje
                            System.out.println("Cliente-R: " + read);
                            String[] arr = read.split("_");
                            // Variables
                            String t_i1 = "";
                            String t_i2 = "";
                            String t_nK = "";
                            String t_oR = "";
                            // Validar
                            if (arr.length >= 4) {
                                t_i1 = arr[0];
                                t_i2 = arr[1];
                                t_nK = arr[2];
                                t_oR = arr[3];
                            } else if (arr.length >= 3) {
                                t_i1 = arr[0];
                                t_i2 = arr[1];
                                t_nK = arr[2];
                                t_oR = "";
                            } else {
                                t_i1 = "0";
                                t_i2 = "0";
                                t_nK = "";
                                t_oR = "";
                            }
                            
                            int iBG = Integer.parseInt(t_i1);
                            int iPY = Integer.parseInt(t_i2);

                            this.vtnVJ.getPanelVJ().setImgToBG(iBG);
                            this.vtnVJ.getPanelVJ().setImgToPY(iPY);

                            this.vtnVJ.getPanelVJ().setNuevaPos(t_oR, t_nK);
                            this.actNickinS = t_nK;

                            if (this.actNickinS.equals(this.myNickMon) || this.actNickinS == this.myNickMon) {
                                this.vtnVJ.getPanelVJ().setEstatus(true, true, t_oR);
                            } else {
                                this.vtnVJ.getPanelVJ().setEstatus(true, false, t_oR);
                            }
                        }
                    } else {
                        if ((s_msgnew != "") && !s_msgnew.equals("")) {
                            continue;
                        }
                    }
                    if(!msg.equals(msgnew)){
                        System.out.println("Cliente-S: " + msgnew);
                        output.writeUTF(msgnew); 
                        msg = msgnew;
                        output.flush();
                    }
                    if(!status){
                        client.close();
                    }
                }
            }
        } catch (IOException ex) {
            switch(ex.getMessage()){
                case "Connection refused (Connection refused)":{
                    break;
                }
            }
            System.out.println("client: "+ex.getMessage());
        }
    }
}
