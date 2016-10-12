package org.apidesign.anagram.app.property;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;
import org.apidesign.anagram.gui.Anagrams;

public final class AnagramsWithProperties extends Anagrams {
    private WordLibrary wordLibrary;
    private Scrambler scrambler;
    
    public AnagramsWithProperties() {
    }

    // BEGIN: anagram.property
    @Override
    protected WordLibrary getWordLibrary() {
        try {
            if (wordLibrary == null) {
                String implName = System.getProperty(
                    "org.apidesign.anagram.api.WordLibrary"
                );
                assert implName != null;
                Class<?> impl = Class.forName(implName);
                wordLibrary = (WordLibrary)impl.newInstance();
            }
            return wordLibrary;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    protected Scrambler getScrambler() {
        try {
            if (scrambler == null) {
                String implName = System.getProperty(
                    "org.apidesign.anagram.api.Scrambler"
                );
                assert implName != null;
                Class<?> impl = Class.forName(implName);
                scrambler = (Scrambler)impl.newInstance();
            }
            return scrambler;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
    // END: anagram.property
}
