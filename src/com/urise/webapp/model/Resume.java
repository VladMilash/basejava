package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {

    @Serial
    private static final long serialversionUID= 1L;

    private final String uuid;

    private String fullName;

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid name must not be null");
        Objects.requireNonNull(fullName, "fullName name must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Section getSections(SectionType type) {
        return sections.get(type);
    }

    public String getContacts(ContactType type) {
        return contacts.get(type);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void putSections (SectionType type, Section content) {
        sections.put(type,content);
    }

    public void putContacts (ContactType type, String content) {
        contacts.put(type,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return uuid + "(" + fullName + ")";
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
