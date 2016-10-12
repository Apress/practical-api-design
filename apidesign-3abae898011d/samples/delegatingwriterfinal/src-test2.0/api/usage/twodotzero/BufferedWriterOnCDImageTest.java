
package api.usage.twodotzero;

import api.Writer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
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
        AtomicInteger cnt = new AtomicInteger();
        Writer writer = CountingWriter.create(cnt);
        CDSequence cdImage = new CDSequence();
        Writer bufferedWriter = Writer.createBuffered(writer);
        bufferedWriter.append(cdImage);
        assertEquals("Correct number of writes delegated", cdImage.length(), cnt.get());
    }


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