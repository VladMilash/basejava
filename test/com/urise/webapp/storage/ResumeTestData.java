package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.ContactType.SITE;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.putContacts(NUMBER, "NumberExample");
        resume.putContacts(SKYPE, "SkypeExample");
        resume.putContacts(EMAIL, "EmailExample");
        resume.putContacts(lINKENDLN, "linkedin");
        resume.putContacts(STACKOVERFLOW, "stackoverflow");
        resume.putContacts(SITE, "SiteExample");

        resume.putSections(PERSONAL, new TextSection("Личные качества для резюме"));
        resume.putSections(OBJECTIVE, new TextSection("Позция для резюме"));
        resume.putSections(ARCHIEVEMENT, new ListSection(List.of("Достижение1, достижение2")));
        resume.putSections(QUALIFICATIONS, new ListSection(List.of("Квалификация1, квалификация2")));

        LocalDate startDateExample = DateUtil.of(1990, Month.APRIL);
        LocalDate endDateExample = DateUtil.of(1992, Month.MAY);
        Period periodExample1 = new Period("Организация1", "Должность1", startDateExample,
                endDateExample);
        Company companyExample1 = new Company("Название компании", "Ссылка на компанию",
                List.of(periodExample1));

        resume.putSections(EXPERIENCE, new CompanySection(List.of(companyExample1)));
        resume.putSections(EDUCATION, new CompanySection(List.of(companyExample1)));

        return resume;
    }

}

