package com.payneteasy.mini.jsonstore.impl;


import com.payneteasy.mini.jsonstore.ILongIdStore;
import com.payneteasy.mini.jsonstore.ISimpleStore;
import com.payneteasy.mini.jsonstore.NotFoundPromise;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class LongIdStoreImpl<T> implements ILongIdStore<T> {

    private final ISimpleStore<T>   store;
    private final Function<T, Long> identity;

    public LongIdStoreImpl(ISimpleStore<T> store, Function<T, Long> identity) {
        this.store    = store;
        this.identity = identity;
    }

    @Override
    public Optional<T> findFirst(long aId) {
        return store.findFirst(t -> identity.apply(t) == aId);
    }

    @Override
    public List<T> listAll(Comparator<T> aComparator) {
        return store.listAll(aComparator);
    }

    @Override
    public void add(T aEntity) {
        store.add(aEntity);
    }

    @Override
    public NotFoundPromise save(long aId, T aEntity) {
        return store.save(aEntity, t -> identity.apply(t) == aId);
    }

    @Override
    public NotFoundPromise removeFirst(long aId) {
        return store.removeFirst(t -> identity.apply(t) == aId);
    }
}
