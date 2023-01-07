package com.payneteasy.mini.core.app;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Builder
public class AppContext {

    Logger   logger;
    String[] args;

}
