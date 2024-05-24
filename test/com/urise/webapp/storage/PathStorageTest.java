package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamSerializationStrategy;

public class PathStorageTest extends AbstractArrayStorageTest{
    public PathStorageTest() {
        super(new PathStorage("C:\\Users\\Матвей\\Documents\\java\\basejava" +
                "\\test\\com\\urise\\webapp\\storage\\FilesTestStorage",new ObjectStreamSerializationStrategy()));
    }
}
