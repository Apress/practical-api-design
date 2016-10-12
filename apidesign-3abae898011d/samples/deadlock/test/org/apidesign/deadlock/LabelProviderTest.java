package org.apidesign.deadlock;

import java.awt.Dimension;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.junit.Test;
import org.netbeans.junit.Log;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

// BEGIN: deadlock.test.controlflow
public class LabelProviderTest extends NbTestCase {
    static final Logger LOG = Logger.getLogger(
        LabelProviderTest.class.getName()
    );
    
    public LabelProviderTest(String n) {
        super(n);
    }

    @Override
    protected int timeOut() {
        return 10000;
    }
    
    
    public static class StrangePanel extends LabelProvider {
        @Override
        // BEGIN: deadlock.pref.size
        public Dimension getPreferredSize () {
            JLabel sampleLabel = createLabel();
            return sampleLabel.getPreferredSize ();
        }
        // END: deadlock.pref.size
    }
    
    
    

    public void testCreateLabel() {
        if (Boolean.getBoolean("no.failures")) return;
        final LabelProvider instance = new StrangePanel();
        
        class R implements Runnable {
            public void run() {
                LOG.info("In AWT thread");
                JFrame f = new JFrame();
                f.add(instance);
                f.setVisible(true);
                f.pack();
            }
        }

        Log.controlFlow(
            Logger.getLogger("org.apidesign"),
            Logger.getLogger("global"), 
            "THREAD: main MSG: Begin" +
            "THREAD: .*AWT.* MSG: In.*thread" +
            "THREAD: main MSG: Will create JLabel" +
            "THREAD: .*AWT.* MSG: Will create JLabel", 
            300
        );
        
        R showFrame = new R();
        SwingUtilities.invokeLater(showFrame);
        
        LOG.info("Begin");
        JLabel result = instance.createLabel();
        assertNotNull("Creates the result", result);
    }

}
// END: deadlock.test.controlflow

