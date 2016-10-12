package org.apidesign.javabeans.without;

// BEGIN: javabeans.without
public interface HighlightsContainer {        
    public void initialize(Callback callback);
    public HighlightsSequence getHighlights(int start, int end);

    public static final class Callback {
        Callback() { /* only for the infastructure */ }

        public final void highlightsChanged() {
            // refresh everything
        }
    }
}
// END: javabeans.without
