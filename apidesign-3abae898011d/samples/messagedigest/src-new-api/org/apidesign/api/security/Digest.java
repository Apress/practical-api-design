
package org.apidesign.api.security;

import java.nio.ByteBuffer;
import java.util.ServiceLoader;
import org.apidesign.spi.security.Digestor;

/** Simplified version of a Digest class that allows to compute a fingerprint
 * for buffer of data.
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: day.end.bridges.Digest
public final class Digest {
    private final DigestImpl<?> impl;
    
    /** Factory method is better than constructor */
    private Digest(DigestImpl<?> impl) {
        this.impl = impl;
    }
    
    /** Factory method to create digest for an algorithm.
     */
    public static Digest getInstance(String algorithm) {
        for (Digestor<?> digestor : ServiceLoader.load(Digestor.class)) {
            DigestImpl<?> impl = DigestImpl.create(
                digestor, algorithm
            );
            if (impl != null) {
                return new Digest(impl);
            }
        }
        throw new IllegalArgumentException(algorithm);
    }
      
    //
    // these methods are kept the same as in original MessageDigest,
    // but for simplicity choose just some from the original API
    //
    
    public byte[] digest(ByteBuffer bb) {
        return impl.digest(bb);
    }
}
// END: day.end.bridges.Digest
