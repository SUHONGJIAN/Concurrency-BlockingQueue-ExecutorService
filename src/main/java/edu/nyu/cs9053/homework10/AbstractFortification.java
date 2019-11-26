package edu.nyu.cs9053.homework10;

public abstract class AbstractFortification<T> implements Fortification<T>, ConcurrencyFactorProvider {

    private final int concurrencyFactor;

    protected AbstractFortification(int concurrencyFactor) {
        this.concurrencyFactor = concurrencyFactor;
    }

    abstract public void handleAttack(AttackHandler handler);

    abstract public void surrender();

    @Override
    public int getConcurrencyFactor() {
        return concurrencyFactor;
    }

}
