package org.apidesign.anagram.app.lookup;

import org.apidesign.anagram.api.UI;

public final class Main {
    private Main() { }
    
    public static void main(String[] args) throws Exception {
        // BEGIN: anagram.words.annotation.layersetup
        System.setProperty("org.openide.util.Lookup.paths", "Services");
        // END: anagram.words.annotation.layersetup

        UI ui = new AnagramsWithLookup();
        ui.display();
    }
}
