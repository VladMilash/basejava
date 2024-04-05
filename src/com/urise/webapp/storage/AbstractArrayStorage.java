package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

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
            saveElement(resume);
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            deleteElements(index);
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

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            return storage[index];
        } else {
            System.out.println("The specified resume could not be found");
            return null;
        }
    }

    protected boolean isExisting(int index) {
        if (index >= 0) {
            return true;
        }
        return false;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveElement(Resume resume);

    protected abstract void deleteElements(int index);
}
