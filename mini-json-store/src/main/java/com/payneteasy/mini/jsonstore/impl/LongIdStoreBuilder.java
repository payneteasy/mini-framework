package com.payneteasy.mini.jsonstore.impl;

import com.google.gson.Gson;
import com.payneteasy.mini.jsonstore.IFileLocator;
import com.payneteasy.mini.jsonstore.ILongIdStore;

import java.io.File;
import java.util.function.Function;
import java.util.function.Supplier;

public class LongIdStoreBuilder<T> {

    private Class<T>     type;
    private IFileLocator fileLocator;
    private Gson         gson;
    private Function<T, Long> identity;

    public LongIdStoreBuilder(Class<T> type) {
        this.type = type;
    }

    public LongIdStoreBuilder<T> fileLocator(IFileLocator aLocator) {
        fileLocator = aLocator;
        return this;
    }

    public LongIdStoreBuilder<T> securityLocator(File aDir, String aFilename, Supplier<String> aUsernameFinder) {
        fileLocator = new SecurityContextFileLocator(aDir, aFilename, aUsernameFinder);
        return this;
    }

    public ILongIdStore<T> build() {
        SimpleStoreGsonImpl<T> storeGson = new SimpleStoreGsonImpl<>(type, fileLocator, gson);
        return new LongIdStoreImpl<>(storeGson, identity);
    }


    public LongIdStoreBuilder<T> gson(Gson aGson) {
        gson = aGson;
        return this;
    }

    public LongIdStoreBuilder<T> identity(Function<T, Long> aIdentity) {
        identity = aIdentity;
        return this;
    }
}
