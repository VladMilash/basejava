package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @org.junit.Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @org.junit.Test
    public void update() {
        Resume updated = RESUME_1;
        storage.update(updated);
        Assert.assertSame(updated, storage.get(UUID_1));
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @org.junit.Test
    public void save() {
        Resume resume = RESUME_4;
        storage.save(resume);
        assertGet(resume);
        assertSize(4);
    }

    @org.junit.Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }

        } catch (StorageException e) {
            Assert.fail("The overflow occurred ahead of time");
        }
        storage.save(new Resume());
    }

    @org.junit.Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @org.junit.Test()
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @org.junit.Test
    public void getAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @org.junit.Test
    public void size() {
        assertSize(3);
    }

    @org.junit.Test
    public void get() {
        assertGet(storage.get(UUID_1));
        assertGet(storage.get(UUID_2));
        assertGet(storage.get(UUID_3));
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}