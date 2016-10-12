package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** Simplified version of NetBeans 
 * <a href="http://bits.netbeans.org/6.0/javadoc/org-openide-util/org/openide/util/Lookup.html">Lookup</a>
 * reimplemented to separate the API for clients
 * from the API for implementators while guaranteeing
 * consistency among all there methods.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 2.0
 */
// BEGIN: design.consistency.2.0
public abstract class Lookup {
    /** only for classes in the same package */
    Lookup() {
    }
    
    // BEGIN: design.consistency.lookup.2.0
    public <T> T lookup(Class<T> clazz) {
        Iterator<T> it = doLookup(clazz);
        return it.hasNext() ? it.next() : null;
    }
    // END: design.consistency.lookup.2.0

    // BEGIN: design.consistency.lookupAll.2.0
    public <T> Collection<? extends T> lookupAll(Class<T> clazz) {
        Iterator<T> it = doLookup(clazz);
        if (!it.hasNext()) {
            return Collections.emptyList();
        } else {
            List<T> result = new ArrayList<T>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            return result;
        }
    }
    // END: design.consistency.lookupAll.2.0

    // BEGIN: design.consistency.lookupAllClasses.2.0
    public <T> Set<Class<? extends T>> lookupAllClasses(Class<T> clazz) {
        Iterator<T> it = doLookup(clazz);
        if (!it.hasNext()) {
            return Collections.emptySet();
        } else {
            Set<Class<? extends T>> result = 
                new HashSet<Class<? extends T>>();
            while (it.hasNext()) {
                result.add(it.next().getClass().asSubclass(clazz));
            }
            return result;
        }
    }
    // END: design.consistency.lookupAllClasses.2.0
// FINISH: design.consistency.2.0
    
    abstract <T> Iterator<T> doLookup(Class<T> clazz);
}
