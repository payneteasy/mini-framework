package com.payneteasy.mini.jsonstore.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.payneteasy.mini.jsonstore.IFileLocator;
import com.payneteasy.mini.jsonstore.ISimpleStore;
import com.payneteasy.mini.jsonstore.NotFoundPromise;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.payneteasy.mini.jsonstore.NotFoundPromise.FOUND_PROMISE;
import static com.payneteasy.mini.jsonstore.NotFoundPromise.NOT_FOUND_PROMISE;
import static java.nio.charset.StandardCharsets.UTF_8;

public class SimpleStoreGsonImpl<T> implements ISimpleStore<T> {

    private final Class<T>     type;
    private final IFileLocator fileLocator;
    private final Gson         gson;
    private final Type         listType;

    public SimpleStoreGsonImpl(Class<T> aType, IFileLocator aFileLocator, Gson aGson) {
        type        = aType;
        fileLocator = aFileLocator;
        gson        = aGson;
        listType    = TypeToken.getParameterized(List.class, aType).getType();
    }

    @Override
    public Optional<T> findFirst(Predicate<T> aPredicate) {
        return loadAll().stream().filter(aPredicate).findFirst();
    }

    @Override
    public List<T> listAll(Comparator<T> aComparator) {
        List<T> list = loadAll();
        list.sort(aComparator);
        return list;
    }

    @Override
    public void add(T aEntity) {
        List<T> list = loadAll();
        list.add(aEntity);
        save(list);
    }

    @Override
    public NotFoundPromise save(T aEntity, Predicate<T> aPredicate) {
        List<T> list = loadAll();
        for(int i=0; i<list.size(); i++) {
            T entry = list.get(i);
            if(aPredicate.test(entry)) {
                list.set(i, aEntity);
                save(list);
                return FOUND_PROMISE;
            }
        }
        return NOT_FOUND_PROMISE;
    }

    private void save(List<T> aList) {
        File file = fileLocator.locateFile();
        try {
            try(OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), UTF_8)) {
                gson.toJson(aList, out);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write to " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public NotFoundPromise removeFirst(Predicate<T> aPredicate) {
        List<T> list = loadAll();
        boolean removed = list.removeIf(aPredicate);
        save(list);
        return removed ? FOUND_PROMISE : NOT_FOUND_PROMISE;
    }

    private List<T> loadAll() {
        File file = fileLocator.locateFile();
        if(!file.exists()) {
            return new ArrayList<>();
        }

        try {
            try(InputStreamReader in = new InputStreamReader(new FileInputStream(file), UTF_8)) {
                return gson.fromJson(in, listType);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read from " + file.getAbsolutePath(), e);
        }
    }
}
