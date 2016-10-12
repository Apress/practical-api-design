package org.apidesign.exceptions.trycatchredo.usage;

import org.apidesign.exceptions.trycatchredo.api.UserQuestionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

// BEGIN: trycatchredo.stream
public final class QueryStream extends OutputStream {
    private ByteArrayOutputStream arr = new ByteArrayOutputStream();
    /** this field can be manipulated by the QueryException */
    Boolean reverse;

    @Override
    public synchronized void write(byte[] b, int off, int len)
    throws IOException {
        if (reverse == null) {
            throw new QueryException();
        }
        arr.write(b, off, len);
    }

    @Override
    public synchronized void write(int b) throws IOException {
        if (reverse == null) {
            throw new QueryException();
        }
        arr.write(b);
    }

    @Override
    public String toString() {
        if (reverse == null) {
            return "Reverse question was not answered yet!";
        }
        if (reverse) {
            StringBuilder sb = new StringBuilder();
            sb.append(arr.toString());
            sb.reverse();
            return sb.toString();
        }
        return arr.toString();
    }

    private class QueryException extends UserQuestionException {

        @Override
        public JOptionPane getQuestionPane() {
            JOptionPane p = new JOptionPane("Store in reverse way?");
            p.setOptionType(JOptionPane.YES_NO_CANCEL_OPTION);
            return p;
        }

        @Override
        public void confirm(Object option) {
            if (option.equals(JOptionPane.YES_OPTION)) {
                reverse = Boolean.TRUE;
                return;
            }
            if (option.equals(JOptionPane.NO_OPTION)) {
                reverse = Boolean.FALSE;
                return;
            }
        }
    }
}
// END: trycatchredo.stream
