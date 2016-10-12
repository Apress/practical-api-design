package org.apidesign.anagram.app.property;

import org.apidesign.anagram.api.UI;

public final class Main {
    private Main() { }
    
    public static void main(String[] args) throws Exception {
        System.setProperty(
            "org.apidesign.anagram.api.WordLibrary", 
            "org.apidesign.anagram.wordstatic.StaticWordLibrary"
        );
        System.setProperty(
            "org.apidesign.anagram.api.Scrambler", 
            "org.apidesign.anagram.scramblersimple.SimpleScrambler"
        );
        
        UI ui = new org.apidesign.anagram.app.property.AnagramsWithProperties();
        ui.display();
    }
    
    
}
