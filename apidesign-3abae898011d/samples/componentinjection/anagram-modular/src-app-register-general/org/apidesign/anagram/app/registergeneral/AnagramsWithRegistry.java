package org.apidesign.anagram.app.registergeneral;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;
import org.apidesign.anagram.gui.Anagrams;

class AnagramsWithRegistry extends Anagrams {

    public AnagramsWithRegistry() {
    }

    @Override
    protected WordLibrary getWordLibrary() {
        return Registry.find(WordLibrary.class);
    }

    @Override
    protected Scrambler getScrambler() {
        return Registry.find(Scrambler.class);
    }

}
