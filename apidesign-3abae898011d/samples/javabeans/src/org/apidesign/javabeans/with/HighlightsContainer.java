package org.apidesign.javabeans.with;

// BEGIN: javabeans.with.HighlightsContainer
public interface HighlightsContainer {        
  public void addHighlightsChangeListener(HighlightsChangeListener l);
  public HighlightsSequence getHighlights(int start, int end);
  public void removeHighlightsChangeListener(HighlightsChangeListener l);
}
// END: javabeans.with.HighlightsContainer
