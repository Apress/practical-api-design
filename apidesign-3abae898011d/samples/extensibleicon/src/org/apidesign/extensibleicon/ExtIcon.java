package org.apidesign.extensibleicon;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import org.openide.util.Lookup;

// BEGIN: ext.Icon
public interface ExtIcon extends Icon, Lookup.Provider {
    public void paintIcon(Component c, Graphics g, int x, int y);
    public int getIconWidth();
    public int getIconHeight();
    
    public Lookup getLookup();
}
// END: ext.Icon
