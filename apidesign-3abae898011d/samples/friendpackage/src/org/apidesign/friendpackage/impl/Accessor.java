package org.apidesign.friendpackage.impl;

import javax.swing.event.ChangeListener;
import org.apidesign.friendpackage.api.Item;

/**
 *
 * @author Jaroslav Tulach
 */
// BEGIN: design.less.friend.Accessor
public abstract class Accessor {
    private static volatile Accessor DEFAULT;
    public static Accessor getDefault() {
        Accessor a = DEFAULT;
        if (a == null) {
            throw new IllegalStateException("Something is wrong: " + a);
        }
        return a;
    }

    public static void setDefault(Accessor accessor) {
        if (DEFAULT != null) {
            throw new IllegalStateException();
        }
        DEFAULT = accessor;
    }
    
    public Accessor() {
    }

    protected abstract Item newItem();
    protected abstract void addChangeListener(Item item, ChangeListener l);
// FINISH: design.less.friend.Accessor

    // BEGIN: design.less.friend.InitAPI
    private static final Class<?> INIT_API_CLASS = loadClass(Item.class.getName());
    private static Class<?> loadClass(String name) {
        try {
            return Class.forName(
                name, true, Accessor.class.getClassLoader()
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    // END: design.less.friend.InitAPI
}
