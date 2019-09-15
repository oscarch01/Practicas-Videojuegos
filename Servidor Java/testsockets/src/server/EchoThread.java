/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author oscar
 */
public class EchoThread extends Thread {
    protected Socket socket;
    protected server_game sg;
    
    public EchoThread (Socket clientSocket,server_game sg){
        this.socket = clientSocket;
        this.sg = sg;
    }
    
    public void run(){
        DataInputStream input = null;
        DataOutputStream output = null;
        try{
            input = new DataInputStream(this.socket.getInputStream());
            output = new DataOutputStream(this.socket.getOutputStream());

            String msg = "";
            while(socket.isConnected()){
                msg = input.readUTF();
                System.out.println(msg);            
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
