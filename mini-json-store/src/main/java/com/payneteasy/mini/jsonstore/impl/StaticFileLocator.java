package com.payneteasy.mini.jsonstore.impl;

import com.payneteasy.mini.jsonstore.IFileLocator;

import java.io.File;

public class StaticFileLocator implements IFileLocator {

    private final File file;

    public StaticFileLocator(File file) {
        this.file = file;
    }

    public StaticFileLocator(String aFilename) {
        this.file = new File(aFilename);
    }

    @Override
    public File locateFile() {
        return file;
    }
}
