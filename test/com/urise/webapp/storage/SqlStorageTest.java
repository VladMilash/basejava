package com.urise.webapp.storage;

import com.urise.webapp.Config;

public class SqlStorageTest extends  AbstractArrayStorageTest{
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
