package org.apidesign.anagram.app.springscan;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.WordLibrary;
import org.apidesign.anagram.gui.AnagramsWithConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* This class shall be in its own module, not here, but because of the need
 * to see the @Service annotation, I've put it here. The right solution would
 * be to add dependency of the module providing super class on spring and
 * use the annotation directly there.
 */

// BEGIN: anagram.spring.autowire.Anagrams
@Service("ui")
public class AnagramsAnnotated extends AnagramsWithConstructor {
    @Autowired
    public AnagramsAnnotated(WordLibrary library, Scrambler scrambler) {
        super(library, scrambler);
    }
}
// END: anagram.spring.autowire.Anagrams
