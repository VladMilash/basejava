package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i] = resume;
                return;
            }
        }
        System.out.println("This resume was not found");
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("This resume is already available");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                System.out.println("This resume is already available");
                return;
            }
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("The specified resume could not be found");
        return null;
    }

    public void delete(String uuid) {
        boolean isFind = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                size--;
                storage[i] = storage[size];
                storage[size] = null;
                isFind = true;
                break;
            }
        }
        if (!isFind) {
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
}
