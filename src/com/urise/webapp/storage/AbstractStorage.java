package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public abstract class AbstractStorage implements Storage {
    protected Object storage;

    public AbstractStorage(ArrayList storage) {
        this.storage = storage;
    }

    public AbstractStorage(Resume[] storage) {
        this.storage = storage;
    }

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExisting(index)) {
            updateElement(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public final void save(Resume resume) {
        if (isExisting(findIndex(resume.getUuid()))) {
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

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            return getElement(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected final boolean isExisting(int index) {
        return index >= 0;
    }

    protected abstract Resume getElement(int index);

    protected abstract void saveElement(Resume resume);

    protected abstract int findIndex(String uuid);

    protected abstract void deleteElement(int index);

    protected abstract void updateElement(Resume resume, int index);

}
