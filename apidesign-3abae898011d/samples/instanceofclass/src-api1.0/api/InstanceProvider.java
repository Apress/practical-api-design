
package api;

// BEGIN: instanceof.class.InstanceProvider1

import java.util.concurrent.Callable;

public final class InstanceProvider {
    private final Callable<Object> instance;

    public InstanceProvider(Callable<Object> instance) {
        this.instance = instance;
    }
    
    public Class<?> instanceClass() throws Exception {
        return instance.call().getClass();
    }
    public Object instanceCreate() throws Exception {
        return instance.call();
    }
}
// END: instanceof.class.InstanceProvider1
