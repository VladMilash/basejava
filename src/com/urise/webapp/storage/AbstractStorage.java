package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistingSearchKey(resume);
        updateElement(resume, searchKey);
    }

    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        getNotExistingSearchKey(resume);
        saveElement(resume, getNotExistingSearchKey(resume));
    }

    public final void delete(String uuid, String fullName) {
        LOG.info("Delete " + uuid);
        Resume resume = new Resume(uuid, fullName);
        deleteElement(getExistingSearchKey(resume));
    }

    public final Resume get(String uuid, String fullName) {
        LOG.info("Update " + uuid);
        Resume resume = new Resume(uuid, fullName);
        return getElement(getExistingSearchKey(resume));
    }

    private SK getExistingSearchKey(Resume resume) {
        SK searchKey = findSearchKey(resume.getUuid(), resume.getFullName());
        if (isExisting(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + resume.getUuid() + " is not exist " + resume.getUuid());
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    private SK getNotExistingSearchKey(Resume resume) {
        SK searchKey = findSearchKey(resume.getUuid(), resume.getFullName());
        if (!isExisting(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + resume.getUuid() + " already exist");
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted ");
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