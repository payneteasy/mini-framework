package com.payneteasy.mini.dao.factory;

import com.googlecode.jdbcproc.daofactory.StoredProcedureDaoFactoryBean;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class DaoFactoryImpl implements IDaoFactory {

    private final StoredProcedureDaoFactoryBean beanFactory;
    private final DataSource                    dataSource;
    private final Closeable                     dataSourceCloseable;

    public DaoFactoryImpl(StoredProcedureDaoFactoryBean beanFactory, DataSource dataSource, Closeable dataSourceCloseable) {
        this.beanFactory = beanFactory;
        this.dataSource = dataSource;
        this.dataSourceCloseable = dataSourceCloseable;
    }

    @NotNull
    public <T> T createDao(Class<T>  aDaoClass) {
        try {
            beanFactory.setInterface(aDaoClass);
            //noinspection unchecked
            return (T)beanFactory.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create " + aDaoClass, e);
        }
    }

    public void closeDataSource() {
        try {
            dataSourceCloseable.close();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot close data source " + dataSource, e);
        }
    }
}
