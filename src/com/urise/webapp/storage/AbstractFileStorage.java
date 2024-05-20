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
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName());
        }
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
        File[] files = directory.listFiles();
        if (files != null) {
            for (File value : files) {
                deleteElement(value);
            }
        }
    }

    @Override
    public int size() {
        try {
            return getCheckedListFiles().length;
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        }
    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> resumes = new ArrayList<>();
        try {
            File[] files = getCheckedListFiles();
            for (File file : files) {
                resumes.add(getElement(file));
            }
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        }
        return resumes;
    }

    private File[] getCheckedListFiles() throws IOException {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", directory.getName());
        } else {
            return files;
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
