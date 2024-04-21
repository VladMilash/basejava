package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected static ArrayList<Resume> arrayList = new ArrayList<Resume>();

    public ListStorage() {
        super(arrayList);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }

    public Resume getElement(int index) {
        return arrayList.get(index);
    }

    @Override
    public Resume[] getAll() {
        return arrayList.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    public int findIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return arrayList.indexOf(searchResume);
    }

    public void saveElement(Resume resume) {
        arrayList.add(resume);
    }

    public void deleteElement(int index) {
        arrayList.remove(index);
    }

    public void updateElement(Resume resume, int index) {
        arrayList.set(index, resume);
    }
}