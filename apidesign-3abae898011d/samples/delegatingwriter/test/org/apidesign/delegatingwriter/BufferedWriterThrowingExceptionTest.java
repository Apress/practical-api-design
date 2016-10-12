
package org.apidesign.delegatingwriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class BufferedWriterThrowingExceptionTest {
    private StringWriter writer;
    
    public BufferedWriterThrowingExceptionTest() {
    }

    @Before
    public void setUp() {
        writer = new StringWriter();
    }
    
    @Test
    public void testBehaviourOfRealBufferInJDK() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        doAppendHello(bufferedWriter, "Hello!");
        bufferedWriter.flush();
        
        assertEquals("Hello!", writer.toString());
    }

    @Test
    public void testBehaviourOfBufferThatThrowsExceptionWhenAppendIsCalled() throws IOException {
        BufferedWriter bufferedWriter = new AltBufferedWriter(writer, AltBufferedWriter.Behaviour.THROW_EXCEPTION);
        doAppendHello(bufferedWriter, "Hello!");
        bufferedWriter.flush();
        
        assertEquals("Hello!", writer.toString());
    }

    @Test
    public void testBehaviourWhenDelegatingConditionallyIsOK() throws IOException {
        BufferedWriter bufferedWriter = new AltBufferedWriter(writer, AltBufferedWriter.Behaviour.DELEGATE_CONDITIONALLY);
        doAppendHello(bufferedWriter, "Hello!");
        bufferedWriter.flush();
        
        assertEquals("Hello!", writer.toString());
    }

    private void doAppendHello(BufferedWriter bufferedWriter, CharSequence what) throws IOException {
        // BEGIN: writer.throw.client
        try {
            bufferedWriter.append(what);
        } catch (UnsupportedOperationException ex) {
            bufferedWriter.write(what.toString());
        }
        // END: writer.throw.client
    }
    
}