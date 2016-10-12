package org.apidesign.stateful.api;

/** API for notifying progress.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: progress.api
public abstract class ProgressStateful {
    public static ProgressStateful create(String name) {
        return createImpl(name);
    }
    public abstract void start(int totalAmount);
    public abstract void progress(int howMuch);
    public abstract void finish();
    // FINISH: progress.api

    ProgressStateful() {
    }
    
    private static ProgressStateful createImpl(String name) {
        return new Impl(name);
    }

    private static final class Impl extends ProgressStateful {
        private final String name;
        private int total = -1;
        private int current;

        public Impl(String name) {
            this.name = name;
        }

        @Override
        public void start(int totalAmount) {
            total = totalAmount;
        }

        @Override
        public void progress(int howMuch) {
            if (total == -1) {
                throw new IllegalStateException("Call start first!");
            }
            current = howMuch;
        }

        @Override
        public void finish() {
            total = -1;
            current = 0;
        }

    }
}
