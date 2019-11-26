package edu.nyu.cs9053.homework10;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * User: blangel
 */
public class ModernFortification extends AbstractFortification<ExecutorService> {

    private final ExecutorService executor;

    public ModernFortification(int concurrencyFactor) {
        super(concurrencyFactor);
        this.executor = Executors.newFixedThreadPool(concurrencyFactor);
    }

    @Override
    public void handleAttack(AttackHandler handler) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                handler.soldiersReady();
            }
        });
    }

    @Override
    public void surrender() {
        executor.shutdown();
    }

}
