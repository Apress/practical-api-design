package org.apidesign.gc;

import java.util.EventListener;

public final class WeakListeners {
    private WeakListeners() { }

    // BEGIN: gc.weaklistener.create
    public static <T extends EventListener> T create(
        Class<T> type, T listener, Object source
    ) {
        return org.openide.util.WeakListeners.create(
            type, listener, source
        );
    }
    // END: gc.weaklistener.create
}
