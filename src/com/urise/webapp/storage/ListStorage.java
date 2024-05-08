package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected static List<Resume> storage = new ArrayList<>();

    public boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    public Resume getElement(Integer searchKey) {
        return storage.get(searchKey);
    }

    public List<Resume> getListResume() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    public Integer findSearchKey(String uuid, String fullName) {
        Resume searchResume = new Resume(uuid, fullName);
        return storage.indexOf(searchResume);
    }

    public void saveElement(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    public void deleteElement(Integer searchKey) {
        int index = searchKey;
        storage.remove(index);
    }

    public void updateElement(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }
}