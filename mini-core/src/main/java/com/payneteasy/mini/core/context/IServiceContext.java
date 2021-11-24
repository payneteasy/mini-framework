package com.payneteasy.mini.core.context;

public interface IServiceContext {

    <T> T singleton(Class<? super T> aClass, IServiceCreator<T> aCreator);

}
