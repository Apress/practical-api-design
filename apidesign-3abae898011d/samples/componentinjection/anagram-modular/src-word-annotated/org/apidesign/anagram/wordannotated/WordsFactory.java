
package org.apidesign.anagram.wordannotated;

import org.apidesign.anagram.api.annotations.Words;

// BEGIN: anagram.words.annotation.usage
public class WordsFactory {
    @Words
    public static String[] myWords() {
        return new String[] {
            "microprocessor",
            "navigation",
            "optimization",
            "parameter",
            "patrick",
        };
    }
}
// END: anagram.words.annotation.usage
