package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.DataStreamSerializer;

public class DataPathStorageTest extends AbstractArrayStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage("C:\\Users\\Матвей\\Documents\\java\\basejava" +
                "\\test\\com\\urise\\webapp\\storage\\FilesTestStorage",new DataStreamSerializer()));
    }
}
