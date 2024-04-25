package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected static List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    public Resume getElement(Object searchKey) {
        int index = (int) searchKey;
        return storage.get(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    public int findIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return storage.indexOf(searchResume);
    }

    public void saveElement(Resume resume) {
        storage.add(resume);
    }

    public void deleteElement(Object searchKey) {
        int index = (int) searchKey;
        storage.remove(index);
    }

    public void updateElement(Resume resume, Object searchKey) {
        int index = (int) searchKey;
        storage.set(index, resume);
    }

    protected boolean isExisting(Object searchKey) {
        int index = (int) searchKey;
        return index >= 0;
    }
}