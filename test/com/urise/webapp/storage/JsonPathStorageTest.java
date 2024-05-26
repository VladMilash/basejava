package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractArrayStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage("C:\\Users\\Матвей\\Documents\\java\\basejava" +
                "\\test\\com\\urise\\webapp\\storage\\FilesTestStorage",new JsonStreamSerializer()));
    }
}
