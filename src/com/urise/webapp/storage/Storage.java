package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.util.List;

public interface Storage {
    void clear() throws IOException;

    void update(Resume resume);

    public void save(Resume resume);

    public Resume get(String uuid, String fullName);

    public void delete(String uuid, String fullName);

    public List<Resume> getAllSorted();

    public int size();
}
