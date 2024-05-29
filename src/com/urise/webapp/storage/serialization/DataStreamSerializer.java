package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithExeption(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            writeWithExeption(sections.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getContent());
                    }
                    case ARCHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) entry.getValue();
                        List<String> contents = listSection.getContent();
                        writeWithExeption(contents, dos, dos::writeUTF);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        CompanySection companySection = (CompanySection) entry.getValue();
                        List<Company> companies = companySection.getCompanies();
                        writeWithExeption(companies, dos, company -> {
                            dos.writeUTF(company.getTitle());
                            dos.writeUTF(company.getWebSite() != null ? company.getWebSite() : "");
                            List<Period> periods = company.getPeriods();
                            writeWithExeption(periods, dos, period -> {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription() != null ? period.getDescription() : "");
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.putContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = null;
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        String content = dis.readUTF();
                        section = new TextSection(content);
                    }
                    case ARCHIEVEMENT, QUALIFICATIONS -> {
                        List<String> content = readWithExeption(dis, DataInput::readUTF);
                        section = new ListSection(content);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Company> companies = readWithExeption(dis, dataInputStream -> {
                            String title = dis.readUTF();
                            String webSite = dis.readUTF();
                            webSite = webSite.isEmpty() ? null : webSite;
                            List<Period> periods = readWithExeption(dis, dataInputStream1 -> {
                                String titlePeriod = dis.readUTF();
                                String description = dis.readUTF();
                                description = description.isEmpty() ? null : description;
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                return new Period(titlePeriod, description, startDate, endDate);
                            });
                            return new Company(title, webSite, periods);
                        });
                        section = new CompanySection(companies);
                    }
                }
                resume.putSections(sectionType, section);
            }
            return resume;
        }

    }

    @FunctionalInterface
    public interface ThrowingConsumer<T> {
        void accept(T t) throws IOException;
    }

    @FunctionalInterface
    public interface ThrowingFunction<T, R> {
        R accept(T t) throws IOException;
    }

    public static <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, ThrowingConsumer<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            consumer.accept(item);
        }
    }

    public static <T> List<T> readWithExeption(DataInputStream dis, ThrowingFunction<DataInputStream, T> function) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(function.accept(dis));
        }
        return list;
    }
}
