package org.apidesign.exceptions.trycatchredo.api;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaroslav Tulach
 */
final class SaveAction extends AbstractAction {
    private final URL where;
    private final CharSequence what;
    
    SaveAction(URL where, CharSequence what) {
        this.where = where;
        this.what = what;
    }
    
    
    public void actionPerformed(ActionEvent ev) {
        assert EventQueue.isDispatchThread();
        // BEGIN: trycatchredo.SaveAction
        try {
            OutputStream os = where.openConnection().getOutputStream();
            os.write(what.toString().getBytes());
            os.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // END: trycatchredo.SaveAction
    }
}
