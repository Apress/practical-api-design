package org.apidesign.extensibleicon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiedTest {
    private ModifiableIcon icon;
    
    public ModifiedTest() {
    }

    @Before
    public void setUp() {
        icon = new ModifiableIcon();
    }

    @After
    public void tearDown() {
    }

    @Test public void testEnabledDisabledState() {
        assertNull("Not yet modified", icon.getLookup().lookup(Modified.class));
        icon.markModified();
        assertNotNull("Modified", icon.getLookup().lookup(Modified.class));
    }
}