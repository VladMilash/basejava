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
        if (!isResumeExists(resume.getUuid())) {
            System.out.println("The specified resume could not be found");
            return;
        }

        storage[indexStorage(resume.getUuid())] = resume;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("The resume storage is full");
            return;
        }

        if (isResumeExists(resume.getUuid())) {
            System.out.println("This resume is already available");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        if (!isResumeExists(uuid)) {
            System.out.println("The specified resume could not be found");
            return null;
        }

        return storage[indexStorage(uuid)];
    }

    public void delete(String uuid) {
        if (!isResumeExists(uuid)) {
            System.out.println("The specified resume could not be found");
            return;
        }

        storage[indexStorage(uuid)] = storage[size - 1];
        storage[size - 1] = null;
        size--;
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

    private boolean isResumeExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    private int indexStorage(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
