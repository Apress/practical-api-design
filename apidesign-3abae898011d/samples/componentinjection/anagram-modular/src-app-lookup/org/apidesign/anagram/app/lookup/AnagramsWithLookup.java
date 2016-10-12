package org.apidesign.anagram.app.lookup;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;
import org.apidesign.anagram.gui.Anagrams;
import org.openide.util.Lookup;

// BEGIN: anagram.lookup.Anagrams
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
class AnagramsWithLookup extends Anagrams {

    public AnagramsWithLookup() {
    }

    @Override
    protected WordLibrary getWordLibrary() {
        return Lookup.getDefault().lookup(WordLibrary.class);
    }

    @Override
    protected Scrambler getScrambler() {
        return Lookup.getDefault().lookup(Scrambler.class);
    }
// FINISH: anagram.lookup.Anagrams
    
    private Lookup.Result<Scrambler> scramblers = Lookup.getDefault().lookupResult(Scrambler.class);
// BEGIN: anagram.lookup.Listeners
    private Lookup.Result<WordLibrary> libraries 
        = Lookup.getDefault().lookupResult(WordLibrary.class);
    private LookupListener listener = new LookupListener() {
        public void resultChanged(LookupEvent ev) {
            initWord();
        }
    };
    {
        libraries.addLookupListener(listener);
// FINISH: anagram.lookup.Listeners
        
        scramblers.addLookupListener(listener);
        // initialize the results for listening
        libraries.allItems();
        scramblers.allItems();
    }
}
