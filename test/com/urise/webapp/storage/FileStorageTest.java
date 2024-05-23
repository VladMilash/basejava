package com.urise.webapp.storage;

import com.urise.webapp.storage.Serialization.ObjectStreamSerializationStrategy;

public class FileStorageTest extends AbstractArrayStorageTest {
    public FileStorageTest() {
        super(new FileStorage (STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}
