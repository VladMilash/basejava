package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private final List<Company> companys;

    public CompanySection(List<Company> companys) {
        Objects.requireNonNull(companys, "company must not be null");
        this.companys = companys;
    }

    public List<Company> getCompanys() {
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
