package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
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
                        for (String content : contents) {
                            dos.writeUTF(content);
                        }
                    }
                    case EDUCATION, EXPERIENCE -> {
                        CompanySection companySection = (CompanySection) entry.getValue();
                        List<Company> companies = companySection.getCompanies();
                        dos.writeInt(companies.size());
                        for (Company company : companies) {
                            dos.writeUTF(company.getTitle());
                            dos.writeUTF(company.getWebSite());
                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            }
                        }
                    }
                }
            }
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
                            int sizePeriods = dis.readInt();
                            List<Period> periods = new ArrayList<>();
                            for (int k = 0; k < sizePeriods; k++) {
                                String titlePeriod = dis.readUTF();
                                String description = dis.readUTF();
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                periods.add(new Period(titlePeriod, description, startDate, endDate));
                            }
                            companies.add(new Company(title, webSite, periods));
                        }
                        section = new CompanySection(companies);
                    }
                }
                if (section != null) {
                    resume.putSections(sectionType, section);
                }
            }
            return resume;
        }

    }
}
