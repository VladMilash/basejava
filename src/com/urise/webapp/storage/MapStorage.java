package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    public boolean isExisting(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void saveElement(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Object findSearchKey(String uuid, String fullName) {
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

    public List<Resume> getListResume() {
        List<Resume> list = new ArrayList<>(storage.values());
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
