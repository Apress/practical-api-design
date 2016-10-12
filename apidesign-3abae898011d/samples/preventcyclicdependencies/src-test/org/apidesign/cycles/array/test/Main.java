package org.apidesign.cycles.array.test;

import org.apidesign.cycles.array.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apidesign.cycles.crypt.Encryptor;

public class Main {
    public static void main(String[] args) throws Exception {
        byte[] five = { 1, 2, 3, 4, 5 };
        MutableArray arr = new MutableArray(five);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        arr.encrypt(os);
        
        byte[] out = os.toByteArray();
        assert out.length == 5;
        
        for (int i = 0; i < 5; i++) {
            int exp = five[i] ^ 0x3d;
            if (exp != out[i]) {
                assert false : "Index: " + i + " exp: " + exp + " was: " + out[i];
            }
        }
        System.err.println("OK");
    }
}

