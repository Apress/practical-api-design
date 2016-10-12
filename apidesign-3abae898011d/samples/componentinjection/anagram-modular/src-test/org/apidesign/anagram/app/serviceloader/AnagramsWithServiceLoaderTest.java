package org.apidesign.anagram.app.serviceloader;

import org.apidesign.anagram.app.lookup.*;
import org.apidesign.anagram.gui.*;

public class AnagramsWithServiceLoaderTest extends AnagramsTestBase {
    @Override
    protected Anagrams create() {
        return new AnagramsWithServiceLoader();
    }
}
