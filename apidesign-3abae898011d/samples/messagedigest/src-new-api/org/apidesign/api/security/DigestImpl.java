/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.api.security;

import java.nio.ByteBuffer;
import org.apidesign.spi.security.Digestor;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: day.end.bridges.DigestImpl
final class DigestImpl<Data> {
    private static DigestorAccessorImpl ACCESSOR = new DigestorAccessorImpl();
    
    private final Digestor<Data> digestor;
    private final String algorithm;
    private Data data;
    
    private DigestImpl(Digestor<Data> digestor, String algorithm, Data d) {
        this.digestor = digestor;
        this.algorithm = algorithm;
        this.data = d;
    }
    
    static <Data> DigestImpl create(
        Digestor<Data> digestor, String algorithm
    ) {
        // indirectly calls digestor.create(algorithm)
        Data d = ACCESSOR.create(digestor, algorithm);
        if (d == null) {
            return null;
        } else {
            return new DigestImpl(digestor, algorithm, d);
        }
    }

    byte[] digest(ByteBuffer bb) {
        // indirectly calls digestor.update(data, bb)
        ACCESSOR.update(digestor, data, bb);
        // indirectly calls digestor.digest(data)
        return ACCESSOR.digest(digestor, data);
    }
}
// END: day.end.bridges.DigestImpl