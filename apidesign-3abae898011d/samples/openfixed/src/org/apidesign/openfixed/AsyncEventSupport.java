package org.apidesign.openfixed;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class AsyncEventSupport implements EventSupport {
    private final List<ModificationListener> listeners = new CopyOnWriteArrayList<ModificationListener>();
    
    AsyncEventSupport() {
    }
    @Override
    public void add(ModificationListener l) {
        listeners.add(l);
    }

    @Override
    public void remove(ModificationListener l) {
        listeners.remove(l);
    }

    // BEGIN: openfixed.asynch
    private static final Executor EXEC = Executors.newSingleThreadExecutor();
    @Override
    public void fireModificationEvent(ModificationEvent ev) {
        EXEC.execute(new Deliverable(
            ev, listeners.toArray(new ModificationListener[0])
        ));
    }
    
    private static class Deliverable implements Runnable {
        final ModificationEvent ev;
        final ModificationListener[] listeners;

        public Deliverable(
            ModificationEvent ev, ModificationListener[] listeners
        ) {
            this.ev = ev;
            this.listeners = listeners;
        }

        @Override
        public void run() {
            for (ModificationListener l : listeners) {
                l.modification(ev);
            }
        }
    }
    // END: openfixed.asynch
}
