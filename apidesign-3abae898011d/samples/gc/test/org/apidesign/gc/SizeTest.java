package org.apidesign.gc;

import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class SizeTest {
    public SizeTest() {
    }
    
    
    @Test
    public void measureIsDataLowerThan8No() {
        if (Boolean.getBoolean("no.failures")) return;
        Data d = new Data();
        
        NbTestCase.assertSize(
            "The size of the data instance is higher than 8, fail", 8, d
        );
    }

    @Test
    public void measureIsDataLowerThan16Yes() {
        // BEGIN: size.measure
        Data d = new Data();
        NbTestCase.assertSize(
            "The size of the data instance is higher than 16", 16, d
        );
        // END: size.measure
    }
    
    
    // BEGIN: size.Data
    private static final class Data {
        int data;
    }
    // END: size.Data
}
