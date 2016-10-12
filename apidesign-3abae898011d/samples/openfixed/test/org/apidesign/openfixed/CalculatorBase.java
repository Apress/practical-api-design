package org.apidesign.openfixed;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public abstract class CalculatorBase extends TestCase {
    
    public CalculatorBase(String testName) {
        super(testName);
    }
    
    protected abstract Calculator create();

    // BEGIN: openfixed.commontest
    public void testSumAndListeners() throws Exception {
        Calculator a = create();
        MockListener l = new MockListener();
        a.addModificationListener(l);
        a.add(5);
        a.add(10);
        a.add(20);
        int ch = allChanges(l.assertEvents("Three changes", 3));
        assertEquals("35 was the change", 35, ch);
        assertEquals("Current value", 35, a.getSum());
        a.add(-5);
        int ch2 = allChanges(l.assertEvents("One change", 1));
        assertEquals("minus five was the change", -5, ch2);
        assertEquals("Final value", 30, a.getSum());
    }
    
    private static int allChanges(List<ModificationEvent> events) {
        int changes = 0;
        for (ModificationEvent me : events) {
            changes += me.getChange();
        }
        return changes;
    }
    
    public static class MockListener implements ModificationListener {
        private List<ModificationEvent> events;
        
        @Override
        public synchronized void modification(ModificationEvent ev) {
            if (events == null) {
                events = new ArrayList<ModificationEvent>();
            }
            events.add(ev);
        }
        
        public synchronized List<ModificationEvent> assertEvents(
            String msg, int cnt
        ) throws InterruptedException {
            for (int i = 0; i < 10; i++) {
                if (events != null && events.size() >= cnt) {
                    break;
                }
                wait(1000);
            }
            assertEquals(msg + ":\n" + events, cnt, events.size());
            List<ModificationEvent> res = events;
            events = null;
            return res;
        }
    } // end of ModificationListener
    // END: openfixed.commontest
}
