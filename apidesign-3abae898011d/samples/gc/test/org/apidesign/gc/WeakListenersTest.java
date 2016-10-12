package org.apidesign.gc;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import javax.swing.JPanel;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;
import static org.junit.Assert.*;

public class WeakListenersTest {

    private static JPanel longLivingBean;
    
    public WeakListenersTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        longLivingBean = new JPanel();
    }
    
    @Before
    public void setUp() {
        longLivingBean.setBackground(Color.BLACK);
    }

    @Test
    public void listenWithoutWeakReference() {
        PropL listener = new PropL();
        longLivingBean.addPropertyChangeListener(listener);
        longLivingBean.setBackground(Color.WHITE);
        assertEquals("background property change notified", "background", listener.propName);
        WeakReference<Object> ref = new WeakReference<Object>(listener);
        listener = null;
        try {
            NbTestCase.assertGC(
                "Of course, this listener cannot disappear, because it is held from long living JavaBean", 
                ref
            );
        } catch (AssertionFailedError ex) {
            ex.printStackTrace();
            // of course it cannot be garbage collected. That is OK.
            return;
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
            // sometimes the test fails on OOME, let's catch it
            // of course it cannot be garbage collected. That is OK.
            return;
        }
        fail("The listener cannot be GCed as it is held from long living JavaBean");
    }

    @Test
    public void listenViaWeakListener() {
        PropL listener = new PropL();
        
        PropertyChangeListener weakL = WeakListeners.create(PropertyChangeListener.class, listener, longLivingBean);
        longLivingBean.addPropertyChangeListener(weakL);
        longLivingBean.setBackground(Color.WHITE);
        assertEquals("background property change notified", "background", listener.propName);
        // BEGIN: gc.assertGC
        WeakReference<Object> ref = new WeakReference<Object>(listener);
        listener = null;
        NbTestCase.assertGC("This listener can disappear", ref);
        // END: gc.assertGC
    }

    
    private static class PropL implements PropertyChangeListener {
        String propName;
        public void propertyChange(PropertyChangeEvent evt) {
            propName = evt.getPropertyName();
        }
    }
}