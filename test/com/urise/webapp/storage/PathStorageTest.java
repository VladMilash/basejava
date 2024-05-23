package com.urise.webapp.storage;

public class PathStorageTest extends AbstractArrayStorageTest{
    public PathStorageTest() {
        super(new PathStorage("C:\\Users\\Матвей\\Documents\\java\\basejava" +
                "\\test\\com\\urise\\webapp\\storage\\FilesTestStorage",new SpecificSerializationStrategy()));
    }
}
