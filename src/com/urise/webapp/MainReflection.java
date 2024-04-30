package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws Exception {
        Resume resume = new Resume("uuid5", "name");
        Class clazz = resume.getClass();
        Method toStringMethod = clazz.getMethod("toString");
        System.out.println(toStringMethod.invoke(resume));
    }
}
