package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/** Factory to create various types of lookup instances.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 2.0
 */
public final class Lookups {
    private Lookups() {
    }
    
    public static Lookup fixed(final Object... instances) {
        return new Lookup() {
            @Override
            <T> Iterator<T> doLookup(Class<T> clazz) {
                ArrayList<T> result = new ArrayList<T>();
                for (Object obj : instances) {
                    if (clazz.isInstance(obj)) {
                        result.add(clazz.cast(obj));
                    }
                }
                return result.iterator();
            }
        };
    }
    
    public static Lookup dynamic(final Dynamic provider) {
        return new Lookup() {
            @Override
            <T> Iterator<T> doLookup(Class<T> clazz) {
                ArrayList<T> result = new ArrayList<T>();
                provider.computeInstances(clazz, result);
                return result.iterator();
            }
        };
    }
    
    public interface Dynamic {
        public <T> void computeInstances(Class<T> requestedType, Collection<? super T> addInstancesTo);
    }
}
