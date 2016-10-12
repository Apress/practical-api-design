package org.apidesign.anagram.app.registergeneral;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Registry {
    
    private Registry() {
    }

    // BEGIN: anagram.registry
    private static Map<Class<?>,Object[]> instances = 
        new LinkedHashMap<Class<?>,Object[]>();
    public static void registerClass(Class<?> impl) {
        instances.put(impl, new Object[1]);
    }
    
    public static <T> T find(Class<T> whatType) {
        for (Map.Entry<Class<?>, Object[]> entry : instances.entrySet()) {
            if (whatType.isAssignableFrom(entry.getKey())) {
                if (entry.getValue()[0] == null) {
                    try {
                        entry.getValue()[0] = entry.getKey().newInstance();
                    } catch (Exception ex) {
                        throw new IllegalStateException(ex);
                    }
                }
                return whatType.cast(entry.getValue()[0]);
            }
        }
        return null;
    }
    // END: anagram.registry
}

