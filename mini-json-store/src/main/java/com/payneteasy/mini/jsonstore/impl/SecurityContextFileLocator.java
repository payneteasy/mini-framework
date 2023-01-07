package com.payneteasy.mini.jsonstore.impl;


import com.payneteasy.mini.jsonstore.IFileLocator;

import java.io.File;
import java.util.function.Supplier;

public class SecurityContextFileLocator implements IFileLocator {

    private final File             baseDir;
    private final String           entityName;
    private final Supplier<String> usernameFinder;

    public SecurityContextFileLocator(File baseDir, String entityName, Supplier<String> usernameFinder) {
        this.baseDir        = baseDir;
        this.entityName     = entityName;
        this.usernameFinder = usernameFinder;
    }

    @Override
    public File locateFile() {
        File userDir = new File(baseDir, usernameFinder.get());
        if(!userDir.exists()) {
            if(!userDir.mkdirs()) {
                throw new IllegalStateException("Cannot create dir " + userDir.getAbsolutePath());
            }
        }
        File jsonFile = new File(userDir, entityName + ".json");
        return jsonFile;
    }
}
