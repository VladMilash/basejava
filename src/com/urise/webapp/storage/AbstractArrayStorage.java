package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            return storage[index];
        } else {
            System.out.println("The specified resume could not be found");
            return null;
        }
    }

    protected boolean isExisting(int index) {
        if (index >= 0) {
            return true;
        }
        return false;
    }

    protected abstract int findIndex(String uuid);
}
