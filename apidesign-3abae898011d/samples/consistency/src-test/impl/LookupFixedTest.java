/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impl;

import api.Lookup;
import api.Lookups;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@netbeans.org>
 */
public class LookupFixedTest {

    @Test public void checkConsistency() throws Exception {
        
        Lookup lkp = Lookups.fixed("Hello", 1, 1.0, "World");
        
        assertEquals("1", 1, lkp.lookup(Number.class));
        assertEquals("two elements", 2, lkp.lookupAll(Number.class).size());
        assertEquals("two classes", 2, lkp.lookupAllClasses(Number.class).size());
        
        assertEquals("Hello", lkp.lookup(String.class));
        assertEquals("two strings", 2, lkp.lookupAll(String.class).size());
        assertEquals("one string type", 1, lkp.lookupAllClasses(String.class).size());
    }

}
