package com.payneteasy.mini.dao.factory;

public interface IDaoFactory {

    <T> T createDao(Class<T> aDaoClass);

    void closeDataSource();
}
