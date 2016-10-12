package org.apidesign.deadlock.startuplock;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public final class CLIHandler {
    // BEGIN: cli.vision
    public static int start(File lockFile) throws IOException {
        if (lockFile.exists ()) {
            // read the port number and connect to it
            int alive = readPortNumber(lockFile);
            if (alive != -1) {
                // exit
                return alive;
            }
        }
        // otherwise try to create the file yourself
        lockFile.createNewFile();
        DataOutputStream os = new DataOutputStream(
            new FileOutputStream(lockFile)
        );
        ServerSocket server = new ServerSocket();
        int p = server.getLocalPort();
        os.writeInt(p);
        
        return p;
    }
    // END: cli.vision

    private static int readPortNumber(File lockFile) throws IOException {
        return -1;
    }
}
