package misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class maps_lists {
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
