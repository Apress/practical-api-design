package org.apidesign.openfixed;

import java.util.concurrent.CountDownLatch;

/** Test the Calculator.createPending() behavior.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public final class PostTest extends PendingTest {
    
    public PostTest(String testName) {
        super(testName);
    }

    @Override
    protected Calculator create() {
        return Calculator.createBatch();
    }

    public void testPostModificationEvents() throws Exception {
        // BEGIN: openfixed.usemount
        class PostListener extends BlockingListener 
        implements PostModificationListener {
            int cnt;

            @Override
            public synchronized void modification(ModificationEvent ev) {
                // registers for callback when batch processing is over:
                ev.postProcess(this);
                super.modification(ev);
            }

            @Override
            public synchronized void postProcess(PostModificationEvent ev) {
                // called when batch processing is over
                cnt++;
            }
        // FINISH: openfixed.usemount
            
            public synchronized void assertPostProcess(String msg, int expected) throws InterruptedException {
                for (int i = 0; i < 10; i++) {
                    if (cnt >= expected) {
                        break;
                    }
                    wait(1000);
                }
                assertEquals(msg, expected, cnt);
                cnt = 0;
            }
        }
        PostListener bl = new PostListener();
        
        Calculator calc = create();
        calc.addModificationListener(bl);
        
        calc.add(10);
        bl.first.await();
        
        calc.add(1);
        calc.add(2);
        calc.add(3);
        
        bl.cdl.countDown();
        bl.assertPostProcess("Two postprocessings (one for 10), then for the rest", 2);
    }
}
