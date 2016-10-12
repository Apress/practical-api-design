package org.apidesign.runtime.check;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

final class VersionAwareClassLoader extends ClassLoader 
implements RuntimeCheck.AwareLoader {
    
    private final Map<String,String> requestedVersions;

    public VersionAwareClassLoader(ClassLoader parent, Map<String,String> requestedVersions) {
        super(parent);
        this.requestedVersions = requestedVersions;
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.endsWith("Test") && name.startsWith("api.")) {
            try {
                InputStream is = getResourceAsStream(name.replace('.', '/').concat(".class"));
                byte[] arr = new byte[is.available()];
                int read = is.read(arr);
                assert read >= 0;
                is.close();
                return defineClass(name, arr, 0, read);
            } catch (IOException ex) {
                throw new ClassNotFoundException("Cannot load " + name, ex);
            }
        }
        
        return super.loadClass(name, resolve);
    }

    public String requestedVersion(String apiName) {
        return requestedVersions.get(apiName);
    }
    
    
}
