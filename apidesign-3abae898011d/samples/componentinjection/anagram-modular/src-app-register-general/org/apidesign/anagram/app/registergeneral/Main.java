package org.apidesign.anagram.app.registergeneral;

import org.apidesign.anagram.api.UI;
import org.apidesign.anagram.scramblersimple.SimpleScrambler;
import org.apidesign.anagram.wordstatic.StaticWordLibrary;

public final class Main {
    private Main() { }
    
    public static void main(String[] args) throws Exception {
        Registry.registerClass(SimpleScrambler.class);
        Registry.registerClass(StaticWordLibrary.class);
        
        UI ui = new AnagramsWithRegistry();
        ui.display();
    }
    
    
}
