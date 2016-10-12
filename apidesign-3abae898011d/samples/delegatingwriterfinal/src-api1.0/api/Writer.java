package api;

import java.io.IOException;

/** Fixing the problem caused by mixing subclassing and delegation in 
 * the <code>java.io.BufferedWriter</code>
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: writer.final.1.0
public final class Writer {
    private final Impl impl;
    
    private Writer(Impl impl) {
        this.impl = impl;
    }
    public final void write(int c) throws IOException {
        char[] arr = { (char)c };
        impl.write(arr, 0, 1);
    }
    
    public final void write(char cbuf[]) throws IOException {
	impl.write(cbuf, 0, cbuf.length);
    }
    public final void write(char cbuf[], int off, int len) 
    throws IOException {
        impl.write(cbuf, off, len);
    }
    public final void write(String str) throws IOException {
	impl.write(str, 0, str.length());
    }
    public final void write(String str, int off, int len) 
    throws IOException {
        impl.write(str, off, len);
    }
    public final void flush() throws IOException {
        impl.flush();
    }
    public final void close() throws IOException {
        impl.close();
    }

    public static Writer create(Impl impl) {
        return new Writer(impl);
    }
    
    public static Writer create(final java.io.Writer w) {
        return new Writer(new Impl() {
            public void write(String str, int off, int len) 
            throws IOException {
                w.write(str, off, len);
            }

            public void write(char[] arr, int off, int len) 
            throws IOException {
                w.write(arr, off, len);
            }

            public void close() throws IOException {
                w.close();
            }

            public void flush() throws IOException {
                w.flush();
            }
        });
    }
    
    public static Writer createBuffered(final Writer out) {
        return create(new SimpleBuffer(out));
    }

    
    public static interface Impl {
        public void close() throws IOException;
        public void flush() throws IOException;
        public void write(String s, int off, int len) throws IOException;
        public void write(char[] a, int off, int len) throws IOException;
    }
}
// END: writer.final.1.0
