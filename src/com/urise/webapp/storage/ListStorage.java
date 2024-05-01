package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    public boolean isExisting(Object searchKey) {
        int index = (int) searchKey;
        return index >= 0;
    }

    protected static List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    public Resume getElement(Object searchKey) {
        int index = (int) searchKey;
        return storage.get(index);
    }

    public List<Resume> getAllSorted() {
       List<Resume> sortedListResume = new ArrayList<>(storage);
       sortedListResume.sort(RESUME_COMPARATOR);
       return sortedListResume;
    }

    @Override
    public int size() {
        return storage.size();
    }

    public Object findSearchKey(String uuid, String fullName) {
        Resume searchResume = new Resume(uuid, fullName);
        return storage.indexOf(searchResume);
    }

    public void saveElement(Resume resume, Object searchKey) {
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
}