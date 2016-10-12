package org.apidesign.deadlock.logs;

import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.Log;
import static org.junit.Assert.*;

public class OverrideMePleaseTest {

    public OverrideMePleaseTest() {
    }

    @Test
    public void overideMePleaseNotOverriden() {
        CharSequence log = Log.enable("org.apidesign", Level.WARNING);
        OverrideMePlease instance = new OverrideMePlease() {
        };
        if (log.length() == 0) {
            fail("There should be a warning: " + log);
        }
    }

    @Test
    public void overideMePleaseOverriden() {
        // BEGIN: logs.override.test
        CharSequence log = Log.enable("org.apidesign", Level.WARNING);
        OverrideMePlease instance = new OverrideMePlease() {
            @Override
            protected boolean overideMePlease() {
                return true;
            }
        };
        if (log.length() != 0) {
            fail("There should be no warning: " + log);
        }
        // END: logs.override.test
    }

}