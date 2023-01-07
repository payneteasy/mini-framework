package com.payneteasy.mini.jsonstore;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ISimpleStore<T> {

    Optional<T> findFirst(Predicate<T> aPredicate);

    List<T> listAll(Comparator<T> aComparator);

    void add(T aEntity);

    NotFoundPromise save(T aEntity, Predicate<T> aPredicate);

    NotFoundPromise removeFirst(Predicate<T> aPredicate);
}
