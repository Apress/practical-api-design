package org.apidesign.anagram.app.serviceloader;

import java.util.Iterator;
import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;
import org.apidesign.anagram.gui.Anagrams;
import java.util.ServiceLoader;

// BEGIN: anagram.serviceloader.Anagrams
class AnagramsWithServiceLoader extends Anagrams {

    public AnagramsWithServiceLoader() {
    }

    @Override
    protected WordLibrary getWordLibrary() {
        Iterator<WordLibrary> it;
        it = ServiceLoader.load(WordLibrary.class).iterator();
        return it.hasNext() ? it.next() : null;
    }

    @Override
    protected Scrambler getScrambler() {
        Iterator<Scrambler> it;
        it = ServiceLoader.load(Scrambler.class).iterator();
        return it.hasNext() ? it.next() : null;
    }

}
// END: anagram.serviceloader.Anagrams
