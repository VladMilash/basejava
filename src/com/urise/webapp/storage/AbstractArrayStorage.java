package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public Resume getElement(int index) {
        return storage[index];
    }

    protected void incrementSize() {
        size++;
    }

    public void updateElement(Resume resume, int index) {
        storage[index] = resume;
    }

    protected void clearElement(Resume[] resume, int indexForDelete) {
        resume[indexForDelete] = null;
        size--;
    }

}
