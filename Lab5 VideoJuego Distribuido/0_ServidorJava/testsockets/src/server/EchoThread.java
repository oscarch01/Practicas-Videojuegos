/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author oscar
 */
public class EchoThread extends Thread {
    protected Socket socket;
    protected server_game sg;
    protected String msg = "";
    protected String msgnew = "";
    protected String tobytes = "";
    protected BufferedImage im;
    protected boolean imgsend = false;
    
    public EchoThread (Socket clientSocket,server_game sg){
        this.socket = clientSocket;
        this.sg = sg;
    }
    
    public void newmsg(String str){
        this.msgnew = str;
    }
    
    public void tobytes(String str){
        this.tobytes = str;
    }
    
    public void imgsend(BufferedImage im){
        this.im = im;
        imgsend = true;
    }
    
    public void run(){
        DataInputStream input = null;
        DataOutputStream output = null;
        try{
            input = new DataInputStream(this.socket.getInputStream());
            output = new DataOutputStream(this.socket.getOutputStream());

            while(socket.isConnected()){
                if(!msg.equals(msgnew)){
                    output.writeUTF(msgnew); 
                    msg = msgnew;
                    output.flush();
                }
                if(!tobytes.equals("")){
                    output.write(tobytes.getBytes()); 
                    tobytes = "";
                    output.flush();
                }
                if(imgsend){
                    ImageIO.write(im, "png", output);
                    imgsend = false;
                    output.flush();
                }
                if(input.available()>0){
                    msg = input.readUTF();
                    System.out.println(msg);                
                }
                if(sg.splitRead(msg)[0].equals("QUIT")){
                    this.socket.close();
                    System.out.println("Client disconected");
                }
            }
        } 
        catch (IOException ex) {
            System.out.println("server: "+ex.getMessage());
            switch(ex.getMessage()){
                case "Connection refused (Connection refused)":{
                    
                    break;
                }
            }
//            Logger.getLogger(server_game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
