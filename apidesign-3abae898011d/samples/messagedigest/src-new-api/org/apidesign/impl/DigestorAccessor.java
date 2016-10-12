/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.impl;

import java.nio.ByteBuffer;
import org.apidesign.spi.security.Digestor;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public abstract class DigestorAccessor {
    private static DigestorAccessor INSTANCE;
    
    protected DigestorAccessor() {
        if (this.getClass().getName().equals("org.apidesign.spi.security.DigestorAccessorImpl")) {
            assert INSTANCE == null;
            INSTANCE = this;
            return;
        }
        if (this.getClass().getName().equals("org.apidesign.api.security.DigestorAccessorImpl")) {
            return;
        }
        throw new IllegalStateException();
    }
    
    final DigestorAccessor getDefault() {
        try {
            Class.forName(Digestor.class.getName(), true, DigestorAccessor.class.getClassLoader());
            return INSTANCE;
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    protected abstract <Data> byte[] digest(Digestor<Data> dig, Data data);
    protected abstract <Data> Data create(Digestor<Data> dig, String algorithm); 
    protected abstract <Data> void update(Digestor<Data> dig, Data data, ByteBuffer input);
    
    
    protected final <Data> byte[] defaultDigest(Digestor<Data> dig, Data data) {
        return getDefault().digest(dig, data);
    }
    protected final <Data> Data defaultCreate(Digestor<Data> dig, String algorithm) {
        return getDefault().create(dig, algorithm);
    }
    protected final <Data> void defaultUpdate(Digestor<Data> dig, Data data, ByteBuffer input) {
        getDefault().update(dig, data, input);
    }
}
