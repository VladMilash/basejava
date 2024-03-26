import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, size(), null);
    }

    void save(Resume resume) {
        storage[size()] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexDelete = -1;
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                indexDelete = i;
                break;
            }
        }

        if (indexDelete != -1) {
            for (int i = indexDelete + 1; i < size(); i++) {
                storage[i - 1] = storage[i];
            }
            storage[size() - 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int size = 0;
        for (Resume i : storage) {
            if (i != null) {
                size++;
            } else {
                break;
            }
        }
        return size;
    }
}
