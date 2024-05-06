package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public boolean isExisting(Object searchKey) {
        int index = (int) searchKey;
        return index >= 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public List<Resume> getListResume() {
        Resume[] arrayResume = Arrays.copyOf(storage, size);
        return new ArrayList<>(Arrays.asList(arrayResume));
    }

    public int size() {
        return size;
    }


    public Resume getElement(Object searchKey) {
        int index = (int) searchKey;
        return storage[index];

    }

    public void updateElement(Resume resume, Object searchKey) {
        int index = (int) searchKey;
        storage[index] = resume;
    }

    protected void incrementSize() {
        size++;
    }


    protected void clearElement(Resume[] resume, int indexForDelete) {
        resume[indexForDelete] = null;
        size--;
    }
}