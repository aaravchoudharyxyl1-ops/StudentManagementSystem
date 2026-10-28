package com.studentmanagement;

public class Student {
    private final int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
        setName(name);
        setAge(age);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 1 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        this.age = age;
    }

    public void displayStudent() {
        System.out.printf("Student ID: %d | Name: %s | Age: %d%n", id, name, age);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}
