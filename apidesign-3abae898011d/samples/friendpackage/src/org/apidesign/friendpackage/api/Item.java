package org.apidesign.friendpackage.api;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apidesign.friendpackage.impl.Accessor;

/** Class in API that everyone can use.
 *
 * @author Jaroslav Tulach
 */
// BEGIN: design.less.friend.Item
public final class Item {
    private int value;
    private ChangeListener listener;

    /** Only friends can create instances. */
    Item() {
    }
    
    /** Anyone can change value of the item. 
     */
    public void setValue(int newValue) {
        value = newValue;
        ChangeListener l = listener;
        if (l != null) {
            l.stateChanged(new ChangeEvent(this));
        }
    }
    
    /** Anyone can get the value of the item. 
     */
    public int getValue() {
        return value;
    }
    
    /** Only friends can listen to changes.
     */
    void addChangeListener(ChangeListener l) {
        assert listener == null;
        listener = l;
    }
// FINISH: design.less.friend.Item

    // BEGIN: design.less.friend.Item.static
    static {
        Accessor.setDefault(new AccessorImpl());
    }
    // END: design.less.friend.Item.static
}
