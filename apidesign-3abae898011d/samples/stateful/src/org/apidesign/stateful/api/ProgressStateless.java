package org.apidesign.stateful.api;

/** API for notifying progress that cannot call
 * {@link #start(int)} and {@link InProgress#progress(int)} in wrong
 * order. In contract to {@link ProgressStateful} class which allows that.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public abstract class ProgressStateless {
// BEGIN: progress.phases
    public static ProgressStateless create(String name) {
        return createImpl(name);
    }
    public abstract InProgress start(int totalAmount);

    public abstract class InProgress {
        public abstract void progress(int howMuch);
        public abstract void finish();
        // FINISH: progress.phases

        InProgress() {
        }
    }


    ProgressStateless() {
    }
    
    private static ProgressStateless createImpl(String name) {
        return new Impl(name);
    }

    private static final class Impl extends ProgressStateless {
        private final String name;

        public Impl(String name) {
            this.name = name;
        }

        @Override
        public InProgress start(int totalAmount) {
            return new InImpl(totalAmount);
        }

        private class InImpl extends InProgress {
            private final int total;
            private int current;

            public InImpl(int total) {
                this.total = total;
            }

            @Override
            public void progress(int howMuch) {
                current = howMuch;
            }

            @Override
            public void finish() {
                current = total;
            }
        }
    }
}
