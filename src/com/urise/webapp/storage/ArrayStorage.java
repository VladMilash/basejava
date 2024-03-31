package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if ((isExisting(resume.getUuid(), index))) {
            storage[index] = resume;
        } else {
            System.out.println("The specified resume could not be found");
        }
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("The resume storage is full");
            return;
        } else if (isExisting(resume.getUuid(), findIndex(resume.getUuid()))) {
            System.out.println("This resume is already available");
            return;
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(uuid, index)) {
            return storage[index];
        } else {
            System.out.println("The specified resume could not be found");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(uuid, index)) {
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

    public int size() {
        return size;
    }

    private boolean isExisting(String uuid, int index) {
        if (storage[index].getUuid().equals(uuid)) {
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
