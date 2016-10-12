package org.apidesign.openfixed;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/** Test the Calculator.createPending() behavior.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class PendingTest extends CalculatorBase {
    
    public PendingTest(String testName) {
        super(testName);
    }

    @Override
    protected Calculator create() {
        return Calculator.createPending();
    }

    public void testPendingEvents() throws Exception {
        BlockingListener bl = new BlockingListener();
        
        Calculator calc = create();
        calc.addModificationListener(bl);
        
        calc.add(10);
        bl.first.await();
        
        calc.add(1);
        calc.add(2);
        calc.add(3);
        
        bl.cdl.countDown();
        
        List<ModificationEvent> events = bl.assertEvents("Four changes together", 4);
        
        assertEquals("No pending events for first event", 0, events.get(0).getPending());
        assertEquals("Group of three, two remaining", 2, events.get(1).getPending());
        assertEquals("Group of three, one remaining", 1, events.get(2).getPending());
        assertEquals("Group of three, last one", 0, events.get(3).getPending());
    }
    
    static class BlockingListener extends MockListener {
        CountDownLatch first = new CountDownLatch(1);
        CountDownLatch cdl = new CountDownLatch(1);

        @Override
        public synchronized void modification(ModificationEvent ev) {
            try {
                first.countDown();
                cdl.await();
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
            super.modification(ev);
        }
    }
    
}
