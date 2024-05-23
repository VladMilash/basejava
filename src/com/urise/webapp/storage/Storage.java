package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void update(Resume resume);

    public void save(Resume resume);

    public Resume get(String uuid, String fullName);

    public void delete(String uuid, String fullName);

    public List<Resume> getAllSorted();

    public int size();
}