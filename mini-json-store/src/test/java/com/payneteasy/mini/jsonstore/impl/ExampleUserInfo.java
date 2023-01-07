package com.payneteasy.mini.jsonstore.impl;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Builder
public class ExampleUserInfo {
    String username;
    String password;

}
