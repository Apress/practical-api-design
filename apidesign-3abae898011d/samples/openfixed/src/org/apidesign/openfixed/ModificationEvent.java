package org.apidesign.openfixed;

import java.util.Collection;
import java.util.EventObject;

// BEGIN: openfixed.event
public final class ModificationEvent extends EventObject {
    private final int delta;
    ModificationEvent(Object source, int delta) {
        super(source);
        this.delta = delta;
    }
    
    public int getChange() {
        return delta;
    }
    
// FINISH: openfixed.event
    
// BEGIN: openfixed.addgetter    
    int pending;
    /** @since 2.0 */
    public int getPending() {
        return pending;
    }
// END: openfixed.addgetter    
    
// BEGIN: openfixed.mount
    Collection<PostModificationListener> posts;
    /** @since 3.0 */
    public void postProcess(PostModificationListener p) {
        posts.add(p);
    }
// END: openfixed.mount  
}
