package org.apidesign.anagram.app.lookup;

import org.apidesign.anagram.api.WordLibrary;
import org.apidesign.anagram.gui.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.netbeans.junit.MockServices;

public class AnagramsWithLookupTest extends AnagramsTestBase {
    @Override
    protected Anagrams create() {
        return new AnagramsWithLookup();
    }
    
    @Test public void testChangesInTheRegistrationAreReflected() {
        Anagrams ui = create();
        
        MockServices.setServices(FirstMockWordLibrary.class);
        ui.display();
        
        assertEquals(
            "Original word has to be from FirstMockWordLibrary",
            "1st", ui.getOriginalWord()
        );
        
        MockServices.setServices(SecondMockWordLibrary.class);
        
        assertEquals(
            "Original word has to be from new library now",
            "2nd", ui.getOriginalWord()
        );
    }
    
    public static final class FirstMockWordLibrary implements WordLibrary {
        public String[] getWords() {
            return new String[] { "1st" };
        }
    }

    public static final class SecondMockWordLibrary implements WordLibrary {
        public String[] getWords() {
            return new String[] { "2nd" };
        }
    }
    
    
}
