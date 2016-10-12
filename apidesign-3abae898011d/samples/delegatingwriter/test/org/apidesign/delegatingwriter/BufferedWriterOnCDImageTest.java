
package org.apidesign.delegatingwriter;

import java.io.BufferedWriter;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/** Emulates what goes wrong when one wants to process really large character 
 * sequence.
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class BufferedWriterOnCDImageTest {
    
    public BufferedWriterOnCDImageTest() {
    }

    @Test
    public void testBehaviourOfRealBufferInJDKObviouslyThisIsGoingToThrowOutOfMemoryError() throws IOException {
        if (Boolean.getBoolean("no.failures")) return;
        // BEGIN: writer.countcd
        CountingWriter writer = new CountingWriter();
        CDSequence cdImage = new CDSequence();
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.append(cdImage);
        assertEquals(
            "Correct number of writes delegated", 
            cdImage.length(), writer.getCharacterCount()
        );
        // END: writer.countcd
    }

    @Test
    public void testBehaviourOfBufferThatDelegatesToAppend() throws IOException {
        CountingWriter writer = new CountingWriter();
        CDSequence cdImage = new CDSequence();
        BufferedWriter bufferedWriter = new AltBufferedWriter(
            writer, AltBufferedWriter.Behaviour.DELEGATE_TO_OUT
        );
        bufferedWriter.append(cdImage);
        assertEquals(
            "Correct number of writes delegated", 
            cdImage.length(), 
            writer.getCharacterCount()
        );
    }
    
    @Test
    public void testBehaviourWhenDelegatingConditionallyIsOK() throws IOException {
        CountingWriter writer = new CountingWriter();
        CDSequence cdImage = new CDSequence();
        BufferedWriter bufferedWriter = new AltBufferedWriter(
            writer, AltBufferedWriter.Behaviour.DELEGATE_CONDITIONALLY
        );
        bufferedWriter.append(cdImage);
        assertEquals(
            "Correct number of writes delegated", 
            cdImage.length(), 
            writer.getCharacterCount()
        );
    }
    

// BEGIN: writer.bigseq
    /** A "lazy" sequence of characters, for example one that can represent
     * content of a CD, read it lazily, do not fit all into memory at once.
     */
    private static final class CDSequence implements CharSequence {
        private final int start;
        private final int end;
        
        public CDSequence() {
            this(0, 647 * 1024 * 1024);
        }

        private CDSequence(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        
        public int length() {
            return end - start;
        }
// FINISH: writer.bigseq

        public char charAt(int index) {
            int ch = index % ('Z' - 'A' + 1);
            return (char) ('A' + ch);
        }

        public CharSequence subSequence(int start, int end) {
            return new CDSequence(start, end);
        }

        @Override
        public String toString() {
            char[] arr = new char[length()];
            for (int i = 0; i < length(); i++) {
                arr[i] = charAt(i);
            }
            return new String(arr);
        }
        
        
    } // end of CharSequence
}