package com.payneteasy.mini.jsonstore.impl;

import com.google.gson.Gson;
import com.payneteasy.mini.jsonstore.IFileLocator;
import com.payneteasy.mini.jsonstore.IStringIdStore;

import java.io.File;
import java.util.function.Function;
import java.util.function.Supplier;

public class StringIdStoreBuilder<T> {

    private Class<T>     type;
    private IFileLocator fileLocator;
    private Gson         gson;
    private Function<T, String> identity;

    public StringIdStoreBuilder(Class<T> type) {
        this.type = type;
    }

    public StringIdStoreBuilder<T> fileLocator(IFileLocator aLocator) {
        fileLocator = aLocator;
        return this;
    }

    public StringIdStoreBuilder<T> securityLocator(File aDir, String aFilename, Supplier<String> aUsernameFinder) {
        fileLocator = new SecurityContextFileLocator(aDir, aFilename, aUsernameFinder);
        return this;
    }

    public IStringIdStore<T> build() {
        SimpleStoreGsonImpl<T> storeGson = new SimpleStoreGsonImpl<>(type, fileLocator, gson);
        return new StringIdStoreImpl<>(storeGson, identity);
    }


    public StringIdStoreBuilder<T> gson(Gson aGson) {
        gson = aGson;
        return this;
    }

    public StringIdStoreBuilder<T> identity(Function<T, String> aIdentity) {
        identity = aIdentity;
        return this;
    }
}
