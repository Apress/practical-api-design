// BEGIN: cycles.ma1
package org.apidesign.cycles.array;

import java.io.IOException;
import java.io.OutputStream;
import org.apidesign.cycles.crypt.Encryptor;

public class MutableArray {
    private byte[] arr;

    public MutableArray(byte[] arr) {
        this.arr = arr;
    }

    public void xor(byte b) {
        for (int i = 0; i < arr.length; i++) { arr[i] ^= b; }
    }

    public void and(byte b) {
        for (int i = 0; i < arr.length; i++) { arr[i] &= b; }
    }

    public void or(byte b) {
        for (int i = 0; i < arr.length; i++) { arr[i] |= b; }
    }

    public void encrypt(OutputStream os) throws IOException {
        Encryptor en = new Encryptor();
        byte[] clone = (byte[]) arr.clone();
        en.encode(clone);
        os.write(clone);
    }
}
// END: cycles.ma1

