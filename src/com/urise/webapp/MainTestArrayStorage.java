package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.Storage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1","name1");
        final Resume r2 = new Resume("uuid2", "name2");
        final Resume r3 = new Resume("uuid3", "name3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.update(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid(), r1.getFullName()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy", "name7"));

//        printAll();
//        ARRAY_STORAGE.delete(r1.getUuid(), r1.getFullName());
//        printAll();
//        ARRAY_STORAGE.clear();
//        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

//    static void printAll() {
//        System.out.println("\nGet All");
//        for (Resume r : ARRAY_STORAGE.getAll()) {
//            System.out.println(r);
//        }
//    }
}
