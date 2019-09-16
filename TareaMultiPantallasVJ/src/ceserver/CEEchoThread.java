/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ceserver;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author CE
 */
public class CEEchoThread extends Thread {
    protected Socket socket;
    protected CEServer ceSrv;
    protected String msgBs = "";
    protected String msgnew = "";
    protected BufferedImage im;
    private Boolean bandConn = false;
    
    public CEEchoThread (Socket clientSocket,CEServer sg){
        this.socket = clientSocket;
        this.ceSrv = sg;
    }
    
    public void newmsg(String str){
        this.msgnew = str;
    }
    
    public void run(){
        DataInputStream input = null;
        DataOutputStream output = null;
        try{
            input = new DataInputStream(this.socket.getInputStream());
            output = new DataOutputStream(this.socket.getOutputStream());
            while(socket.isConnected()){
                // Recuperar parametros
                String recMSj = "";
                if(input.available()>0){
                    msgBs = input.readUTF();
                    recMSj = ceSrv.splitRead(msgBs)[0];
                }
                // Validar conexion
                if (!bandConn && recMSj.equals("conn")) {
                    bandConn = true;
                    msgnew = "";
                    ceSrv.broadcast(ceSrv.getSrv().getOrdenActual());
                    continue;
                }
                // Validar
                if (bandConn) {
                    // DESCONECTAR
                    if(recMSj.equals("QUIT")){
                        this.socket.close();
                    } else {
                        // EJECUTAR
                        if (input.available()>0) {
                            System.out.println("Servidor-R: " + msgBs);
                            ceSrv.splitEject(msgBs);
                        }
                        // MENSAJE
                        if(!msgBs.equals(msgnew)){
                            System.out.println("Servidor-S: " + msgnew);
                            output.writeUTF(msgnew); 
                            msgBs = msgnew;
                            output.flush();
                        }
                    }
                } else {
                    // MENSAJE
                    output.writeUTF("NoNo"); 
                    output.flush();
                }
            }
        } catch (IOException ex) {
            System.out.println("server: "+ex.getMessage());
            switch(ex.getMessage()){
                case "Connection refused (Connection refused)":{
                    break;
                }
            }
        }
    }
}
