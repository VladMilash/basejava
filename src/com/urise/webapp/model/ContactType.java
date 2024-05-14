package com.urise.webapp.model;

public enum ContactType {
    NUMBER("Номер телефона"),
    SKYPE("Скайп"),
    EMAIL("Электронная почта"),
    lINKENDLN("Профиль на LinkedIn"),
    GITHUB("Профиль на GitHub"),
    STACKOVERFLOW("Профиль на Stackoverflow"),
    SITE("Личный web-сайт");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ContactType{" +
                "title='" + title + '\'' +
                '}';
    }
}
