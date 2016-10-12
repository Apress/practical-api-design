// BEGIN: nocycles.encrypt2
package org.apidesign.cycles.crypt;

import org.apidesign.cycles.array.DoEncode;

public class DoEncodeImpl implements DoEncode {
    public void encode(byte[] arr) {
        Encryptor en = new Encryptor();
        en.encode(arr);
    }
}
// END: nocycles.encrypt2
