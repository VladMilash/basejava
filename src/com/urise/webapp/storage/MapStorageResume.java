package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageResume extends AbstractStorage<Resume> {

    protected Map<String, Resume> storage = new HashMap<>();

    public boolean isExisting(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume getElement(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void saveElement(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume findSearchKey(String uuid, String fullName) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(Resume searchKey) {
        storage.remove((searchKey).getUuid());
    }

    @Override
    protected void updateElement(Resume resume, Resume searchKey) {
        storage.put((searchKey).getUuid(), resume);
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