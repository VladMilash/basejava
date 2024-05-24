package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serialization.SerializationStrategy;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final SerializationStrategy serializationStrategy;

    protected PathStorage(String directory, SerializationStrategy serializationStrategy) {
        this.directory = Paths.get(directory);
        Objects.requireNonNull(this.directory, "directory must not be null");
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    protected List<Resume> getListResume() {
        try (Stream<Path> paths = getCheckedListFiles()) {
            return paths.map(this::getElement)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error ", directory.toString(), e);
        }
    }

    private Stream<Path> getCheckedListFiles() throws IOException {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error ", directory.toString(), e);
        }
    }

    @Override
    protected boolean isExisting(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return serializationStrategy.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("Directory read error ", directory.toString(), e);
        }
    }

    @Override
    protected void saveElement(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", path.toString(), e);
        }
        updateElement(resume, path);
    }

    @Override
    protected Path findSearchKey(String uuid, String fullName) {
        return directory.resolve(uuid);
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error ", path.toString(), e);
        }
    }

    protected void updateElement(Resume resume, Path path) {
        try (OutputStream os = Files.newOutputStream(path)) {
            serializationStrategy.doWrite(resume, os);
        } catch (IOException e) {
            throw new StorageException("File write error: ", path.toString(), e);
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> paths = getCheckedListFiles()) {
            paths.forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) getCheckedListFiles().count();
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toString(), e);
        }
    }
}

