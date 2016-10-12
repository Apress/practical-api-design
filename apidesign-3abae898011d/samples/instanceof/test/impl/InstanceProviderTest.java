package impl;

import api.BetterInstanceProvider;
import api.InstanceProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InstanceProviderTest {

    public InstanceProviderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void verifyBehaviourOnOldInterface() throws Exception {
        class IP implements InstanceProvider {
            int instanceClass;
            int instanceCreate;
            
            public Class<?> instanceClass() {
                instanceClass++;
                return String.class;
            }

            public Object instanceCreate() {
                instanceCreate++;
                return "API Design!";
            }
        }
        IP instance = new IP();
        
        boolean isString = checkIsString(instance);
        assertTrue("Provides string", isString);
        assertEquals("Class was tested", 1, instance.instanceClass);
        assertEquals("No instance created", 0, instance.instanceCreate);
    }
    
    @Test
    public void verifyBehaviourOnNewInterface() throws Exception {
        class IP implements BetterInstanceProvider {
            int isInstanceOf;
            int instanceClass;
            int instanceCreate;
            
            public Class<?> instanceClass() {
                instanceClass++;
                return String.class;
            }

            public Object instanceCreate() {
                instanceCreate++;
                return "API Design!";
            }

            public boolean isInstanceOf(Class<?> c) {
                isInstanceOf++;
                return c.isAssignableFrom(String.class);
            }
        }
        IP instance = new IP();
        
        boolean isString = checkIsString(instance);
        assertTrue("Provides string", isString);
        assertEquals("Class was not tested", 0, instance.instanceClass);
        assertEquals("No instance created", 0, instance.instanceCreate);
        assertEquals("isInstanceOf called", 1, instance.isInstanceOf);
    }

    private static boolean checkIsString(InstanceProvider instance) throws Exception {
        // BEGIN: instanceof.Use
        if (instance instanceof BetterInstanceProvider) {
            BetterInstanceProvider bip = (BetterInstanceProvider)instance;
            return bip.isInstanceOf(String.class);
        } else {
            return String.class.isAssignableFrom(instance.instanceClass());
        }
        // END: instanceof.Use
    }

}