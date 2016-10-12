package org.apidesign.anagram.gui;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.netbeans.junit.MockServices;

public abstract class AnagramsTestBase {

    protected abstract Anagrams create();
    
    @Before
    public void cleanUpServices() {
        MockServices.setServices();
    }

    // BEGIN: anagram.lookup.mockservices
    @Test public void testInjectionOfServices() throws Exception {
        Anagrams ui = create();
        
        assertNull("No scrambler injected yet", ui.getScrambler());
        assertNull("No scrambler injected yet", ui.getWordLibrary());
        
        MockServices.setServices(
            ReversingMockScrambler.class, SingleMockLibrary.class
        );
        
        Scrambler s = ui.getScrambler();
        assertNotNull("Now we have scrambler", s);
        assertEquals(
            "It is the mock one", ReversingMockScrambler.class, s.getClass()
        );
        WordLibrary l = ui.getWordLibrary();
        assertNotNull("Now we have library", l);
        assertEquals(
            "It is the mock one", SingleMockLibrary.class, l.getClass()
        );
        
        ui.display();
        
        assertEquals(
            "The word from SingleMockLibrary is taken",
            "Hello World!", ui.getOriginalWord()
        );
        assertEquals(
            "The word is rotated using ReversingMockScrambler",
            "!dlroW olleH", ui.getScrambledWord()
        );
    }
    
    public static final class ReversingMockScrambler implements Scrambler {
        public String scramble(String word) {
            return new StringBuilder(word).reverse().toString();
        }
    }
    
    public static final class SingleMockLibrary implements WordLibrary {
        public String[] getWords() {
            return new String[] { "Hello World!" };
        }
    }
    // END: anagram.lookup.mockservices
}
