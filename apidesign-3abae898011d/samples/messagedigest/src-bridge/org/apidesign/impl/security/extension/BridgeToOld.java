package org.apidesign.impl.security.extension;

import org.apidesign.api.security.Digest;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: day.end.bridges.BridgeToOld
public final class BridgeToOld extends Provider {

    public BridgeToOld() {
        super("spi.Digestor", 1.0, "");
        Security.addProvider(this);
    }

    // BEGIN: day.end.bridges.cycle
    private ThreadLocal<Boolean> searching = new ThreadLocal<Boolean>();
    final boolean isSearching() {
        return Boolean.TRUE.equals(searching.get());
    }
    
    @Override
    public synchronized Service getService(String type, String algorithm) {
        Boolean prev = searching.get();
        try {
            searching.set(Boolean.TRUE);
            if ("MessageDigest".equals(type)) {
                Digest dig = Digest.getInstance(algorithm);
                if (dig != null) {
                    return new ServiceImpl(
                        dig, this, type, algorithm, "", 
                        Collections.<String>emptyList(), 
                        Collections.<String,String>emptyMap());
                }
            }
            return null;
        } finally {
            searching.set(prev);
        }
    }
    // END: day.end.bridges.cycle

    private static class ServiceImpl<Data> extends Service {
        Digest dig;

        public ServiceImpl(Digest dig, Provider provider, 
            String type, String algorithm, String className, 
            List<String> aliases, Map<String, String> attributes
        ) {
            super(
                provider, type, algorithm, className, aliases, attributes
            );
            this.dig = dig;
        }

        @Override
        public Object newInstance(Object constructorParameter) 
        throws NoSuchAlgorithmException {
            return new MessageDigest(getAlgorithm()) {
                private byte[] res;
                
                @Override
                protected void engineUpdate(byte input) {
                    ByteBuffer bb = ByteBuffer.wrap(new byte[] { input });
                    res = dig.digest(bb);
                }

                @Override
                protected void engineUpdate(
                    byte[] input, int offset, int len
                ) {
                    ByteBuffer bb = ByteBuffer.wrap(input);
                    bb.position(offset);
                    bb.limit(offset + len);
                    res = dig.digest(bb);
                }

                @Override
                protected byte[] engineDigest() {
                    return res;
                }

                @Override
                protected void engineReset() {
                    dig = Digest.getInstance(getAlgorithm());
                }
            };
        }
        
        
    }

}
// END: day.end.bridges.BridgeToOld
