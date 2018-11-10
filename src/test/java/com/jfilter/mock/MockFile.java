package com.jfilter.mock;

import java.io.File;

public class MockFile extends File {
    private static final long serialVersionUID = -8702456483666584628L;

    public MockFile(String pathname) {
        super(pathname);
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
