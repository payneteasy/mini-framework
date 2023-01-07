package com.payneteasy.mini.jsonstore.impl;

import com.google.gson.Gson;
import com.payneteasy.mini.jsonstore.IStringIdStore;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringIdStoreBuilderTest {

    @Test
    public void test() {
        IStringIdStore<ExampleUserInfo> store = new StringIdStoreBuilder<>(ExampleUserInfo.class)
                .fileLocator(new StaticFileLocator("target/example-user.json-" + UUID.randomUUID()))
                .gson(new Gson())
                .identity(ExampleUserInfo::getUsername)
                .build();

        store.add(ExampleUserInfo.builder()
                .username("username-1")
                .password("password-2")
                .build());

        Optional<ExampleUserInfo> optionUser = store.findFirst("username-1");
        assertTrue(optionUser.isPresent());
        assertEquals("username-1", optionUser.get().getUsername());
        assertEquals("password-2", optionUser.get().getPassword());
    }

}