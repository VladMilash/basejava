package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void saveElement(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void deleteElements(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
