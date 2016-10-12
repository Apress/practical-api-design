package org.apidesign.exceptions.trycatchredo.api;

import java.net.URL;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public final class IOManager {
    IOManager() {
    }

    /** Action that can store a text to given URL.
     *
     * @param where the url to upload the text to
     * @param what the text to upload
     * @return action that can be invoked anytime to save the content
     */
    public static Action createSaveAction(URL where, CharSequence what) {
        if (old) {
            return new SaveAction(where, what);
        } else {
            return new SaveActionWithQuery(where, what);
        }
    }

    //
    // Support for executing mock objects in tests
    //

    static boolean old;
    static Object setVisibleOption;
    static void setVisible(JDialog d, JOptionPane p) {
        if (setVisibleOption == null) {
            d.setVisible(true);
        } else {
            // only in test mode
            p.setValue(setVisibleOption);
        }
    }
}
