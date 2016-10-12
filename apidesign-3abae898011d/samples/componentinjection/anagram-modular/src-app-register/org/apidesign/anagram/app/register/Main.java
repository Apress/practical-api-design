package org.apidesign.anagram.app.register;

import org.apidesign.anagram.api.UI;
import org.apidesign.anagram.gui.AnagramsWithConstructor;
import org.apidesign.anagram.scramblersimple.SimpleScrambler;
import org.apidesign.anagram.wordstatic.StaticWordLibrary;

public final class Main {
    private Main() { }
    
    public static void main(String[] args) throws Exception {
        Launcher.registerScrambler(SimpleScrambler.class);
        Launcher.registerWordLibrary(StaticWordLibrary.class);
        Launcher.registerUI(AnagramsWithConstructor.class);
        
        UI ui = Launcher.launch();
        ui.display();
    }
    
    
}
