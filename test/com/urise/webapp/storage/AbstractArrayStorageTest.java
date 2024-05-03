package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assume.assumeTrue;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String NAME_1 = "name1";
    private static final Resume RESUME_1 = new Resume(UUID_1, NAME_1);
    private static final String UUID_2 = "uuid2";
    private static final String NAME_2 = "name2";
    private static final Resume RESUME_2 = new Resume(UUID_2, NAME_2);
    private static final String UUID_3 = "uuid3";
    private static final String NAME_3 = "name3";
    private static final Resume RESUME_3 = new Resume(UUID_3, NAME_3);
    private static final String UUID_4 = "uuid4";
    private static final String NAME_4 = "name4";
    private static final Resume RESUME_4 = new Resume(UUID_4, NAME_4);

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
        List<Resume> expectedEmptyList = new ArrayList<>();
        Assert.assertEquals(expectedEmptyList, storage.getAllSorted());
    }

    @org.junit.Test
    public void update() {
        Resume updated = RESUME_1;
        storage.update(updated);
        Assert.assertSame(updated, storage.get(UUID_1, NAME_1));
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
        if (storage instanceof AbstractArrayStorage) {
            storage.clear();
            try {
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume());
                }

            } catch (StorageException e) {
                Assert.fail("The overflow occurred ahead of time");
            }
            storage.save(new Resume());
        } else {
            assumeTrue(false);
        }
    }

    @org.junit.Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @org.junit.Test()
    public void delete() {
        storage.delete(UUID_1, NAME_1);
        assertSize(2);
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4, NAME_4);
    }

    @org.junit.Test
    public void size() {
        assertSize(3);
    }

    @org.junit.Test
    public void get() {
        assertGet(storage.get(UUID_1, NAME_1));
        assertGet(storage.get(UUID_2, NAME_2));
        assertGet(storage.get(UUID_3, NAME_3));
    }

    @org.junit.Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4, NAME_4);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid(), resume.getFullName()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = new ArrayList<>();
        expected.add(RESUME_1);
        expected.add(RESUME_2);
        expected.add(RESUME_3);
        Assert.assertEquals(expected, storage.getAllSorted());
    }
}