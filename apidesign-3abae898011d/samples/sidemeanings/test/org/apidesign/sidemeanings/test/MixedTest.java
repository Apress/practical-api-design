package org.apidesign.sidemeanings.test;


import org.apidesign.sidemeanings.MixedClass;
import org.apidesign.sidemeanings.NonMixed;
import org.apidesign.sidemeanings.NonMixedFactory;
import org.apidesign.sidemeanings.NonMixedFactory.Callback;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MixedTest {

    public MixedTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // BEGIN: sidemeanings.Mixed.Use
    @Test public void useOfClassWithMixedMeanings() {
        class AddFiveMixedCounter extends MixedClass {
            @Override
            protected int toBeImplementedBySubclass() {
                toBeCalledBySubclass();
                return 5;
            }
        }
        AddFiveMixedCounter add5 = new AddFiveMixedCounter();
        assertEquals("5/1 = 5", 5, add5.apiForClients());
        assertEquals("10/2 = 5", 5, add5.apiForClients());
        assertEquals("15/3 = 5", 5, add5.apiForClients());
    }
    // END: sidemeanings.Mixed.Use
    
    // BEGIN: sidemeanings.Mixed.Clean.Use
    @Test public void useWithoutMixedMeanings() {
        class AddFiveMixedCounter implements NonMixedFactory.Provider {
            private Callback callback;
            
            public int toBeImplementedBySubclass() {
                callback.toBeCalledBySubclass();
                return 5;
            }

            public void initialize(Callback c) {
                callback = c;
            }
        }
        NonMixed add5 = NonMixedFactory.create(new AddFiveMixedCounter());
        assertEquals("5/1 = 5", 5, add5.apiForClients());
        assertEquals("10/2 = 5", 5, add5.apiForClients());
        assertEquals("15/3 = 5", 5, add5.apiForClients());
    }
    // END: sidemeanings.Mixed.Clean.Use
}

