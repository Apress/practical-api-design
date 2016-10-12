
package api;

// BEGIN: instanceof.BetterInstanceProvider
public interface BetterInstanceProvider extends InstanceProvider {
    public boolean isInstanceOf(Class<?> c);
}
// END: instanceof.BetterInstanceProvider
