package com.payneteasy.mini.jsonstore;

import java.util.function.Supplier;

public class NotFoundPromise {

    public static final NotFoundPromise NOT_FOUND_PROMISE = new NotFoundPromise(State.NOT_FOUND);
    public static final NotFoundPromise FOUND_PROMISE     = new NotFoundPromise(State.FOUND);

    public enum State {
        FOUND, NOT_FOUND
    }

    private final State state;

    private NotFoundPromise(State state) {
        this.state = state;
    }

    public <X extends Throwable> void onNotFound(Supplier<? extends X> exceptionSupplier) throws X {
        if (state == State.NOT_FOUND) {
            throw exceptionSupplier.get();
        }
    }

    public State getState() {
        return state;
    }
}
