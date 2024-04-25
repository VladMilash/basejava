package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume);
        updateElement(resume, searchKey);
    }

    public final void save(Resume resume) {
        getNotExistingSearchKey(resume);
        saveElement(resume);
    }

    public final void delete(String uuid) {
        Resume resume = new Resume(uuid);
        deleteElement(getExistingSearchKey(resume));
    }

    public final Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        return getElement(getExistingSearchKey(resume));
    }

    public Object getExistingSearchKey(Resume resume) {
        Object searchKey = findIndex(resume.getUuid());
        if (isExisting(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public Object getNotExistingSearchKey(Resume resume) {
        Object searchKey = findIndex(resume.getUuid());
        if (!isExisting(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract boolean isExisting(Object searchKey);

    protected abstract Resume getElement(Object searchKey);

    protected abstract void saveElement(Resume resume);

    protected abstract int findIndex(String uuid);

    protected abstract void deleteElement(Object searchKey);

    protected abstract void updateElement(Resume resume, Object searchKey);

}