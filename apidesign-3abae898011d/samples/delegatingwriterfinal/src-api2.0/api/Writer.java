package api;

import java.io.IOException;

/** Fixing the problem caused by mixing subclassing and delegation in 
 * the <code>java.io.BufferedWriter</code>
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: writer.final.2.0
public final class Writer implements Appendable {
    private final Impl impl;
    private final ImplSeq seq;
    
    private Writer(Impl impl, ImplSeq seq) {
        this.impl = impl;
        this.seq = seq;
    }
    public final void write(int c) throws IOException {
        if (impl != null) {
            char[] arr = {(char) c};
            impl.write(arr, 0, 1);
        } else {
            seq.write(new CharSeq(c));
        }
    }
    
    public final void write(char cbuf[]) throws IOException {
        if (impl != null) {
            impl.write(cbuf, 0, cbuf.length);
        } else {
            seq.write(new CharSeq(cbuf, 0, cbuf.length));
        }
    }
    public final void write(char cbuf[], int off, int len) 
    throws IOException {
        if (impl != null) {
            impl.write(cbuf, off, len);
        } else {
            seq.write(new CharSeq(cbuf, off, len));
        }
    }
    public final void write(String str) throws IOException {
        if (impl != null) {
            impl.write(str, 0, str.length());
        } else {
            seq.write(str);
        }
    }
    public final void write(String str, int off, int len) 
    throws IOException {
        if (impl != null) {
            impl.write(str, off, len);
        } else {
            seq.write(str.subSequence(off, off + len));
        }
    }

    public final void flush() throws IOException {
        if (impl != null) {
            impl.flush();
        } else {
            seq.flush();
        }
    }
    public final void close() throws IOException {
        if (impl != null) {
            impl.close();
        } else {
            seq.flush();
        }
    }

    
    
    public final Writer append(CharSequence csq) throws IOException {
        if (impl != null) {
            String s = csq == null ? "null" : csq.toString();
            impl.write(s, 0, s.length());
        } else {
            seq.write(csq);
        }
        return this;
    }

    public final Writer append(CharSequence csq, int start, int end) 
    throws IOException {
        return append(csq.subSequence(start, end));
    }

    public final Writer append(char c) throws IOException {
        write(c);
        return this;
    }

    public static Writer create(Impl impl) {
        return new Writer(impl, null);
    }

    public static Writer create(ImplSeq seq) {
        return new Writer(null, seq);
    }
    
    public static Writer create(final java.io.Writer w) {
        return new Writer(null, new ImplSeq() {
            public void write(CharSequence seq) throws IOException {
                w.append(seq);
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
        public void write(String str, int off, int len) throws IOException;
        public void write(char[] arr, int off, int len) throws IOException;
    }
    public static interface ImplSeq {
        public void close() throws IOException;
        public void flush() throws IOException;
        public void write(CharSequence seq) throws IOException;
    }
}
// END: writer.final.2.0