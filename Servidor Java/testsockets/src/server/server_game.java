/*
    Basado en: https://www.youtube.com/watch?v=DUSGrlNyK7g
*/

package server;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import misc.maps_lists;




public class server_game extends Observable implements Runnable {
    //    Instancias
    public maps_lists ml = new maps_lists();
    
    public Map<String,String> arr_map = new HashMap<String,String>();
    public ArrayList arr_clients = new ArrayList();
    
    public ServerSocket server;
    public Socket client;
    private int port;
    
    
    
    
    //  Constructor
    public server_game(int port){
        this.port = port;
    }
    
    
    
    //  Descomposición de la lectura
    private void splitRead(String read){
        String[] arr = read.split(":");
        
        switch(arr[0]){
            //  Conectar cliente
            case "conn":{
                this.clientConnect((String)arr[1]);
                break;
            }
        }
    }
    
    
    
    //  Conectar cliente
    private void clientConnect(String client_info){
        String[] arr = client_info.split("\\|");
        String key = arr[0];
        String data = arr[1]+","+arr[2];
        
        
        if(!ml.arr_clients.contains(key)){
            this.arr_clients.add(key);
            this.arr_map.put(key,data);
            this.setChanged();
            this.notifyObservers("new client");
            this.clearChanged();
        }
        
        ml.setArr_clients(this.arr_clients);
        ml.setArr_map(this.arr_map);
            
        this.setChanged();
        this.notifyObservers("reload_clients");
        this.clearChanged();
    }
    
    
    

    @Override
    public void run() {
        DataInputStream input = null;
        DataOutputStream output = null;
        
        try{
            server = new ServerSocket(port);
            System.out.println("Server started");
        
            // Server always listening
            while(true){
                client = server.accept();
                System.out.println("Client connected");

                input = new DataInputStream(client.getInputStream());
                output = new DataOutputStream(client.getOutputStream());
                
         

                String msg = input.readUTF();
                System.out.println(msg);
                this.splitRead(msg);

                
                //  Set notify to send to view
                this.setChanged();
                this.notifyObservers(msg);
                this.clearChanged();
                

                client.close();
                
                System.out.println("Client disconected");
            }
        } 
        catch (IOException ex) {
            switch(ex.getMessage()){
                case "Connection refused (Connection refused)":{
                    
                    break;
                }
            }
            System.out.println("server: "+ex.getMessage());
//            Logger.getLogger(server_game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
