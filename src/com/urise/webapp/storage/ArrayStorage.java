package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExisting(index)) {
            storage[index] = resume;
        } else {
            System.out.println("The specified resume could not be found");
        }
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("The resume storage is full");
        } else if (isExisting(findIndex(resume.getUuid()))) {
            System.out.println("This resume is already available");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            return storage[index];
        } else {
            System.out.println("The specified resume could not be found");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("The specified resume could not be found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    private boolean isExisting(int index) {
        if (index >= 0) {
            return true;
        }
        return false;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
