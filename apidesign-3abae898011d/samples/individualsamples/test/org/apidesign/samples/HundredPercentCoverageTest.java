package org.apidesign.samples;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HundredPercentCoverageTest {
    HundredPercentCoverage instance;
    
    public HundredPercentCoverageTest() {
    }

    @Before
    public void setUp() {
        instance = new HundredPercentCoverage();
    }

    @Test
    public void cover100PercentOfAllLinesBlocksAndStatements() {
        assertEquals("init value + 5", 15, instance.add(5));
        assertEquals("division", 20, instance.percentageFrom(3));
    }

    @Test
    public void stillThereIsAWayToFail() {
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals("init value - 10", 0, instance.add(-10));
        assertEquals("division", 0, instance.percentageFrom(5));
    }
}