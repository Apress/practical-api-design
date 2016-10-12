package org.apidesign.javamonitorflaws;

import java.util.HashMap;
import java.util.Map;

/** Classical caching support class that makes sure there is
 * always one "To" value for each "From" one returned from the {@link #get}
 * method. However it does not prevent multiple threads to call
 * {@link #createItem} multiple times for the same "From" value.
 * <p>
 * In contrast to {@link Cache}, this is correctly synchronized.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: monitor.pitfalls.CacheOK
public abstract class CacheOK<From,To> {
    private final Object LOCK = new Object();

    private Map<From,To> cache;

    protected abstract To createItem(From f);

    public final To get(From f) {
        To t = inspectValue(f);
        if (t != null) {
            return t;
        }
        To newT = createItem(f);
        return registerValue(f, newT);
    }


    private To inspectValue(From f) {
        synchronized (LOCK) {
            if (cache == null) {
                cache = new HashMap<From, To>();
            }
            return cache.get(f);
        }
    }

    private To registerValue(From f, To newT) {
        synchronized (LOCK) {
            To t = cache.get(f);
            if (t == null) {
                cache.put(f, newT);
                return newT;
            } else {
                return t;
            }
        }
    }
}
// END: monitor.pitfalls.CacheOK
