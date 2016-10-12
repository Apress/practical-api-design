package api;

import java.io.IOException;

/**
 *
 * @author Jaroslav Tulach
 */
final class SimpleBuffer implements Writer.ImplSeq {
    private final Writer out;
    private final StringBuffer sb = new StringBuffer();
    
    public SimpleBuffer(Writer out) {
        this.out = out;
    }

    public void close() throws IOException {
        flush();
        out.close();
    }

    public void flush() throws IOException {
        if (sb.length() > 0) {
            out.write(sb.toString());
            sb.setLength(0);
            out.flush();
        }
    }

    public void write(CharSequence seq) throws IOException {
        if (shouldBufferAsTheSequenceIsNotTooBig(seq)) {
            sb.append(seq);
        } else {
            flush();
            out.append(seq);
        }
    }

    /** At the end the purpose of BufferedWriter is to buffer writes, this
     * method is here to decide when it is OK to prefer buffering and when 
     * it is better to delegate directly into the underlaying stream.
     * 
     * @param csq the seqence to evaluate
     * @return true if buffering from super class should be used
     */
    private static boolean shouldBufferAsTheSequenceIsNotTooBig(CharSequence csq) {
        if (csq == null) {
            return false;
        }
        // as buffers are usually bigger than 1024, it makes sense to 
        // pay the penalty of converting the sequence to string, but buffering
        // the write
        if (csq.length() < 1024) {
            return true;
        } else {
            // otherwise, just directly write down the char sequence
            return false;
        }
    }
    
}
