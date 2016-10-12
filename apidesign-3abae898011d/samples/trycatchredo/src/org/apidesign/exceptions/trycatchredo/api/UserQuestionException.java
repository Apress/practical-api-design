package org.apidesign.exceptions.trycatchredo.api;

import java.io.IOException;
import javax.swing.JOptionPane;

// BEGIN: trycatchredo.UserQuestionException
/** Specialized I/O exception to request some kind of user confirmation.
 * A code that needs to ask user shall not attempt to open a dialog itself,
 * rather it shall emit this exception and let its callers show the dialog
 * at appropriate time.
 *
 * @author Jaroslav Tulach
 * @since 2.0
 */
public abstract class UserQuestionException extends IOException {
    /** Description of the dialog to show to the user. Whoever catches
     * this exception shall use 
     * {@link #getQuestionPane()}.
     * {@link JOptionPane#createDialog(java.lang.String)}
     * to construct and display the dialog.
     * 
     * @return the pane to display to user
     */
    public abstract JOptionPane getQuestionPane();
    /** When the user confirms (or rejects) message presented by the
     * {@link #getQuestionPane()} dialog, the exception shall be notified
     * by calling this method with {@link JOptionPane#getValue()} option.
     *
     * @param option the option selected by the user
     */
    public abstract void confirm(Object option);
}
// END: trycatchredo.UserQuestionException