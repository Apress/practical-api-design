package org.apidesign.javabeans.toomany;

import java.util.TooManyListenersException;

public interface HighlightsContainer {        
// BEGIN: javabeans.with.HighlightsContainer.TooMany
  public void addHighlightsChangeListener(HighlightsChangeListener l) 
      throws TooManyListenersException;
// END: javabeans.with.HighlightsContainer.TooMany
  public HighlightsSequence getHighlights(int startOffset, int endOffset);
  public void removeHighlightsChangeListener(HighlightsChangeListener l);
}
