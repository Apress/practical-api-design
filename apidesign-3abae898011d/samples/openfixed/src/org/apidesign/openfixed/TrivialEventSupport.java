/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apidesign.openfixed;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class TrivialEventSupport implements EventSupport {
    List<ModificationListener> listener = new CopyOnWriteArrayList<ModificationListener>();
    
    TrivialEventSupport() {
    }

    // BEGIN: openfixed.trivial
    @Override
    public void fireModificationEvent(ModificationEvent ev) {
        for (ModificationListener l : listener) {
            l.modification(ev);
        }
    }
    // END: openfixed.trivial

    @Override
    public void add(ModificationListener l) {
        listener.add(l);
    }

    @Override
    public void remove(ModificationListener l) {
        listener.remove(l);
    }
    
}
