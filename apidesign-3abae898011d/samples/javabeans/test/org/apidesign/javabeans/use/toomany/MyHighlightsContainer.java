package org.apidesign.javabeans.use.toomany;

import org.apidesign.javabeans.toomany.*;
import java.util.TooManyListenersException;

// BEGIN: javabeans.with.MyHighlightsContainer
final class MyHighlightsContainer implements HighlightsContainer {
    private HighlightsChangeListener listener;

    public synchronized void addHighlightsChangeListener(
        HighlightsChangeListener l
    ) throws TooManyListenersException {
        if (listener != null) throw new TooManyListenersException();
        listener = l;
    }
    public synchronized void removeHighlightsChangeListener(
        HighlightsChangeListener l
    ) {
        if (listener == l) listener = null;
    }
    public HighlightsSequence getHighlights(int start, int end) {
        return null; // implement
    }
}
// END: javabeans.with.MyHighlightsContainer
