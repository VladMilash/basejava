package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
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


    public Resume getElement(Integer searchKey) {
        return storage[searchKey];

    }

    public void updateElement(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    protected void incrementSize() {
        size++;
    }


    protected void clearElement(Resume[] resume, int indexForDelete) {
        resume[indexForDelete] = null;
        size--;
    }
}