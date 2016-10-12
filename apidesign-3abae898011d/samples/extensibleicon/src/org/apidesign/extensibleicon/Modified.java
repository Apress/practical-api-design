package org.apidesign.extensibleicon;

import java.io.IOException;

// BEGIN: ext.modified
public interface Modified {
    public void save() throws IOException;
    public void discard() throws IOException;
}
// END: ext.modified
