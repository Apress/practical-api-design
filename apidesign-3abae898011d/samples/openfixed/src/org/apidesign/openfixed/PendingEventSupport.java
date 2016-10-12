package org.apidesign.openfixed;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class PendingEventSupport implements EventSupport, Runnable {
    private final List<ModificationListener> listeners = new CopyOnWriteArrayList<ModificationListener>();
    private final List<Deliverable> deliverables = new CopyOnWriteArrayList<Deliverable>();
    private static final Executor EXEC = Executors.newSingleThreadExecutor();
    
    PendingEventSupport() {
    }

    @Override
    public void fireModificationEvent(ModificationEvent ev) {
        synchronized (deliverables) {
            final Deliverable d = new Deliverable(ev, listeners.toArray(new ModificationListener[0]));
            deliverables.add(d);
            EXEC.execute(this);
        }
    }

    @Override
    public void add(ModificationListener l) {
        listeners.add(l);
    }

    @Override
    public void remove(ModificationListener l) {
        listeners.remove(l);
    }

    @Override
    public void run() {
        Deliverable[] pending;
        synchronized (deliverables) {
            if (deliverables.isEmpty()) {
                return;
            }
            pending = deliverables.toArray(new Deliverable[0]);
            deliverables.clear();
        }
        // BEGIN: openfixed.pendingCount
        int pendingCount = pending.length;
        for (Deliverable d : pending) {
            d.ev.pending = --pendingCount;
            for (ModificationListener l : d.listeners) {
                l.modification(d.ev);
            }
        }
        // END: openfixed.pendingCount
    }
    
    private static class Deliverable {
        final ModificationEvent ev;
        final ModificationListener[] listeners;

        public Deliverable(ModificationEvent ev, ModificationListener[] listeners) {
            this.ev = ev;
            this.listeners = listeners;
        }
    }
}
