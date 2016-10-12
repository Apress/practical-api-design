package api.request.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Support {
    private Support() {
    }
    
    public static int searchByName(String name, Compute provider) {
        Map<String,String> result = new LinkedHashMap<String,String>();
        Compute.Request request = new Compute.Request();
        Compute.Response response = new Compute.Response(result);
        
        provider.computeData(request, response);
        
        Iterator<String> it = result.keySet().iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (name.equals(it.next())) {
                return i;
            }
        }
        return -1;
    }
    
    public static int searchByDescription(String name, Compute provider) {
        Map<String,String> result = new LinkedHashMap<String,String>();
        Compute.Request request = new Compute.Request();
        Compute.Response response = new Compute.Response(result);
        
        provider.computeData(request, response);
        
        Iterator<String> it = result.values().iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (name.equals(it.next())) {
                return i;
            }
        }
        return -1;
    }
}
