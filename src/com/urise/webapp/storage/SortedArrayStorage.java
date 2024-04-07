package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(Resume resume) {
        int insertionPoint = -(Arrays.binarySearch(storage, 0, size, resume)) - 1;
        if (insertionPoint < size) {
            System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
            storage[insertionPoint] = resume;
            incrementSize();
        } else {
            storage[insertionPoint] = resume;
            incrementSize();
        }

    }

    @Override
    protected void deleteElement(int index) {
        if (index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            clearElement(storage, size - 1);
        } else {
            clearElement(storage, index);
        }
    }

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
