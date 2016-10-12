package org.apidesign.singletons.api;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.openide.util.Lookup;

// BEGIN: singletons.injectable.api
public abstract class DialogDisplayer {
    protected DialogDisplayer() {
    }

    /** Ask user a question.
     *
     * @param query the text of the question
     * @return true if user confirmed or false if declined
     */
    public abstract boolean yesOrNo(String query);

    public static DialogDisplayer getDefault() {
        return Impl.DEFAULT;
    }
    // FINISH: singletons.injectable.api


    // BEGIN: singletons.injectable.dummyimpl
    private static final class Impl extends DialogDisplayer {
        private static final DialogDisplayer DEFAULT = initialize();
        
        @Override
        public boolean yesOrNo(String query) {
            System.err.printf("Saying no to '%s'\n", query);
            return false;
        }
        // FINISH: singletons.injectable.dummyimpl

        private static DialogDisplayer initialize() {
            if (Boolean.getBoolean("singleton.jdk6")) {
                return initializeServiceLoader();
            } else {
                return initializeLookup();
            }
        }
        
        // BEGIN: singletons.injectable.serviceloader
        private static DialogDisplayer initializeServiceLoader() {
            Iterator<DialogDisplayer> it = null;
            it = ServiceLoader.load(DialogDisplayer.class).iterator();
            return it != null && it.hasNext() ? it.next() : new Impl();
        }
        // END: singletons.injectable.serviceloader

        // BEGIN: singletons.injectable.lookup
        private static DialogDisplayer initializeLookup() {
            DialogDisplayer def = Lookup.getDefault().lookup(DialogDisplayer.class);
            return def != null ? def : new Impl();
        }
        // END: singletons.injectable.lookup
    }
}
