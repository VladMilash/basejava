package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
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

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExisting(index)) {
            storage[index] = resume;
        } else {
            //System.out.println("The specified resume could not be found");
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public final void save(Resume resume) {
        if (size >= storage.length) {
            throw  new StorageException("Storage overflow", resume.getUuid());
        } else if (isExisting(findIndex(resume.getUuid()))) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume);
        }
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            deleteElement(index);
        } else {
            throw new NotExistStorageException(uuid);
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

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected boolean isExisting(int index) {
        return index >= 0;
    }

    protected void incrementSize() {
        size++;
    }

    protected void clearElement(Resume[] resume, int indexForDelete) {
        resume[indexForDelete] = null;
        size--;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveElement(Resume resume);

    protected abstract void deleteElement(int index);
}
