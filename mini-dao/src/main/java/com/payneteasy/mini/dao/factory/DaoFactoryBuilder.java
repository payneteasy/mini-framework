package com.payneteasy.mini.dao.factory;

import com.googlecode.jdbcproc.daofactory.DaoMethodInfoFactory;
import com.googlecode.jdbcproc.daofactory.IMetaLoginInfoService;
import com.googlecode.jdbcproc.daofactory.StoredProcedureDaoFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.prometheus.PrometheusHistogramMetricsTrackerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoFactoryBuilder {

    private IMetaLoginInfoService metaLoginInfoService;
    private DaoDatabaseConfig     daoDatabaseConfig;

    public DaoFactoryBuilder daoStartupConfig(IDaoStartupConfig aConfig) {
        daoDatabaseConfig = DaoDatabaseConfig.builder()
                .jdbcUrl(aConfig.jdbcUrl())
                .username(aConfig.jdbcUsername())
                .password(aConfig.jdbcPassword())
                .connectionInitQuery(aConfig.jdbcConnectionInitQuery())
                .build();
        return this;
    }

    public DaoFactoryBuilder daoDatabaseConfig(DaoDatabaseConfig aConfig) {
        daoDatabaseConfig = aConfig;
        return this;
    }

    public DaoFactoryBuilder metaLoginInfoService(IMetaLoginInfoService aLoginService) {
        metaLoginInfoService = aLoginService;
        return this;
    }

    public IDaoFactory build() {

        HikariDataSource dataSource = new HikariDataSource(createHikariConfig(daoDatabaseConfig));

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        DaoMethodInfoFactory daoMethodFactory = new DaoMethodInfoFactory();
        daoMethodFactory.setJdbcTemplate(jdbcTemplate);
        daoMethodFactory.setMetaLoginInfoService(metaLoginInfoService);
        try {
            daoMethodFactory.afterPropertiesSet();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot init dao factory to " + daoDatabaseConfig.getJdbcUrl(), e);
        }

        StoredProcedureDaoFactoryBean beanFactory = new StoredProcedureDaoFactoryBean();
        beanFactory.setDaoMethodInfoFactory(daoMethodFactory);
        beanFactory.setJdbcTemplate(jdbcTemplate);

        return new DaoFactoryImpl(beanFactory, dataSource, dataSource);
    }

    @NotNull
    private static HikariConfig createHikariConfig(DaoDatabaseConfig aConfig) {
        HikariConfig poolConfig = new HikariConfig();
        poolConfig.setAutoCommit(true);
        poolConfig.setConnectionInitSql(aConfig.getConnectionInitQuery());
        poolConfig.setJdbcUrl(aConfig.getJdbcUrl());
        poolConfig.setUsername(aConfig.getUsername());
        poolConfig.setPassword(aConfig.getPassword());
        poolConfig.setMetricsTrackerFactory(new PrometheusHistogramMetricsTrackerFactory());
        return poolConfig;
    }


}
