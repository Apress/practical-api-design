package org.apidesign.cycles.array;

import java.io.IOException;
import java.io.OutputStream;
import org.openide.util.Lookup;
// BEGIN: nocycles.ma2
public class MutableArray {
    private byte[] arr;

    public MutableArray(byte[] arr) {
        this.arr = arr;
    }

    public void xor(byte b) {
        for (int i = 0; i < arr.length; i++) { arr[i] ^= b; }
    }

    public void encrypt(OutputStream os) throws IOException {
        DoEncode en = Lookup.getDefault().lookup(DoEncode.class);
        assert en != null : "org.netbeans.example.crypt missing!";
        byte[] clone = (byte[]) arr.clone();
        en.encode(clone);
        os.write(clone);
    }    
}
// END: nocycles.ma2
