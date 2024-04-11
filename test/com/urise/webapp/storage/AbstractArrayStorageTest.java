package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractArrayStorageTest {
private Storage storage;

private static final String UUID_1 = "uuid1";
private static final String UUID_2 = "uuid2";
private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @org.junit.Test
    public void clear() {
        Assert.assertEquals(3, storage.size());
        storage.clear();
        Assert.assertEquals(0,storage.size());
    }

    @org.junit.Test
    public void update() {
        Resume Updateresume = new Resume("uuid1");
        storage.update(Updateresume);
        Assert.assertEquals(Updateresume, storage.get("uuid1"));
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume Updateresume = new Resume("uuid5");
        storage.update(Updateresume);
    }

    @org.junit.Test
    public void save() {
        Resume resume = new Resume("uuid_55");
        storage.save(resume);
    }

    @org.junit.Test(expected = StorageException.class)
    public void saveStorageOverflow() {
    }

//    public final void save(Resume resume) {
//        if (size >= storage.length) {
//            throw  new StorageException("Storage overflow", resume.getUuid());
//        } else if (isExisting(findIndex(resume.getUuid()))) {
//            throw new ExistStorageException(resume.getUuid());
//        } else {
//            saveElement(resume);
//        }
//    }

    @org.junit.Test
    public void delete() {
    }

    @org.junit.Test
    public void getAll() {
    }

    @org.junit.Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @org.junit.Test
    public void get() {
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}