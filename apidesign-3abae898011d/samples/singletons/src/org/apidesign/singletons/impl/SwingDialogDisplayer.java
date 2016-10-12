package org.apidesign.singletons.impl;

import javax.swing.JOptionPane;
import org.apidesign.singletons.api.DialogDisplayer;
import org.openide.util.lookup.ServiceProvider;

// BEGIN: singletons.injectable.implement
@ServiceProvider(service=DialogDisplayer.class)
public final class SwingDialogDisplayer extends DialogDisplayer {
    @Override
    public boolean yesOrNo(String query) {
        final int res = JOptionPane.showConfirmDialog(null, query);
        return res == JOptionPane.OK_OPTION;
    }
}
// END: singletons.injectable.implement
