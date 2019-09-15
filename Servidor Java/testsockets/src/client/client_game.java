package client;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class client_game implements Runnable{
    public Socket client;
    private String host;
    private String msg = "";
    private String msgnew;
    private int port;
    private boolean status = true;
    
    public client_game(String host,int port, String msg){
        this.host = host;
        this.port = port;
        this.msgnew = msg;
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
            
            
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
            while(client.isConnected()){
                while(input.available()>0){
                    String read = input.readUTF();
                    System.out.println(read);            
                }            

                if(!msg.equals(msgnew)){
                    output.writeUTF(msgnew); 
                    msg = msgnew;
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
