package org.apidesign.extensibleicon;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Icon2Image {
    private Icon2Image() {}
    
    // BEGIN: ext.icon2image
    public static Image toImage(ExtIcon icon) {
        Image img = icon.getLookup().lookup(Image.class);
        if (img != null) {
            return img;
        }
        BufferedImage buf = new BufferedImage(
            icon.getIconWidth(), 
            icon.getIconHeight(), 
            BufferedImage.TYPE_INT_RGB
        );
        icon.paintIcon(null, buf.getGraphics(), 0, 0);
        return buf;
    }
    // END: ext.icon2image
}
