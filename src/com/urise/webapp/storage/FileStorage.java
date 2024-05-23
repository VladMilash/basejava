package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Serialization.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final SerializationStrategy serializationStrategy;
    private final File directory;

    protected FileStorage(File directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "directory must not bee null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    protected boolean isExisting(File file) {
        return file.exists();
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error ", file.getName(), e);
        }
    }

    @Override
    protected void saveElement(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", file.getAbsolutePath(), e);
        }
        updateElement(resume, file);
    }

    @Override
    protected File findSearchKey(String uuid, String fullName) {
        return new File(directory, uuid);
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected void updateElement(Resume resume, File file) {
        try {
            serializationStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error ", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        try {
            File[] files = getCheckedListFiles();
            for (File value : files) {
                deleteElement(value);
            }
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
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
        try {
            List<Resume> resumes = new ArrayList<>();
            File[] files = getCheckedListFiles();
            for (File file : files) {
                resumes.add(getElement(file));
            }
            return resumes;
        } catch (IOException e) {
            throw new StorageException("Directory read error ", directory.getName(), e);
        }

    }

    private File[] getCheckedListFiles() throws IOException {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error ", directory.getName());
        }
        return files;
    }

}