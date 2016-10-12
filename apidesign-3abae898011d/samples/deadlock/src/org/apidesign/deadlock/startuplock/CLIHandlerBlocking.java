package org.apidesign.deadlock.startuplock;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CLIHandlerBlocking {
    // BEGIN: cli.real
    public static int start(File lockFile) throws IOException {
        enterState(10);
        if (lockFile.exists ()) {
            // read the port number and connect to it
            enterState(11);
            int alive = readPortNumber(lockFile);
            if (alive != -1) {
                // exit
                return alive;
            }
        }
        // otherwise try to create the file yourself
        enterState(20);
        lockFile.createNewFile();
        DataOutputStream os = new DataOutputStream(
            new FileOutputStream(lockFile)
        );
        ServerSocket server = new ServerSocket();
        enterState(21);
        int p = server.getLocalPort();
        enterState(22);
        os.writeInt(p);
        enterState(23);
        
        return p;
    }
    // END: cli.real

    private static Logger LOG = Logger.getLogger(CLIHandlerBlocking.class.getName());
    private static void enterState(int state) {
        LOG.log(Level.FINEST, "enterState {0}", state);
    }

    private static int readPortNumber(File lockFile) throws IOException {
        return -1;
    }
}
