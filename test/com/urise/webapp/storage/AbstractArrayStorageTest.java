package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
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
        storage.update(new Resume("uuid5"));
    }

    @org.junit.Test
    public void save() {
        Resume resume = new Resume("uuid55");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("uuid55"));
    }

    @org.junit.Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        storage.clear();
        try {
            for (int i =0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }

        } catch (StorageException e) {
            Assert.fail("The overflow occurred ahead of time");
        }
        storage.save(new Resume());
    }

    @org.junit.Test(expected = ExistStorageException.class)
    public void saveExist() {
       storage.save(new Resume("uuid2"));
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        storage.get("uuid1");
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid10");
    }

    @org.junit.Test
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        Assert.assertEquals(storage.size(), allResumes.length);
    }

    @org.junit.Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @org.junit.Test
    public void get() {
        Assert.assertEquals(storage.get(UUID_1), new Resume(UUID_1));
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}