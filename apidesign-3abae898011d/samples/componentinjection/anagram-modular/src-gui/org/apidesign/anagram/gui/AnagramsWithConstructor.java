package org.apidesign.anagram.gui;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;

// BEGIN: anagram.ui.init
public class AnagramsWithConstructor extends Anagrams {
    
    private final WordLibrary library;
    private final Scrambler scrambler;

    public AnagramsWithConstructor(
        WordLibrary library, Scrambler scrambler
    ) {
        this.library = library;
        this.scrambler = scrambler;
    }

    @Override
    protected WordLibrary getWordLibrary() {
        return library;
    }

    @Override
    protected Scrambler getScrambler() {
        return scrambler;
    }
// FINISH: anagram.ui.init

    @Override
    public void display() {
        setVisible(true);
    }
}
