/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ceserver;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import vistasvj.Servidor;

/**
 *
 * @author CE
 */
public class CEServer extends Observable implements Runnable {
    //    Instancias
    public CEMaps_Lists ceMaps_Lists = new CEMaps_Lists();
    
    public ArrayList<CEEchoThread> threadsWorking = new ArrayList<CEEchoThread>();
    public Map<String,String> arr_map = new HashMap<String,String>();
    public ArrayList arr_clients = new ArrayList();
    public String actMonitor = "";
    public String actOrienta = "";
    
    public String ordL = "LEFT";
    public String ordR = "RIGHT";
    
    
    private Servidor vtnSrv;
    public ServerSocket server;
    public Socket client;
    private int port;
    
    
    public Servidor getSrv() {
        return vtnSrv;
    }
    
    //  Constructor
    public CEServer(int port, Servidor svv){
        this.port = port;
        this.vtnSrv = svv;
    }
    
    //  Descomposición de la lectura
    public void splitEject(String read) {
        String[] arr = read.split(":");
        switch(arr[0]){
            case "next":{
                String[] ary = ((String)arr[1]).split("_");
                if (((String)ary[0] == this.actMonitor) || ((String)ary[0]).equals(this.actMonitor)) {
                    if (((String)ary[1] == "LEFT") || ((String)ary[1]).equals("LEFT")) {
                        this.actOrienta = this.ordR;
                    } else {
                        this.actOrienta = this.ordL;
                    }
                    // Cambiar monitor        
                    int i = this.arr_clients.indexOf(this.actMonitor);
                    if (this.arr_clients.size() > (i + 1)) {
                        i += 1;
                    } else {
                        if (0 <= (i - 1)) {
                            i -= 1;
                        }
                    }
                    String nMon = (String) this.arr_clients.get(i).toString();
                    if (this.arr_clients.contains((Object) nMon)) {
                        this.actMonitor = nMon;
                    }
                }
                break;
            }
            case "prev":{
                String[] ary = ((String)arr[1]).split("_");
                if (((String)ary[0] == this.actMonitor) || ((String)ary[0]).equals(this.actMonitor)) {
                    if (((String)ary[1] == "LEFT") || ((String)ary[1]).equals("LEFT")) {
                        this.actOrienta = this.ordR;
                    } else {
                        this.actOrienta = this.ordL;
                    }
                    // Cambiar monitor        
                    int i = this.arr_clients.indexOf(this.actMonitor);
                    if (0 <= (i - 1)) {
                        i -= 1;
                    } else {
                        if (this.arr_clients.size() > (i + 1)) {
                           i += 1;
                        }   
                    }
                    String nMon = (String) this.arr_clients.get(i).toString();
                    if (this.arr_clients.contains((Object) nMon)) {
                        this.actMonitor = nMon;
                    }
                }
                break;
            }
            case "ord":{
                String[] ary = ((String)arr[1]).split("_");
                if (((String)ary[0] == this.actMonitor) || ((String)ary[0]).equals(this.actMonitor)) {
                    if (((String)ary[1] == "LEFT") || ((String)ary[1]).equals("LEFT")) {
                        this.actOrienta = this.ordL;
                    } else {
                        this.actOrienta = this.ordR;
                    }
                }
                break;
            }
        }
    }
    public String[] splitRead(String read){
        String[] arr = read.split(":");
        switch(arr[0]){
            //  Conectar cliente
            case "conn":{
                this.clientConnect((String)arr[1]);
                break;
            }
            case "QUIT":{
                this.ClientDisconnect((String)arr[1]);
                break;
            }
        }
        return arr;
    }
    
    //  Conectar cliente
    public void clientConnect(String client_info){
        String[] arr = client_info.split("\\|");
        String key = arr[0];
        String data = arr[1]+","+arr[2];
        if(!ceMaps_Lists.arr_clients.contains(key)){
            this.arr_clients.add(key);
            this.arr_map.put(key,data);
            this.setChanged();
            this.notifyObservers("new client");
            this.clearChanged();
            
            // Indicar que aqui esta el palyer
            if ((this.actMonitor != key) && !this.actMonitor.equals(key)) {
                if ((this.actMonitor == "") || this.actMonitor.equals("")) {
                    this.actMonitor = key;
                }
            }
        }
        
        ceMaps_Lists.setArr_clients(this.arr_clients);
        ceMaps_Lists.setArr_map(this.arr_map);
        
        this.setChanged();
        this.notifyObservers("reload_clients");
        this.clearChanged();
        
    }
    
    public void ClientDisconnect(String client){
        int i = this.arr_clients.indexOf(client);
        this.arr_clients.remove(client);
        this.arr_map.remove(client);
        this.threadsWorking.remove(i);
        
        ceMaps_Lists.setArr_clients(this.arr_clients);
        ceMaps_Lists.setArr_map(this.arr_map);
        
        this.setChanged();
        this.notifyObservers("reload_clients");
        this.clearChanged();
        
        // Validar
        if (this.arr_clients.size() > 0) {
            // Bandera
            Boolean bandNV = false;
            // Calcular
            while (!bandNV) {
                // Calcular nuevo monitor
                if (i == 0) {
                    if (this.arr_clients.size() > 0) {
                        client = this.arr_clients.get(i).toString();
                    }
                    this.actOrienta = this.ordL;
                } else {
                   if ((i - 1) >= 0) {
                       i -= 1;
                       this.actOrienta = this.ordR;
                   } else if (this.arr_clients.size() > (i + 1)) {
                       i += 1;
                       this.actOrienta = this.ordL;
                   } else {
                       this.actOrienta = this.ordR;
                   }
                   client = this.arr_clients.get(i).toString();
                }
                if (this.arr_clients.contains((Object) client)) {
                    // Indicar que aqui debera estar el palyer
                    if ((this.actMonitor != client) && !this.actMonitor.equals(client)) {
                        bandNV = true;
                        this.actMonitor = client;
                        this.vtnSrv.notificarA_Monitores();
                    }
                }
            }
        }
    }
    
    public void broadcast(String str){
        for (CEEchoThread thread : this.threadsWorking) {
            thread.newmsg(str);
        }
    }

    @Override
    public void run() {
        try{
            // Crear punto
            server = new ServerSocket(port);
            // Server always listening
            while(true){
                client = server.accept();
                CEEchoThread et = new CEEchoThread(client,this);
                et.newmsg(vtnSrv.getOrdenActual());
                et.start();
                this.threadsWorking.add(et);
            }
        } catch (IOException ex) {
            switch(ex.getMessage()){
                case "Connection refused (Connection refused)": {
                    break;
                }
            }
            System.out.println("server: "+ex.getMessage());
        }
    }
}
