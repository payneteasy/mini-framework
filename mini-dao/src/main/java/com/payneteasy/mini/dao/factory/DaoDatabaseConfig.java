package com.payneteasy.mini.dao.factory;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Builder
@Data
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DaoDatabaseConfig {

    String jdbcUrl;
    String username;
    String password;
    String connectionInitQuery;

}
