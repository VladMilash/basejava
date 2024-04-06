package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(Resume resume) {
        int insertionPoint = -(Arrays.binarySearch(storage, 0, size, resume)) - 1;
        storage[size] = resume;
        for (int i = size - 1; i >= insertionPoint; i--) {
            storage[i + 1] = storage[i];
        }
        storage[insertionPoint] = resume;
        incrementSize();
    }

    @Override
    protected void deleteElement(int index) {
        for (int i = index; i <= size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        clearElement(storage, size - 1);
    }

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
