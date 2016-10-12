package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Factory to create various types of lookup instances.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 1.0
 */
public final class Lookups {
    private Lookups() {
    }
    
    public static Lookup fixed(final Object... instances) {
        return new Lookup() {
            @Override
            public <T> T lookup(Class<T> clazz) {
                for (Object obj : instances) {
                    if (clazz.isInstance(obj)) {
                        return clazz.cast(obj);
                    }
                }
                return null;
            }

            @Override
            public <T> Collection<? extends T> lookupAll(Class<T> clazz) {
                List<T> result = new ArrayList<T>();
                for (Object obj : instances) {
                    if (clazz.isInstance(obj)) {
                        result.add(clazz.cast(obj));
                    }
                }
                return result;
            }

            @Override
            public <T> Set<Class<? extends T>> lookupAllClasses(Class<T> clazz) {
                Set<Class<? extends T>> result = new HashSet<Class<? extends T>>();
                for (Object obj : instances) {
                    if (clazz.isInstance(obj)) {
                        result.add(obj.getClass().asSubclass(clazz));
                    }
                }
                return result;
            }
        };
    }
}
