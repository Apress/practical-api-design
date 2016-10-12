package org.apidesign.impl.security.extension;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apidesign.spi.security.Digestor;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: day.end.bridges.BridgeToNew
public class BridgeToNew extends Digestor<MessageDigest> {
    /** initializes the other bridge, and allow us to eliminate stack 
     * overflow */
    private static final BridgeToOld oldBridge = new BridgeToOld();
    
    @Override
    protected MessageDigest create(String algorithm) {
        // BEGIN: day.end.bridges.cyclecheck
        if (oldBridge.isSearching()) {
            // if the call is initiated from the other bridge, do not do
            // any delegation
            return null;
        }
        // END: day.end.bridges.cyclecheck
        
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(BridgeToNew.class.getName()).log(
                Level.FINE, "Cannot find " + algorithm, ex
            );
            return null;
        }
    }

    @Override
    protected byte[] digest(MessageDigest data) {
        return data.digest();
    }

    @Override
    protected void update(MessageDigest data, ByteBuffer input) {
        data.update(input);
    }
    
    static {
        new BridgeToOld();
    }
}
// END: day.end.bridges.BridgeToNew
