package org.apidesign.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

public class ExecutorServiceTest implements Runnable {
    private int cnt;
    
    @Test
    public void showUsageOfExecutionService() throws Exception {
        Runnable someRunnable = this;
        
        // BEGIN: deadlock.service
        ExecutorService service = Executors.newSingleThreadExecutor();

        // scheduling is quite different
        Future<?> task = service.submit(someRunnable);

        // compared to waiting
        task.get(1000, TimeUnit.MILLISECONDS);
        // END: deadlock.service
        
        Assert.assertEquals("Cnt executed", 1, cnt);
    }

    public void run() {
        cnt++;
    }

}
