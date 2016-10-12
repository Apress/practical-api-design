package org.apidesign.runtime.check;

import java.util.Map;

public final class RuntimeCheck {
    private RuntimeCheck() {
    }
    
    public static boolean requiresAtLeast(String minVersion, String apiName, ClassLoader caller) {
        if (caller instanceof AwareLoader) {
            String realVersion = ((AwareLoader)caller).requestedVersion(apiName);
            if (realVersion != null) {
                double minV = Double.parseDouble(minVersion);
                double realV = Double.parseDouble(realVersion);
                return minV <= realV;
            }
        }
        return false;
    }
    
    public static interface AwareLoader {
        public String requestedVersion(String apiName);
    }
    
    
    
    public static ClassLoader create(ClassLoader wrap, Map<String,String> requiredVersion) {
        return new VersionAwareClassLoader(wrap, requiredVersion);
    }
}
