package com.payneteasy.mini.jsonstore;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface IStringIdStore<T> {

    Optional<T> findFirst(Predicate<T> aPredicate);

    Optional<T> findFirst(String aId);

    List<T> listAll(Comparator<T> aComparator);

    void add(T aEntity);

    NotFoundPromise save(String aId, T aEntity);

    NotFoundPromise removeFirst(String aId);

}
