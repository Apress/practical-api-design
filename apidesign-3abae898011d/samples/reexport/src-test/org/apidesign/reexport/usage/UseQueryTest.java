package usage;

import api.String;
import query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

public class UseQueryTest {
    
    public UseQueryTest() {
    }            

    @Test public void verifyThatQueryReturnsHello() {
        // BEGIN: reexport.transitive.change.in.String
        Query query = new Query();
        String reply = query.computeReply();
        assertEquals("Length is correct", 5, reply.length());
        // END: reexport.transitive.change.in.String
        
        assertEquals('H', reply.charAt(0));
        assertEquals('e', reply.charAt(1));
        assertEquals('l', reply.charAt(2));
        assertEquals('l', reply.charAt(3));
        assertEquals('o', reply.charAt(4));
        
    }
}
