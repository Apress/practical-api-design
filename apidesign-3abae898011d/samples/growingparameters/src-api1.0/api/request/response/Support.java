package api.request.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Support {
    private Support() {
    }
    
    public static int searchByName(String name, Compute provider) {
        List<String> result = new ArrayList<String>();
        Compute.Request request = new Compute.Request();
        Compute.Response response = new Compute.Response(result);
        
        provider.computeData(request, response);
        
        Iterator<String> it = result.iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (name.equals(it.next())) {
                return i;
            }
        }
        return -1;
    }
    
}
