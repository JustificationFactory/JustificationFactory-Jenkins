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

	private final static Logger logger = Logger.getLogger(ConcurrentTaskManager.class);
	private volatile static int taskCount = 0;
	private final Set<FutureTask> running = new HashSet<>();
	private final Map<Thread,Integer> threads = new HashMap<>();

	private void run(Runnable r, boolean onPlatform) {
		this.run(() -> {r.run(); return true;},	onPlatform);
	}
	private <V> void run(Callable<V> c, boolean onPlatform) {
		if (Thread.currentThread() == platformThread && onPlatform) {
			try {
				c.call();
			} catch (Exception e) {
				logger.error("Exception thrown during task call", e);
			}
			return;
		}


		final FutureTask<V> ft = new FutureTask<>(() -> {
			int id = ++taskCount;

			logger.debug("Task " + id + " started");
			V ret = c.call();
			logger.debug("Task " + id + " finished");

			Thread t = Thread.currentThread();
			threads.put(t, threads.containsKey(t) ? threads.get(t) + 1 : 1);
			return ret;
		});

		if(onPlatform) {
			Platform.runLater(ft);
			threads.put(platformThread, threads.containsKey(platformThread) ? threads.get(platformThread) + 1 : 1);
		}
		else
			new Thread(ft).start();

		running.add(ft);
	}

	public void runNowOnPlatform(Runnable r) {
		runNowOnPlatform(() -> {r.run(); return true;});
	}
	public <V> V runNowOnPlatform(Callable<V> c) {
		try {
			if(Thread.currentThread() == platformThread)
				c.call();

			FutureTask<V> ft = new FutureTask<>(c);
			Platform.runLater(ft);
			return ft.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void runLaterOnPlatform(Runnable r) {
		run(r, true);
	}
	public <V> void runLaterOnPlatform(Callable<V> c) {
		run(c, true);
	}
	public void runLaterOnThread(Runnable r) {
		run(r, false);
	}
	public <V> void runLaterOnThread(Callable<V> c) {
		run(c, false);
	}

	public void waitForTasks() {
		new HashSet<>(running).forEach(this::waitForTask);
	}
	private void waitForTask(FutureTask ft) {
		try {
			ft.get();
		} catch (InterruptedException | ExecutionException e) {
			logger.error("Future task failed", e);
		}
		running.remove(ft);
	}
}
