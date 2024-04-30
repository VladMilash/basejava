package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {
    void clear();

    void update(Resume resume);

    public void save(Resume resume);

    public Resume get(String uuid, String fullName);

    public void delete(String uuid, String fullName);

    public Resume[] getAll();

    public int size();
}
