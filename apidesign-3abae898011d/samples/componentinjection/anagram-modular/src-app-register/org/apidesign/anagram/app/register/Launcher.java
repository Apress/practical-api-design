package org.apidesign.anagram.app.register;

import org.apidesign.anagram.api.Scrambler;
import org.apidesign.anagram.api.UI;
import org.apidesign.anagram.api.WordLibrary;

// BEGIN: anagram.programatic.register
public final class Launcher {
    private static Class<? extends WordLibrary> wordLibrary;
    private static Class<? extends Scrambler> scrambler;
    private static Class<? extends UI> ui;
    
    
    private Launcher() {
    }

    
    public static void registerWordLibrary(
        Class<? extends WordLibrary> libraryClass
    ) {
        wordLibrary = libraryClass;
    }
    public static void registerScrambler(
        Class<? extends Scrambler> scramblerClass
    ) {
        scrambler = scramblerClass;
    }
    public static void registerUI(Class<? extends UI> uiClass) {
        ui = uiClass;
    }
    
    public static UI launch() throws Exception {
        WordLibrary w = wordLibrary.newInstance();
        Scrambler s = scrambler.newInstance();
        return ui.getConstructor(
            WordLibrary.class, Scrambler.class
        ).newInstance(w, s);
    }
}
// END: anagram.programatic.register
