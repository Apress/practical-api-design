/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.nio.ByteBuffer;
import org.apidesign.spi.security.Digestor;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
// BEGIN: day.end.bridges.CountingDigestor
public final class CountingDigestor extends Digestor<int[]> {
    @Override
    protected byte[] digest(int[] data) {
        int i = data[0];
        byte[] arr = { 
            (byte) (i & 255), 
            (byte) ((i >> 8) & 255), 
            (byte) ((i >> 16) & 255), 
            (byte) ((i >> 24) & 255) 
        };
        return arr;
    }

    @Override
    protected int[] create(String algorithm) {
        return "cnt".equals(algorithm) ? new int[1] : null;
    }

    @Override
    protected void update(int[] data, ByteBuffer input) {
        data[0] += input.remaining();
        input.position(input.position() + input.remaining());
    }
}
// END: day.end.bridges.CountingDigestor