package org.apidesign.stateful.api;

import java.lang.annotation.Documented;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
// BEGIN: progress.annotations
public abstract class ProgressStateMachine {
    /** Annotation that helps users and IDEs to understand what
     * is the allowed order of method calls on instances of given class.
     */
    @Documented
    public @interface StateChange {
        /** What is the required state of the object before given method
         * is called.
         * @return "*" means any state, otherwise specify the state's name
         */
        String from() default "*";
        /** The state object enters after the method successfully returns.
         * @return name of new state
         */
        String to();
    }

    @StateChange(to="ready")
    public static ProgressStateMachine create(String name) {
        return createImpl(name);
    }

    @StateChange(from="ready", to="started")
    public abstract void start(int totalAmount);

    @StateChange(from="started", to="started")
    public abstract void progress(int howMuch);

    @StateChange(to="finished")
    public abstract void finish();
    // FINISH: progress.annotations



    private static ProgressStateMachine createImpl(String name) {
        return null; // implementation left out, similar to Progress.java
    }
}
