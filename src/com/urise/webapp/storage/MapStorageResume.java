package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageResume extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    public boolean isExisting(Object searchKey) {
        return storage.containsKey(((Resume) searchKey).getUuid());
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected void saveElement(Resume resume, Object searchKey) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected Resume findSearchKey(String uuid, String fullName) {
        Resume searchKey = new Resume(uuid, fullName);
        return searchKey;
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    public List<Resume> getAllSorted() {
        List<Resume> sortedListResume = new ArrayList<>(storage.values());
        sortedListResume.sort(RESUME_COMPARATOR);
        return sortedListResume;
    }

    @Override
    public int size() {
        return storage.size();
    }

}
