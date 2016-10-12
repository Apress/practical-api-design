package org.apidesign.openfixed;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class PostEventSupport implements EventSupport, Runnable {
    private final List<ModificationListener> listeners = new CopyOnWriteArrayList<ModificationListener>();
    private final List<Deliverable> deliverables = new CopyOnWriteArrayList<Deliverable>();
    private static final Executor EXEC = Executors.newSingleThreadExecutor();
    
    PostEventSupport() {
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
        // BEGIN: openfixed.postimpl
        Calculator calc = null;
        Set<PostModificationListener> notify;
        notify = new HashSet<PostModificationListener>();
        int pendingCount = pending.length;
        for (Deliverable d : pending) {
            calc = (Calculator)d.ev.getSource();
            d.ev.pending = --pendingCount;
            d.ev.posts = notify;
            for (ModificationListener l : d.listeners) {
                l.modification(d.ev);
            }
            d.ev.posts = null;
        }
        for (PostModificationListener pml : notify) {
            pml.postProcess(new PostModificationEvent(calc));
        }
        // END: openfixed.postimpl
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
