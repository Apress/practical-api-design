package org.apidesign.deadlock;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelProvider extends JPanel {
    private static Logger LOG = Logger.getLogger(LabelProvider.class.getName());
    
    // BEGIN: deadlock.label.provider
    private HashSet<JLabel> allCreated = new HashSet<JLabel>();

    public synchronized JLabel createLabel () {
        // BEGIN: deadlock.logs
        LOG.log(Level.INFO, "Will create JLabel");
        JLabel l = new JLabel ();
        LOG.log(Level.INFO, "Label created {0}", l);
        // END: deadlock.logs
        allCreated.add (l);
        return l;
    }
    // END: deadlock.label.provider

}
