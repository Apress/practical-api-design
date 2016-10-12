package api.classes;

import java.util.Iterator;
import java.util.List;

public class Support {
    private Support() {
    }
    
    public static int searchByName(String name, Compute provider) {
        Iterator<String> it = provider.getData().iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (name.equals(it.next())) {
                return i;
            }
        }
        return -1;
    }
    
}
