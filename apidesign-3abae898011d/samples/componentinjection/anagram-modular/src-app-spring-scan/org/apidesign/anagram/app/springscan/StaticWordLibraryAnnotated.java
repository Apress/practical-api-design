package org.apidesign.anagram.app.springscan;

import org.apidesign.anagram.wordstatic.StaticWordLibrary;
import org.springframework.stereotype.Service;

/* This class shall be in its own module, not here, but because of the need
 * to see the @Service annotation, I've put it here. The right solution would
 * be to add dependency of the module providing super class on spring and
 * use the annotation directly there.
 */


// BEGIN: anagram.spring.autowire.WordLibrary
@Service
public class StaticWordLibraryAnnotated extends StaticWordLibrary {
    public StaticWordLibraryAnnotated() {
    }
}
// END: anagram.spring.autowire.WordLibrary
