package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    protected HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExisting(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void saveElement(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected int findIndex(String uuid) {
        return 0;
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
