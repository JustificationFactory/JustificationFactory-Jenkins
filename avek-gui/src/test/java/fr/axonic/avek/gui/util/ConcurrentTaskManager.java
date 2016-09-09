package fr.axonic.avek.gui.util;

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Nathaël N on 25/07/16.
 */
public class ConcurrentTaskManager {
    private static Thread platformThread;

    static {
        Platform.runLater(() -> platformThread = Thread.currentThread());
    }

    private static final Logger LOGGER = Logger.getLogger(ConcurrentTaskManager.class);
    private volatile static int taskCount = 0;
    private final Set<FutureTask> running = new HashSet<>();
    private final Map<Thread, Integer> threads = new HashMap<>();

    private void run(Runnable r, boolean onPlatform) throws Exception {
        this.run(() -> {
            r.run();
            return true;
        }, onPlatform);
    }

    private <V> void run(Callable<V> c, boolean onPlatform) throws Exception {
        if (Thread.currentThread() == platformThread && onPlatform) {
            c.call();
            return;
        }

        final FutureTask<V> ft = new FutureTask<>(() -> {
            int id = ++taskCount;

            LOGGER.debug("Task " + id + " started");
            V ret = c.call();
            LOGGER.debug("Task " + id + " finished");

            Thread t = Thread.currentThread();
            threads.put(t, threads.containsKey(t) ? threads.get(t) + 1 : 1);
            return ret;
        });

        if (onPlatform) {
            Platform.runLater(ft);
            threads.put(platformThread, threads.containsKey(platformThread) ? threads.get(platformThread) + 1 : 1);
        } else {
            new Thread(ft).start();
        }

        running.add(ft);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean runNowOnPlatform(Runnable r) throws Exception {
        return runNowOnPlatform(() -> {
            r.run();
            return true;
        });
    }

    public <V> V runNowOnPlatform(Callable<V> c) throws Exception {
        if (Thread.currentThread() == platformThread) {
            c.call();
        }

        FutureTask<V> ft = new FutureTask<>(c);
        Platform.runLater(ft);
        return ft.get();
    }

    public void waitForTasks() throws ExecutionException, InterruptedException {
        for (FutureTask ft : running) {
            waitForTask(ft);
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    private Object waitForTask(FutureTask ft) throws ExecutionException, InterruptedException {
        Object value = ft.get();
        running.remove(ft);
        return value;
    }
}
