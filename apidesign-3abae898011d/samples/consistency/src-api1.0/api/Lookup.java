package api;

import java.util.Collection;
import java.util.Set;

/** Simplified version of NetBeans 
 * <a href="http://bits.netbeans.org/6.0/javadoc/org-openide-util/org/openide/util/Lookup.html">Lookup</a> class.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @version 1.0
 */
// BEGIN: design.consistency.1.0
public abstract class Lookup {
    // BEGIN: design.consistency.lookup.1.0
    public abstract <T> T lookup(Class<T> clazz);
    // END: design.consistency.lookup.1.0

    // BEGIN: design.consistency.lookupAll.1.0
    public abstract <T> Collection<? extends T> lookupAll(Class<T> clazz);
    // END: design.consistency.lookupAll.1.0

    // BEGIN: design.consistency.lookupAllClasses.1.0
    public abstract <T> Set<Class<? extends T>> lookupAllClasses(
        Class<T> clazz
    );
    // END: design.consistency.lookupAllClasses.1.0
}
// END: design.consistency.1.0
