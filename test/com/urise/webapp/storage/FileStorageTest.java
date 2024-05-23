package com.urise.webapp.storage;

public class FileStorageTest extends AbstractArrayStorageTest {
    public FileStorageTest() {
        super(new FileStorage (STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}
