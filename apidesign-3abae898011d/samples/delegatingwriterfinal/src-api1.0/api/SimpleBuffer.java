package api;

import java.io.IOException;

/**
 *
 * @author Jaroslav Tulach
 */
final class SimpleBuffer implements Writer.Impl {
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
        out.write(sb.toString());
        sb.setLength(0);
        out.flush();
    }

    public void write(String str, int off, int len) throws IOException {
        sb.append(str, len, len);
    }

    public void write(char[] arr, int off, int len) throws IOException {
        sb.append(arr, len, len);
    }

}
