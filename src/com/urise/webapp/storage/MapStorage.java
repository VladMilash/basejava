package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    public boolean isExisting(Object searchKey) {
       return  storage.containsKey(searchKey);
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void saveElement(Resume resume, Object searchKey) {
        storage.put((String)searchKey, resume);
    }

    @Override
    protected Object findIndex(String uuid) {
       return uuid;
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
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
