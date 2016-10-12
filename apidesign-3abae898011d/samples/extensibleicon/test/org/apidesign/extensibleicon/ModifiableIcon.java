package org.apidesign.extensibleicon;

import java.awt.Component;
import java.awt.Graphics;
import java.io.IOException;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

// BEGIN: ext.modifiable.icon
public final class ModifiableIcon implements ExtIcon {
    // AbstractLookup is helper implementation
    // so that people do not need to write its own
    // lookup from scratch
    private AbstractLookup lookup;
    // InstanceContent is interface that gives
    // "creator of the lookup" more rights, like
    // ability to modify the content
    private InstanceContent ic;
    
    ModifiableIcon() {
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
    }
    
    public Lookup getLookup() {
        return lookup;
    }
    
    public void markModified() {
        if (lookup.lookup(ModifiedImpl.class) == null) {
            ic.add(new ModifiedImpl());
        }
    }
    
    private final class ModifiedImpl implements Modified {
        public void save() throws IOException {
            // save somehow
        }

        public void discard() throws IOException {
            // discard changes
        }
    }
// FINISH: ext.modifiable.icon

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.fillRect(x, y, 16, 16);
    }

    public int getIconWidth() {
        return 16;
    }

    public int getIconHeight() {
        return 16;
    }
}
