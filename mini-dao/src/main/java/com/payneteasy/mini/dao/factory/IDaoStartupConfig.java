package com.payneteasy.mini.dao.factory;

import com.payneteasy.startup.parameters.AStartupParameter;

public interface IDaoStartupConfig {

    @AStartupParameter(name = "JDBC_URL", value = "jdbc:mysql://localhost:3306/mysql_db?characterEncoding=utf8&useInformationSchema=true&noAccessToProcedureBodies=false&useLocalSessionState=true&autoReconnect=false")
    String jdbcUrl();

    @AStartupParameter(name = "JDBC_USERNAME", value = "mysql_user")
    String jdbcUsername();

    @AStartupParameter(name = "JDBC_PASSWORD", value = "mysql_password", maskVariable = true)
    String jdbcPassword();

    @AStartupParameter(name = "JDBC_CONNECTION_INIT_QUERY", value = "select 1")
    String jdbcConnectionInitQuery();

}
