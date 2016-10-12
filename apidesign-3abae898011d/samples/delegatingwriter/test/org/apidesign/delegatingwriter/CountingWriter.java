
package org.apidesign.delegatingwriter;

import java.io.IOException;
import java.io.Writer;

// BEGIN: writer.CountingWriter
/** Writer that counts the number of written in characters.
 */
public class CountingWriter extends Writer {
    private int counter;
    
    
    public int getCharacterCount() {
        return counter;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        counter += len;
    }

    @Override
    public Writer append(CharSequence csq) throws IOException {
        counter += csq.length();
        return this;
    }
// FINISH: writer.CountingWriter

    @Override
    public Writer append(CharSequence csq, int start, int end) throws IOException {
        counter += (end - start);
        return this;
    }

    @Override
    public Writer append(char c) throws IOException {
        counter++;
        return this;
    }

    @Override
    public void write(int c) throws IOException {
        counter++;
    }

    @Override
    public void write(char[] cbuf) throws IOException {
        counter += cbuf.length;
    }

    @Override
    public void write(String str) throws IOException {
        counter += str.length();
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        counter += len;
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

}
