package impl;

import java.net.URL;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class ConsistencyOfURLTest {

    @Test public void urlToExternalAndToString() throws Exception {
        // BEGIN: consistency.url
        URL url = new URL("http://www.apidesign.org");
        assertEquals(url.toString(), url.toExternalForm());
        // END: consistency.url
    }
    
}
