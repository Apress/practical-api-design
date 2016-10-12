package org.apidesign.deadlock.logs;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

// BEGIN: test.parallel
class Parallel implements Runnable {
    public void run() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(r.nextInt(100));
            } catch (InterruptedException ex) {}
            Logger.global.log(Level.WARNING, "cnt: {0}", new Integer(i));
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Parallel(), "1st");
        Thread t2 = new Thread(new Parallel(), "2nd");
        t1.start(); t2.start();
        t1.join(); t2.join();
    }
}
// END: test.parallel
