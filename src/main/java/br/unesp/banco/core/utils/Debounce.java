package br.unesp.banco.core.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Debounce implements Runnable {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Runnable runnable;
    private final long timeout;
    private long lastTime;
    private boolean isQueued = false;

    public Debounce(Runnable runnable, long timeout) {
        this.runnable = runnable;
        this.timeout = timeout;
    }

    @Override
    public synchronized void run() {
        long currentTime = System.currentTimeMillis();
        if (!isQueued) {
            if (shouldRun(currentTime)) {
                lastTime = currentTime;
                runnable.run();
            } else {
                isQueued = true;
                schedule(this::runScheduled, timeout);
            }
        }
    }

    private synchronized void runScheduled() {
        lastTime = System.currentTimeMillis();
        isQueued = false;
        runnable.run();
    }

    private boolean shouldRun(long currentTime) {
        if (lastTime == -1) return false;
        return lastTime + timeout < currentTime;
    }

    private void schedule(Runnable runnable, long timeout) {
        scheduler.schedule(runnable, timeout, TimeUnit.MILLISECONDS);
    }
}
