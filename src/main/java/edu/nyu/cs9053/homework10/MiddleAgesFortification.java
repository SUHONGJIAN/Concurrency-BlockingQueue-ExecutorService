package edu.nyu.cs9053.homework10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * User: blangel
 */
public class MiddleAgesFortification extends AbstractFortification<Thread> {

    private final BlockingQueue<Runnable> queue;

    private final Thread[] threads;

    public MiddleAgesFortification(int concurrencyFactor) {
        super(concurrencyFactor);
        this.queue = new ArrayBlockingQueue<>(concurrencyFactor);
        this.threads = new Thread[concurrencyFactor];
        this.activeAllThreads();            //active fixed threads to take items from blockingQueue
    }

    private void activeAllThreads() {
        for (int pointer = 0; pointer < getConcurrencyFactor(); pointer++) {
            threads[pointer] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            queue.take().run();            //consumer
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(ie);
                        }
                    }
                }
            });
            threads[pointer].start();
        }
    }

    @Override
    public void handleAttack(AttackHandler handler) {
        try {
            queue.put(new Runnable() {             //producer
                @Override
                public void run() {
                    handler.soldiersReady();
                }
            });
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ie);
        }
    }

    @Override
    public void surrender() {
        synchronized (this) {
            for (Thread thread : threads) {
                if (thread != null) {
                    thread.interrupt();
                }
            }
        }
    }

}
