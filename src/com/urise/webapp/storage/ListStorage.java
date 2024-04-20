package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage implements Storage {
    protected static ArrayList<Resume> arrayList = new ArrayList<Resume>();

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExisting(index)) {
            arrayList.set(index,resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (isExisting(findIndex(resume.getUuid()))) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            arrayList.add(resume);
        }

    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            return arrayList.get(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            arrayList.remove(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return arrayList.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExisting(int index) {
        return index >= 0;
    }

}
