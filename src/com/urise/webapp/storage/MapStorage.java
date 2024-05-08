package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    protected Map<String, Resume> storage = new HashMap<>();

    public boolean isExisting(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected Resume getElement(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void saveElement(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected String findSearchKey(String uuid, String fullName) {
        return uuid;
    }

    @Override
    protected void deleteElement(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    public List<Resume> getListResume() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
