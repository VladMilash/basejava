package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractArrayStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage("C:\\Users\\Матвей\\Documents\\java\\basejava" +
                "\\test\\com\\urise\\webapp\\storage\\FilesTestStorage",new XmlStreamSerializer()));
    }
}
