package org.apidesign.exceptions.trycatchredo.api;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaroslav Tulach
 */
final class SaveActionWithQuery extends AbstractAction {
    private final URL where;
    private final CharSequence what;
    
    SaveActionWithQuery(URL where, CharSequence what) {
        this.where = where;
        this.what = what;
    }
    
    
    public void actionPerformed(ActionEvent ev) {
        assert EventQueue.isDispatchThread();
        // BEGIN: trycatchredo.SaveActionWithQuery
        for (;;) {
            try {
                OutputStream os = where.openConnection().getOutputStream();
                os.write(what.toString().getBytes());
                os.close();
            } catch (UserQuestionException ex) {
                JOptionPane p = ex.getQuestionPane();
                JDialog d = p.createDialog(ex.getLocalizedMessage());
                setVisible(d, p);
                ex.confirm(p.getValue());
                if (
                    !p.getValue().equals(JOptionPane.CANCEL_OPTION) &&
                    !p.getValue().equals(JOptionPane.CLOSED_OPTION)
                ) {
                    continue;
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            break;
        }
        // END: trycatchredo.SaveActionWithQuery
    }

    private static void setVisible(JDialog d, JOptionPane p) {
        IOManager.setVisible(d, p);
    }
}
