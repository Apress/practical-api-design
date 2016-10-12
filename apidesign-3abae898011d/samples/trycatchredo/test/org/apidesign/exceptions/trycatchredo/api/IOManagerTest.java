package org.apidesign.exceptions.trycatchredo.api;


import org.apidesign.exceptions.trycatchredo.usage.MemoryURL;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.apidesign.exceptions.trycatchredo.usage.QueryStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class IOManagerTest {

    public IOManagerTest() {
    }

    @Before
    public void setUp() {
        MemoryURL.initialize();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void simpleWrite() throws Exception {
        URL u = new URL("memory://simpleWrite.txt");
        MemoryURL.registerURL(u.toExternalForm(), "", null);
        final Action a = IOManager.createSaveAction(u, "Hello World!");
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
                a.actionPerformed(new ActionEvent(this, 0, ""));
            }
        });
        String out = MemoryURL.getOutputForURL(u.toExternalForm());
        assertEquals("Hello World!", out);
    }

    @Test
    public void writeWithAQuestion() throws Exception {
        URL u = new URL("memory://queryEncoding.txt");

        MemoryURL.registerURL(u.toExternalForm(), "", new QueryStream());
        final Action a = IOManager.createSaveAction(u, "Ask a Question");
        // simulate that the user clicks Yes to the reverse question in the dialog
        IOManager.setVisibleOption = JOptionPane.YES_OPTION;
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
                a.actionPerformed(new ActionEvent(this, 0, ""));
            }
        });
        String out = MemoryURL.getOutputForURL(u.toExternalForm());
        assertEquals("Text is reversed", "noitseuQ a ksA", out);
    }
}