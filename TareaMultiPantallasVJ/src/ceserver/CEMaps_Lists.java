/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ceserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author CE
 */
public class CEMaps_Lists {
    // Variables
    public static Map<String,String> arr_map = new HashMap<String,String>();
    public static ArrayList arr_clients = new ArrayList();
    
    //    GETTERS
    public Map<String, String> getArr_map() {
        return arr_map;
    }

    public ArrayList getArr_clients() {
        return arr_clients;
    }
    
    //    SETTERS
    public void setArr_map(Map<String, String> arr_map) {
        this.arr_map = arr_map;
    }

    public void setArr_clients(ArrayList arr_clients) {
        this.arr_clients = arr_clients;
    }
}
