/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.impl.security.friendapi;

import java.nio.ByteBuffer;

/**
 *
 * @author Jaroslav Tulach
 */
public abstract class DigestImplementation {
    final String name;
    
    protected DigestImplementation(String algorithm) {
        this.name = algorithm;
    }
    
    public abstract void update(ByteBuffer bb);
    public abstract byte[] digest();
}
