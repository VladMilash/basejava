package com.urise.webapp.storage;

public class ObjectStreamStorageTest extends AbstractArrayStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
