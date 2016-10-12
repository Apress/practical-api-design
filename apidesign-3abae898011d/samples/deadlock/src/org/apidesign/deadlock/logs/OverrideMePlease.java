package org.apidesign.deadlock.logs;

import java.util.logging.Logger;


public abstract class OverrideMePlease {
    boolean whatIsDefault;
    
    public OverrideMePlease() {
        whatIsDefault = overideMePlease();
    }
    // BEGIN: logs.override
    protected boolean overideMePlease() {
        Logger.getLogger(OverrideMePlease.class.getName()).warning(
            "subclasses are supposed to override overideMePlease() method!"
        );
        // some default
        return true;
    }
    // END: logs.override
}
