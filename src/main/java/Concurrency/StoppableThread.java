package Concurrency;

import java.util.logging.Logger;

public abstract class StoppableThread implements Runnable {
    private Thread thread;
    private Logger log;
    private Object notifier;
    private boolean stop;
    private boolean running;

    public StoppableThread(Logger log) {
        this.log = log;
        this.thread = new Thread(this);
        this.stop = false;
        this.running = false;
    }

    void setNotifier(Object notifier) {
        this.notifier = notifier;
    }

    void start() {
        stop = false;
        thread.start();
        running = true;
    }

    public boolean isStopping() {
        return stop;
    }

    void slowStop() {
        stop = true;
        try {
            thread.join();
            synchronized (notifier) {
                running = false;
                notifier.notifyAll();
            }
        } catch (InterruptedException e) {
            log.severe(e.getLocalizedMessage());
        }
    }

    public void forceStop() {
        stop = true;
        while (thread.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.severe(e.getLocalizedMessage());
            }

            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
        synchronized (notifier) {
            running = false;
            notifier.notifyAll();
        }
    }

    public void awaitStop() {
        synchronized (notifier) {
            while (!running) {
                try {
                    notifier.wait();
                } catch (InterruptedException e) {
                    log.severe(e.getLocalizedMessage());
                }
            }
        }
    }

    public boolean isAlive() {
        return thread.isAlive();
    }
}
