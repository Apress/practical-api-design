package org.apidesign.exceptions.trycatchredo.usage;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.Action;
import org.apidesign.exceptions.trycatchredo.api.IOManager;
import org.apidesign.exceptions.trycatchredo.api.UserQuestionException;

/** Sample usage showing interactive storage capabilities of the
 * {@link UserQuestionException}
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MemoryURL.initialize();
        
        for (int cnt = 0; cnt < 10; cnt++) {
            URL u = new URL("memory://" + cnt + "/queryEncoding.txt");
            MemoryURL.registerURL(u.toExternalForm(), "", new QueryStream());
            final Action a = IOManager.createSaveAction(u, "Ask a Question");
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    a.actionPerformed(new ActionEvent(this, 0, ""));
                }
            });
            String out = MemoryURL.getOutputForURL(u.toExternalForm());

            System.err.println(cnt + " output: " + out);
        }

        System.exit(0);
    }
}
