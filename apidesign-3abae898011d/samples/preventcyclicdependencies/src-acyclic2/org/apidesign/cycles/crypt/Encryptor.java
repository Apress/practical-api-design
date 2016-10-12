package org.apidesign.cycles.crypt;

import org.apidesign.cycles.array.MutableArray;

public final class Encryptor {
    public void encode(byte[] arr) {
        MutableArray m = new MutableArray(arr);
        m.xor((byte)0x3d);
    }
}
