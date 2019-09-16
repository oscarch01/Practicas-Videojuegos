package client;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import view.client_view;


public class client_game implements Runnable{
    public Socket client;
    private String host;
    private String msg = "";
    private String msgnew;
    private int port;
    private boolean status = true;
    private client_view cv;
    
    public client_game(String host,int port, String msg,client_view cv){
        this.host = host;
        this.port = port;
        this.msgnew = msg;
        this.cv = cv;
    }
    
    public void end_connection(){
        status = false;
    }
    
    public void send_message(String msg){
        this.msgnew = msg;
    }

    @Override
    public void run() {
        try {
            client = new Socket(host,port);
            DataInputStream input = null;
            DataOutputStream output = null;
            
            int i;
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
            while(client.isConnected()){
                if(input.available()>0){
                    String read = input.readUTF();
                    System.out.println(read);
                    if(read.equals("SENDIMG")){
                        i = 0;
                        System.out.println("entering img recive");
                        BufferedImage image = ImageIO.read(input);
                        File outputfl = new File("testing"+i+".png");
                        i++;
                        ImageIO.write(image, "png", outputfl);
                        System.out.println("img recived");                             
                    }else{
                        cv.splitRead(read);
                    }
                }            

                if(!msg.equals(msgnew)){
                    output.writeUTF(msgnew); 
                    msg = msgnew;
                    output.flush();
                }

                if(!status){
                    client.close();
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
