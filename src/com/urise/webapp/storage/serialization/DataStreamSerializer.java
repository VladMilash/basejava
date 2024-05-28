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
            dos.writeInt(contacts.size());
            writeWithExeption(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
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
                        dos.writeInt(contents.size());
                        writeWithExeption(contents, dos, dos::writeUTF);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        CompanySection companySection = (CompanySection) entry.getValue();
                        List<Company> companies = companySection.getCompanies();
                        dos.writeInt(companies.size());
                        writeWithExeption(companies, dos, company -> {
                            dos.writeUTF(company.getTitle());
                            dos.writeUTF(company.getWebSite() != null ? company.getWebSite() : "");
                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
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
                        int sizeContent = dis.readInt();
                        List<String> content = new ArrayList<>();
                        for (int j = 0; j < sizeContent; j++) {
                            content.add(dis.readUTF());
                        }
                        section = new ListSection(content);

                    }
                    case EDUCATION, EXPERIENCE -> {
                        int sizeCompanies = dis.readInt();
                        List<Company> companies = new ArrayList<>();
                        for (int j = 0; j < sizeCompanies; j++) {
                            String title = dis.readUTF();
                            String webSite = dis.readUTF();
                            webSite = webSite.isEmpty() ? null : webSite;
                            int sizePeriods = dis.readInt();
                            List<Period> periods = new ArrayList<>();
                            for (int k = 0; k < sizePeriods; k++) {
                                String titlePeriod = dis.readUTF();
                                String description = dis.readUTF();
                                description = description.isEmpty() ? null : description;
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                periods.add(new Period(titlePeriod, description, startDate, endDate));
                            }
                            companies.add(new Company(title, webSite, periods));
                        }
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

    public static <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, ThrowingConsumer<T> consumer) throws IOException {
        for (T item : collection) {
            consumer.accept(item);
        }
    }
}
