
package api;

// BEGIN: instanceof.InstanceProvider
public interface InstanceProvider {
    public Class<?> instanceClass() throws Exception;
    public Object instanceCreate() throws Exception;
}
// END: instanceof.InstanceProvider
