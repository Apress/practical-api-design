package usage;

import org.junit.Test;
import static org.junit.Assert.*;
import query.Query;

public class UseQueryTest {
    
    public UseQueryTest() {
    }            

    @Test public void verifyThatQueryReturnsHello() {
        Query query = new Query();
        Query.Sequence reply = query.computeReply();
        assertEquals("Length is correct", 5, reply.length());
        
        assertEquals('H', reply.charAt(0));
        assertEquals('e', reply.charAt(1));
        assertEquals('l', reply.charAt(2));
        assertEquals('l', reply.charAt(3));
        assertEquals('o', reply.charAt(4));
    }
}
