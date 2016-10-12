
package api;

// BEGIN: instanceof.class.InstanceProvider2

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public final class InstanceProvider {
    private final Callable<Object> instance;
    private final Set<String> types;

    public InstanceProvider(Callable<Object> instance) {
        this.instance = instance;
        this.types = null;
    }
    /** Specifies not only a factory for creating objects, but
     * also additional information about them.
     * @param instance the factory to create the object
     * @param type the class that the create object will be instance of
     * @since 2.0 
     */
    public InstanceProvider(Callable<Object> instance, String... types) {
        this.instance = instance;
        this.types = new HashSet<String>();
        this.types.addAll(Arrays.asList(types));
    }
    
    public Class<?> instanceClass() throws Exception {
        return instance.call().getClass();
    }
    public Object instanceCreate() throws Exception {
        return instance.call();
    }
    
    /** Allows to find out if the InstanceProvider creates object of given
     * type. This check can be done without loading the actual object or
     * its implementation class into memory.
     * 
     * @param c class to test 
     * @return if the instances produced by this provider is instance of c
     * @since 2.0 
     */
    public boolean isInstanceOf(Class<?> c) throws Exception {
        if (types != null) {
            return types.contains(c.getName());
        } else {
            // fallback
            return c.isAssignableFrom(instanceClass());
        }
    }

    
}
// END: instanceof.class.InstanceProvider2
