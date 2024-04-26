package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(Resume resume) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage[size] = resume;
        incrementSize();
    }

    @Override
    protected void deleteElement(Object searchKey) {
        int index = (int) searchKey;
        storage[index] = storage[size - 1];
        clearElement(storage, size - 1);
    }

    protected Object findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}