package org.apidesign.javabeans.toomany;

import java.util.EventListener;

public interface HighlightsChangeListener extends EventListener {
    public void highlightChanged(HighlightsChangeEvent ev);
}
