package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {
    void clear();

    void update(Resume resume);

    public void save(Resume resume);

    public Resume get(String uuid);

    public void delete(String uuid);

    public Resume[] getAll();

    public int size();
}
