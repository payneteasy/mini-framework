package com.payneteasy.mini.jsonstore;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ILongIdStore<T> {

    Optional<T> findFirst(long aId);

    List<T> listAll(Comparator<T> aComparator);

    void add(T aEntity);

    NotFoundPromise save(long aId, T aEntity);

    NotFoundPromise removeFirst(long aId);

}
