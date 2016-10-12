
package api.usage.twodotzero;

import api.Writer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/** Writer that counts the number of written in characters.
 */
public class CountingWriter implements Writer.ImplSeq {
    private final AtomicInteger counter;
    
    private CountingWriter(AtomicInteger counter) {
        this.counter = counter;
    }
    
    public static Writer create(AtomicInteger result) {
        return Writer.create(new CountingWriter(result));
    }

    @Override
    public void write(CharSequence csq) throws IOException {
        counter.addAndGet(csq.length());
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

}
