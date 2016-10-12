package org.apidesign.gc;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

// BEGIN: compiler.surprises.intro
public class CompilerSurprisesTest {
    Reference<String> cache;

    public String factory() {
        String value = new String("Can I disappear?");
        cache = new WeakReference<String>(value);
        return value;
    }

    @Test
    public void checkThatTheValueCanDisapper() {
        String retValue = factory();
        retValue = null;
        assertGC("Nobody holds the string value anymore." +
                "It can be GCed.", cache);
    }
// FINISH: compiler.surprises.intro

// BEGIN: compiler.surprises.error
    @Test
    public void obviouslyWithoutClearingTheReferenceItCannotBeGCed() {
        String retValue = factory();
// commented out:        retValue = null;
        assertNotGC("The reference is still on stack." +
                "It cannot be GCed.", cache);
    }
// END: compiler.surprises.error


// BEGIN: compiler.surprises.surprise
    boolean yes = true;
    @Test
    public void canItBeGCedSurprisingly() {
        String retValue;
        if (yes) {
            retValue = factory();
        }
        assertGC("Can be GCed, as retValue is not on stack!!!!", cache);
    }
// END: compiler.surprises.surprise


// BEGIN: compiler.surprises.fix
    boolean ok = true;
    @Test
    public void canItBeGCedIfInitialized() {
        String retValue = null;
        if (ok) {
            retValue = factory();
        }
        assertNotGC("Cannot be GCed as retValue is not stack", cache);
    }
// END: compiler.surprises.fix

// BEGIN: compiler.surprises.fix.init
    @Test public void properInitializationFixesTheProblem() {
        String retValue;
        if (yes) {
            retValue = factory();
        } else {
            retValue = null;
        }
        assertNotGC("Cannot be GCed, now the retValue is on stack", cache);
    }
// END: compiler.surprises.fix.init

// BEGIN: compiler.surprises.fix.final
    @Test public void properUseOfFinalFixesTheProblem() {
        final String retValue;
        if (yes) {
            retValue = factory();
        } else {
            retValue = null;
        }
        assertNotGC("Cannot be GCed, now the retValue is on stack", cache);
    }
// END: compiler.surprises.fix.final

// BEGIN: compiler.surprises.scope
    @Test public void canItBeHeldByNoLongerExistingScopeSurprisingly() {
        {
            Object val = factory();
        }
        assertNotGC("Surprisingly this variable cannot be GCed, " +
                "even val is out of scope!!!!", cache);
    }
// END: compiler.surprises.scope

    private static void assertGC(String msg, Reference<?> ref) {
        NbTestCase.assertGC(msg, ref);
    }

    private static void assertNotGC(String msg, Reference<?> ref) {
        try {
            NbTestCase.assertGC("Cannot be GCed. " + msg, ref);
        } catch (Error ex) {
            // OK
            return;
        }
        fail(msg + ref.get());
    }
}