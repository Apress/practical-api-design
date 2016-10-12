package org.apidesign.anagram.app.serviceloader;

import org.apidesign.anagram.api.UI;

public final class Main {
    private Main() { }
    
    public static void main(String[] args) throws Exception {
        UI ui = new AnagramsWithServiceLoader();
        ui.display();
    }
}
