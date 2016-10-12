
package org.apidesign.privileged.api;

import java.util.concurrent.Executor;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public final class Executors {
    /** let's prefer factory methods */
    private Executors() {
    }
    
    
    public static Executor create() {
        return new Simple();
    }
    
    public static Executor create(boolean fair) {
        Configuration conf = new Configuration();
        conf.setFair(fair);
        return new Fair(conf);
    }

    // BEGIN: design.less.privileged
    public static Executor create(Configuration config) {
        return new Fair(config);
    }
    
    public static final class Configuration {
        boolean fair;
        int maxWaiters = -1;
        
        public void setFair(boolean fair) {
            this.fair = fair;
        }
        public void setMaxWaiters(int max) {
            this.maxWaiters = max;
        }
    }
    // END: design.less.privileged
    
    private static final class Simple implements Executor {
        public synchronized void execute(Runnable command) {
            command.run();
        }
    }
    private static final class Fair implements Executor {
        private final Configuration conf;
        
        public Fair(Configuration conf) {
            this.conf = conf;
        }
        
        public void execute(Runnable command) {
            // TBD
        }
    }
}
