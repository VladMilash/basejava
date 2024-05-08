package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final void update(Resume resume) {
        SK searchKey = getExistingSearchKey(resume);
        updateElement(resume, searchKey);
    }

    public final void save(Resume resume) {
        getNotExistingSearchKey(resume);
        saveElement(resume, getNotExistingSearchKey(resume));
    }

    public final void delete(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        deleteElement(getExistingSearchKey(resume));
    }

    public final Resume get(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        return getElement(getExistingSearchKey(resume));
    }

    private SK getExistingSearchKey(Resume resume) {
        SK searchKey = findSearchKey(resume.getUuid(), resume.getFullName());
        if (isExisting(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    private SK getNotExistingSearchKey(Resume resume) {
        SK searchKey = findSearchKey(resume.getUuid(), resume.getFullName());
        if (!isExisting(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getListResume();
        sortedList.sort(RESUME_COMPARATOR);
        return sortedList;
    }

    protected abstract List<Resume> getListResume();

    protected abstract boolean isExisting(SK searchKey);

    protected abstract Resume getElement(SK searchKey);

    protected abstract void saveElement(Resume resume, SK searchKey);

    protected abstract SK findSearchKey(String uuid, String fullName);

    protected abstract void deleteElement(SK searchKey);

    protected abstract void updateElement(Resume resume, SK searchKey);

}