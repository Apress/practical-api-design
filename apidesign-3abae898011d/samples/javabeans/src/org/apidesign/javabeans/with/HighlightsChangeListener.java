package org.apidesign.javabeans.with;

import java.util.EventListener;

public interface HighlightsChangeListener extends EventListener {
    public void highlightChanged(HighlightsChangeEvent ev);
}
