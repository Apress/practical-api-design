package org.apidesign.delegatingwriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This is a regular {@link BufferedWriter}, just its implementation
 * of the append method can choose from three options. This allows us to
 * simulate the potential pros and cons of various possible implementations.
 * 
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class AltBufferedWriter extends BufferedWriter {
    private final Writer out;
    private final Behaviour behaviour;
    
    public AltBufferedWriter(Writer out) {
        // behave exactly like BufferedWriter in 1.5 behaves
        this(out, Behaviour.DELEGATE_TO_SUPER);
    }
    public AltBufferedWriter(Writer out, Behaviour behaviour) {
        super(out);
        this.out = out;
        this.behaviour = behaviour;
    }
    
    @Override
    public Writer append(CharSequence csq) throws IOException {
        switch (behaviour) {
            case THROW_EXCEPTION: 
                return appendThrowException(csq); 
            case DELEGATE_TO_SUPER: 
                return appendDelegateToSuper(csq);
            case DELEGATE_TO_OUT: 
                return appendDelegateToUnderlaying(csq);
            case DELEGATE_CONDITIONALLY: 
                return appendDelegateConditionally(csq);
            default: 
                throw new IllegalStateException("Unknown" + behaviour);
        }
    }
    
    public Writer appendThrowException(CharSequence csq) throws IOException {
        /* in case of real code, this would be part of 
         the regular append method. BEGIN: writer.throw
    public Writer append(CharSequence csq) throws IOException {
        /* thrown an exception as this method is new and 
         subclasses need to override it */
        throw new UnsupportedOperationException();
    }
    // END: writer.throw
    
    public Writer appendDelegateToSuper(CharSequence csq) throws IOException {
        // non-efficient variant of delegating via converting to String first 
        // and using one of methods that existed in 1.4
        // BEGIN: writer.super
        if (csq == null) {
            write("null");
        } else {
            write(csq.toString());
        }
        return this;
        // END: writer.super
    }
    
    public Writer appendDelegateToUnderlaying(CharSequence csq) throws IOException {
        // BEGIN: writer.delegateout
        // efficient, yet dangerous delegation skipping methods unknown to 
        // subclasses that used version 1.4
        if (shouldBufferAsTheSequenceIsNotTooBig(csq)) {
            write(csq.toString());
        } else {
            flush();
            out.append(csq);
        }
        return this;
        // END: writer.delegateout
    }

    private Writer appendDelegateConditionally(CharSequence csq) 
    throws IOException {
        // BEGIN: writer.conditionally
        boolean isOverriden = false;
        try {
            isOverriden = 
                (
                    getClass().getMethod(
                        "write", String.class
                    ).getDeclaringClass() != Writer.class
                ) ||
                (
                    getClass().getMethod(
                        "write", Integer.TYPE
                    ).getDeclaringClass() != BufferedWriter.class
                ) ||
                (
                    getClass().getMethod(
                        "write", String.class, Integer.TYPE, Integer.TYPE
                    ).getDeclaringClass() != BufferedWriter.class
                );
        } catch (Exception ex) {
            throw new IOException(ex);
        }
        
        if (isOverriden || shouldBufferAsTheSequenceIsNotTooBig(csq)) {
            write(csq.toString());
        } else {
            flush();
            out.append(csq);
        }
        return this;
        // END: writer.conditionally
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
    
    public enum Behaviour {
        THROW_EXCEPTION, 
        DELEGATE_TO_SUPER, 
        DELEGATE_TO_OUT, 
        DELEGATE_CONDITIONALLY
    }
}
