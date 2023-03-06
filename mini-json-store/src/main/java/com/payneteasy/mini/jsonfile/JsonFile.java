package com.payneteasy.mini.jsonfile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonFile<T> {
    
    private static final Logger LOG = LoggerFactory.getLogger(JsonFile.class);

    private final Gson gson;
    private final Type type;

    public JsonFile(Gson aGson, Class<T> type) {
        gson = aGson;
        this.type = type;
    }

    public JsonFile(Gson aGson, TypeToken<T> aTypeToken) {
        gson = aGson;
        type = aTypeToken.getType();
    }

    public void write(File aFile, T aValue) {
        LOG.debug("Writing to {}\n{} ...", aFile.getAbsolutePath(), aValue);
        try {
            try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(aFile), UTF_8)) {
                out.write(gson.toJson(aValue));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write to " + aFile.getAbsolutePath(), e);
        }
    }

    public Optional<T> read(File aFile) {
        if(!aFile.exists()) {
            LOG.debug("File not found: {}", aFile.getAbsolutePath());
            return Optional.empty();
        }

        try {
            LOG.debug("Reading from {} ...", aFile.getAbsolutePath());

            try(InputStreamReader in = new InputStreamReader(new FileInputStream(aFile), UTF_8)) {
                return Optional.of(gson.fromJson(in, type));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file " + aFile.getAbsolutePath(), e);
        }

    }
}
