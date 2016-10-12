
package api.usage;

import api.Writer;
import java.io.IOException;
import java.io.StringWriter;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/** Converting the Crypto example to the APIs that split client and provider
 * concerns and do not mix delegation and subclassing.
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class BufferedWriterCryptoTest {
    private StringWriter writer;
    
    
    public BufferedWriterCryptoTest() {
    }
    
    @Before
    public void setUp() {
        writer = new StringWriter();
    }

    @Test
    public void testBehaviourOfRealBufferInJDKWorksFine() throws IOException {
        Writer bufferedWriter = CryptoWriter.create(Writer.create(writer));
        bufferedWriter.write("VMS");
        bufferedWriter.flush();
        assertEquals("Converted", "WNT", writer.toString());
    }
    
}