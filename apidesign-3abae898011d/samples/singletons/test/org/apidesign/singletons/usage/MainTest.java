package org.apidesign.singletons.usage;

import org.apidesign.singletons.api.DialogDisplayer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.MockServices;
import static org.junit.Assert.*;

// BEGIN: singletons.injectable.test
public class MainTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        MockServices.setServices(MockDialogDisplayer.class);
    }

    @Test
    public void testMainAsksAQuestion() {
        assertNull(
            "No question asked yet", MockDialogDisplayer.askedQuery
        );
        Main.main(new String[0]);
        assertNotNull(
            "main code asked our Mock displayer",
            MockDialogDisplayer.askedQuery
        );
    }

    public static final class MockDialogDisplayer extends DialogDisplayer {
        static String askedQuery;
        
        @Override
        public boolean yesOrNo(String query) {
            askedQuery = query;
            return false;
        }
    }
}
// END: singletons.injectable.test
