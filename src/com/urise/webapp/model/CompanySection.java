package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySection extends Section {
    @Serial
    private static final long serialversionUID = 1L;
    private List<Company> companys;

    public CompanySection() {
    }

    public CompanySection(List<Company> companys) {
        Objects.requireNonNull(companys, "company must not be null");
        this.companys = companys;
    }

    public List<Company> getCompanies() {
        return companys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companys, that.companys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companys);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "company=" + companys +
                '}';
    }
}
