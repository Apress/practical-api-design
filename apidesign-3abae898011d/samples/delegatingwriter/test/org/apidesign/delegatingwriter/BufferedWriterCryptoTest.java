
package org.apidesign.delegatingwriter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/** Emulates what goes wrong when delegating directly
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class BufferedWriterCryptoTest {
    private StringWriter writer;
    private String fromCode;
    private String toCode;
    
    public BufferedWriterCryptoTest() {
    }
    
    @Before
    public void setUp() {
        writer = new StringWriter();
        StringBuffer f = new StringBuffer();
        StringBuffer t = new StringBuffer();
        for (int i = 0; i < 500; i++) {
            f.append("VMS");
            t.append("WNT");
        }
        fromCode = f.toString();
        toCode = t.toString();
    }

    @Test
    public void testBehaviourOfRealBufferInJDKWorksFine() throws IOException {
        // BEGIN: writer.crypto.ok
        CryptoWriter bufferedWriter = new CryptoWriter(writer);
        bufferedWriter.append("VMS");
        bufferedWriter.flush();
        assertEquals("Converted", "WNT", writer.toString());
        // END: writer.crypto.ok
    }

    @Test
    public void testBehaviourOfRealBufferInJDKWorksFineOnLongSentences() throws IOException {
        CryptoWriter bufferedWriter = new CryptoWriter(writer);
        bufferedWriter.append(fromCode);
        bufferedWriter.flush();
        assertEquals("Converted", toCode, writer.toString());
    }

    @Test
    public void testBehaviourOfBufferThatDelegatesToAppendFails() throws IOException {
        CryptoWriter bufferedWriter = new CryptoWriter(writer, CryptoWriter.Behaviour.DELEGATE_TO_OUT);
        bufferedWriter.append(fromCode);
        bufferedWriter.flush();
        if (Boolean.getBoolean("no.failures")) return;
        assertEquals("This will fail, as the direct delegation from append to " +
            "the underlaying writer will skip all the crypto methods", 
            toCode, writer.toString()
        );
    }

    @Test
    public void testBehaviourWhenDelegatingConditionallyIsOK() throws IOException {
        CryptoWriter bufferedWriter = new CryptoWriter(writer, AltBufferedWriter.Behaviour.DELEGATE_CONDITIONALLY);
        bufferedWriter.append("VMS");
        bufferedWriter.flush();
        assertEquals("Converted", "WNT", writer.toString());
    }
    
}