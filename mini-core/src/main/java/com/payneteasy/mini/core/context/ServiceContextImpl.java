package com.payneteasy.mini.core.context;

import java.util.HashMap;
import java.util.Map;

public class ServiceContextImpl implements IServiceContext {

    private final Map<Class, Object> singletons = new HashMap<>();

    @Override
    public <T> T singleton(Class<? super T> aClass, IServiceCreator<T> aCreator) {
        //noinspection unchecked
        T service = (T) singletons.get(aClass);

        if (service == null) {
            //noinspection unchecked
            service = (T) aCreator.createService();
            singletons.put(aClass, service);
        }

        return service;
    }

}
