package Concurrency;

import java.util.ArrayDeque;
import java.util.logging.Logger;

public class ThreadPool {
    private int maxSize;
    private boolean running;

    private Logger log;
    private PoolManager manager;
    private ArrayDeque<StoppableThread> pool;
    private final Object deathNotifier = new Object();

    public ThreadPool(int maxSize, Logger log) {
        this.maxSize = maxSize;
        this.running = true;
        this.log = log;
        this.manager = new PoolManager();
        this.pool = new ArrayDeque<StoppableThread>();
    }

    public void stop() {
        this.running = false;
        synchronized (deathNotifier) {
            deathNotifier.notify();

            for (StoppableThread thread : pool) {
                if (thread.isAlive()) {
                    thread.slowStop();
                }
            }

            pool = new ArrayDeque<StoppableThread>();
        }
    }

    public boolean requestThread(StoppableThread thread) {
        if (pool.size() < maxSize) {
            synchronized (deathNotifier) {
                thread.setNotifier(deathNotifier);
                thread.start();
                pool.add(thread);
            }
            return true;
        } else
        {
            return false;
        }

    }

    private class PoolManager implements Runnable {
        public void run() {
            while (running) {
                synchronized (deathNotifier) {
                    try {
                        deathNotifier.wait();
                        if (!running) {
                            return;
                        }
                    } catch (InterruptedException e) {
                        log.severe(e.getLocalizedMessage());
                    }

                    ArrayDeque<StoppableThread> remove = new ArrayDeque<StoppableThread>();
                    for (StoppableThread thread: pool) {
                        if (!thread.isAlive()) {
                            remove.add(thread);
                        }
                    }

                    for (StoppableThread thread: remove) {
                        pool.remove(thread);
                    }
                }
            }
        }
    }
}
