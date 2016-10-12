package org.apidesign.extensionpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import org.apidesign.extensionpoint.api.TipOfTheDay;
import org.openide.util.Lookup;

public class Main {
    public static void main(String[] args) {
        for (;;) {
            // BEGIN: extension.point.Query
            Collection<? extends TipOfTheDay> all = 
                Lookup.getDefault().lookupAll(TipOfTheDay.class);
            List<TipOfTheDay> arr = new ArrayList<TipOfTheDay>(all);
            Collections.shuffle(arr);

            String msg;
            String title;
            int type;
            if (arr.isEmpty()) {
                msg = "I do not know what to say!";
                title = "No provider registered";
                type = JOptionPane.WARNING_MESSAGE;
            } else {
                msg = arr.get(0).sayHello();
                title = "Selected from " + arr.size() + " providers";
                type = JOptionPane.INFORMATION_MESSAGE;
            }
            // END: extension.point.Query

            String again = "Once Again";
            String exit = "Exit";
            String[] options = new String[] { again, exit };
            int ret = JOptionPane.showOptionDialog(
                null, msg, title, 0, type, null, options, exit
            );

            if (ret != 0) {
                break;
            }
        }
        System.exit(0);
    }
}
