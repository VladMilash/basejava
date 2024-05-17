package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not bee null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getListResume() {
        try {
            return readAllDerectory(directory);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        }
    }

    private List<Resume> readAllDerectory(File file) throws IOException {
        List<Resume> resumes = new ArrayList<>();
        if (file != null) {
            File[] files = file.listFiles();
            for (File value : files) {
                if (value.isDirectory()) {
                    readAllDerectory(value);
                } else {
                    try {
                        resumes.add(doRead(value));
                    } catch (IOException e) {
                        throw new StorageException("IO error", value.getName());
                    }
                }
            }
        }
        return resumes;
    }

    @Override
    protected boolean isExisting(File file) {
        return file.exists();
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void saveElement(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File findSearchKey(String uuid, String fullName) {
        return new File(directory, uuid);
    }

    @Override
    protected void deleteElement(File file) {
        file.delete();
    }

    @Override
    protected void updateElement(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        try {
            clearDirectory(directory);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        }
    }

    private void clearDirectory(File file) throws IOException {
        if (file != null) {
            File[] files = file.listFiles();
            for (File value : files) {
                if (value.isDirectory()) {
                    clearDirectory(value);
                } else {
                    if (!value.delete()) {
                        throw new StorageException("IO error", value.getName());
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files.length;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
