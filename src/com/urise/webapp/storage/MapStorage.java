package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<>();

    public boolean isExisting(Object searchKey) {
        return searchKey != null;
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
    protected Object findIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
