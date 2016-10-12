package org.apidesign.javabeans.with;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHighlightsContainer implements HighlightsContainer {
    private List<HighlightsChangeListener> listeners = new ArrayList<HighlightsChangeListener>();

    protected AbstractHighlightsContainer() {
    }

    public abstract HighlightsSequence getHighlights(int startOffset, int endOffset);

    public final void addHighlightsChangeListener(HighlightsChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public final void removeHighlightsChangeListener(HighlightsChangeListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected final void fireHighlightsChange(int changeStartOffset, int changeEndOffset) {
        List<HighlightsChangeListener> targets;

        synchronized (listeners) {
            targets = new ArrayList<HighlightsChangeListener>(listeners);
        }

        if (targets.size() > 0) {
            HighlightsChangeEvent evt = new HighlightsChangeEvent(this, changeStartOffset, changeEndOffset);

            for (HighlightsChangeListener l : targets) {
                l.highlightChanged(evt);
            }
        }
    }
}
