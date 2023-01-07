package com.payneteasy.mini.jsonstore.impl;

import com.payneteasy.mini.jsonstore.ISimpleStore;
import com.payneteasy.mini.jsonstore.IStringIdStore;
import com.payneteasy.mini.jsonstore.NotFoundPromise;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringIdStoreImpl<T> implements IStringIdStore<T> {

    private final ISimpleStore<T>     store;
    private final Function<T, String> identity;

    public StringIdStoreImpl(ISimpleStore<T> store, Function<T, String> identity) {
        this.store    = store;
        this.identity = identity;
    }

    @Override
    public Optional<T> findFirst(String aId) {
        return store.findFirst(t -> identity.apply(t).equals(aId));
    }

    @Override
    public Optional<T> findFirst(Predicate<T> aPredicate) {
        return store.findFirst(aPredicate);
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
    public NotFoundPromise save(String aId, T aEntity) {
        return store.save(aEntity, t -> identity.apply(t).equals(aId));
    }

    @Override
    public NotFoundPromise removeFirst(String aId) {
        return store.removeFirst(t -> identity.apply(t).equals(aId));
    }
}
