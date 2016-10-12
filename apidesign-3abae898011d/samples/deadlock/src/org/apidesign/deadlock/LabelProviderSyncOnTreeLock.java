package org.apidesign.deadlock;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelProviderSyncOnTreeLock extends JPanel {
    private static Logger LOG = Logger.getLogger(LabelProviderSyncOnTreeLock.class.getName());
    
    // BEGIN: deadlock.provider.treelock
    private HashSet<JLabel> allCreated = new HashSet<JLabel>();

    public JLabel createLabel () {
        synchronized (getTreeLock()) {
            LOG.log(Level.INFO, "Will create JLabel");
            JLabel l = new JLabel ();
            LOG.log(Level.INFO, "Label created {0}", l);
            allCreated.add (l);
            return l;
        }
    }
    // END: deadlock.provider.treelock

}
