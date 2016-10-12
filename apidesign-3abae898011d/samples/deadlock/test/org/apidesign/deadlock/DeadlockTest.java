package org.apidesign.deadlock;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

// BEGIN: deadlock.test
public class DeadlockTest extends NbTestCase {
    static final Logger LOG = Logger.getLogger(
        DeadlockTest.class.getName()
    );
    
    public DeadlockTest(String n) {
        super(n);
    }

    @Override
    protected int timeOut() {
        return 10000;
    }
    
    
    public static class StrangePanel extends LabelProvider {
        @Override
        public Dimension getPreferredSize () {
            try {
                Thread.sleep(1000);
                JLabel sampleLabel = createLabel();
                return sampleLabel.getPreferredSize();
            } catch (InterruptedException ex) {
                Logger l = Logger.getLogger(
                    DeadlockTest.class.getName()
                );
                l.log(Level.SEVERE, null, ex);
                return super.getPreferredSize();
            }
        }
    }
    
    
    

    public void testCreateLabel() throws Exception {
        if (Boolean.getBoolean("no.failures")) return;
        final LabelProvider instance = new StrangePanel();
        
        class R implements Runnable {
            public void run() {
                JFrame f = new JFrame();
                f.add(instance);
                f.setVisible(true);
                f.pack();
            }
        }

        R showFrame = new R();
        SwingUtilities.invokeLater(showFrame);
        
        Thread.sleep(500);
        JLabel result = instance.createLabel();
        assertNotNull("Creates the result", result);
    }

}
// END: deadlock.test

