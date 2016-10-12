/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.anagram.impl.annotations;

import java.util.Map;
import org.apidesign.anagram.api.WordLibrary;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: anagram.api.WordsImpl
public class WordsImpl implements WordLibrary {
    private Map<String,Object> map;

    private WordsImpl(Map<String,Object> m) {
        this.map = m;
    }

    public static WordsImpl create(Map attributes) {
        return new WordsImpl(attributes);
    }

    public String[] getWords() {
        return (String[])map.get("words"); // NOI18N
    }
}
// END: anagram.api.WordsImpl
