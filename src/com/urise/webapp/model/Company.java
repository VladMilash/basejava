package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final String title;
    private final String webSite;
    private final List<Period> periods;

    public Company(String title, String webSite, List<Period> periods) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(webSite, "webSite must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.title = title;
        this.webSite = webSite;
        this.periods = periods;
    }


    public String getTitle() {
        return title;
    }

    public String getWebSite() {
        return webSite;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(title, company.title) && Objects.equals(webSite, company.webSite) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, webSite, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "title='" + title + '\'' +
                ", webSite='" + webSite + '\'' +
                ", periods=" + periods +
                '}';
    }
}
