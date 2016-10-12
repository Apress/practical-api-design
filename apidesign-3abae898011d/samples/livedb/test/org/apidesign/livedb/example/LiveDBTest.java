package org.apidesign.livedb.example;

import java.sql.SQLException;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class LiveDBTest extends TestCase {
    
    public LiveDBTest(String testName) {
        super(testName);
    }

    public void testSomeMethod() throws SQLException {
    // BEGIN: livedb.test
        List<Age> ages = Age.query();
        assertEquals("One record", 1, ages.size());
        Age age = ages.get(0);
        assertEquals("name is apidesign", "apidesign", age.NAME);
        assertEquals("it is three years old", 3, age.AGE.intValue());
    // END: livedb.test
    }

}
