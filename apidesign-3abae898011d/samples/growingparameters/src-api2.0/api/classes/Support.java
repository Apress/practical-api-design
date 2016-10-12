package api.classes;

import java.util.Iterator;
import java.util.Map.Entry;

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

    /** @since 2.0 */
    public static int searchByDescription(String name, Compute provider) {
        Iterator<Entry<String, String>> it = provider.getDataAndDescription().entrySet().iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (name.equals(it.next().getValue())) {
                return i;
            }
        }
        return -1;
    }
    
}
