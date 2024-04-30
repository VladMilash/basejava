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
        saveElement(resume, getNotExistingSearchKey(resume));
    }

    public final void delete(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        deleteElement(getExistingSearchKey(resume));
    }

    public final Resume get(String uuid,String fullName) {
        Resume resume = new Resume(uuid, fullName);
        return getElement(getExistingSearchKey(resume));
    }

    private Object getExistingSearchKey(Resume resume) {
        Object searchKey = findSearchKey(resume.getUuid(), resume.getFullName());
        if (isExisting(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    private Object getNotExistingSearchKey(Resume resume) {
        Object searchKey = findSearchKey(resume.getUuid(), resume.getFullName());
        if (!isExisting(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract boolean isExisting(Object searchKey);

    protected abstract Resume getElement(Object searchKey);

    protected abstract void saveElement(Resume resume, Object searchKey);

    protected abstract Object findSearchKey(String uuid, String fullName);

    protected abstract void deleteElement(Object searchKey);

    protected abstract void updateElement(Resume resume, Object searchKey);

}